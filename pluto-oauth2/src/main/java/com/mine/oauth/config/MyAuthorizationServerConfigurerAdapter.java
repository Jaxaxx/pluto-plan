package com.mine.oauth.config;

import com.mine.common.core.constant.SecurityConstants;
import com.mine.common.security.model.MyUser;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.DefaultAuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author jax-li
 */
@Configuration
@RequiredArgsConstructor
@EnableAuthorizationServer
public class MyAuthorizationServerConfigurerAdapter extends AuthorizationServerConfigurerAdapter {

    // Factory Bean
    // ======================================================
    private final DataSource dataSource;
    private final RedisConnectionFactory redisConnectionFactory;
    private final UserDetailsService myUserDetailsService;
    private final AuthenticationManager authenticationManager;
//    private final WebResponseExceptionTranslator myWebResponseExceptionTranslator;

    // expand Bean
    // ======================================================

    @Primary
    @Bean
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setTokenStore(redisTokenStore());
        tokenServices.setSupportRefreshToken(true);
        tokenServices.setTokenEnhancer(tokenEnhancer());
        tokenServices.setClientDetailsService(jdbcClientDetailsService());
        return tokenServices;
    }

    @Bean
    public TokenStore redisTokenStore() {
        RedisTokenStore redisTokenStore = new RedisTokenStore(redisConnectionFactory);
        redisTokenStore.setPrefix(SecurityConstants.OAUTH_PREFIX);
        redisTokenStore.setAuthenticationKeyGenerator(new DefaultAuthenticationKeyGenerator() {
            @Override
            public String extractKey(OAuth2Authentication authentication) {
                // 可自定修改token 生成规则
                return super.extractKey(authentication);
            }
        });
        return redisTokenStore;
    }

    @Bean
    public TokenEnhancer tokenEnhancer() {
        return (accessToken, authentication) -> {
            if (SecurityConstants.CLIENT_CREDENTIALS.equals(authentication.getOAuth2Request().getGrantType())) {
                return accessToken;
            }
            final Map<String, Object> additionalInfo = new HashMap<String, Object>(16);
            MyUser myUser = (MyUser) authentication.getUserAuthentication().getPrincipal();
            additionalInfo.put(SecurityConstants.DETAILS_USER_ID, myUser.getId());
            additionalInfo.put(SecurityConstants.DETAILS_PHONE, myUser.getPhone());
            additionalInfo.put(SecurityConstants.DETAILS_LICENSE, SecurityConstants.MY_LICENSE);
            additionalInfo.put(SecurityConstants.DETAILS_NAME, myUser.getName());
            ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
            return accessToken;
        };
    }

    @Bean
    @Primary
    public ClientDetailsService jdbcClientDetailsService() {
        JdbcClientDetailsService clientDetailsService = new JdbcClientDetailsService(dataSource);
        clientDetailsService.setInsertClientDetailsSql(SecurityConstants.DEFAULT_INSERT_STATEMENT);
        clientDetailsService.setDeleteClientDetailsSql(SecurityConstants.DEFAULT_DELETE_STATEMENT);
        clientDetailsService.setUpdateClientDetailsSql(SecurityConstants.DEFAULT_UPDATE_STATEMENT);
        clientDetailsService.setUpdateClientSecretSql(SecurityConstants.DEFAULT_UPDATE_SECRET_STATEMENT);
        clientDetailsService.setSelectClientDetailsSql(SecurityConstants.DEFAULT_SELECT_STATEMENT);
        clientDetailsService.setFindClientDetailsSql(SecurityConstants.DEFAULT_FIND_STATEMENT);
        return clientDetailsService;
    }

    // function
    // ======================================================
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        /**
         * redis token 方式
         * authenticationManage() 调用此方法才能支持 password 模式。
         * userDetailsService() 设置用户验证服务。
         * tokenStore() 指定 token 的存储方式。
         */
        endpoints.allowedTokenEndpointRequestMethods(HttpMethod.POST, HttpMethod.GET)
                .authenticationManager(authenticationManager)
                .userDetailsService(myUserDetailsService)
                .tokenServices(tokenServices())
//                .exceptionTranslator(myWebResponseExceptionTranslator)
        ;
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(jdbcClientDetailsService());
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        /**
         * 允许客户端访问 OAuth2 授权接口，否则请求 token 会返回 401
         */
        security.allowFormAuthenticationForClients();
        /**
         * 允许已授权用户访问 checkToken 接口和获取 token 接口。
         */
        security.checkTokenAccess("isAuthenticated()");
        security.tokenKeyAccess("isAuthenticated()");
    }

}