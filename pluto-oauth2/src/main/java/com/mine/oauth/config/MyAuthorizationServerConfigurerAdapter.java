package com.mine.oauth.config;

import com.mine.common.core.constant.SecurityConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;

import javax.sql.DataSource;

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
    private final UserDetailsService myUserDetailsService;
    private final AuthenticationManager authenticationManager;
    private final TokenStore redisTokenStore;
    private final TokenEnhancer tokenEnhancer;

    // expand Bean
    // ======================================================

//    @Primary
//    @Bean
//    public DefaultTokenServices tokenServices() {
//        DefaultTokenServices tokenServices = new DefaultTokenServices();
//        tokenServices.setTokenStore(redisTokenStore());
//        tokenServices.setSupportRefreshToken(true);
//        tokenServices.setTokenEnhancer(tokenEnhancer());
//        tokenServices.setClientDetailsService(jdbcClientDetailsService());
//        return tokenServices;
//    }


    // function
    // ======================================================
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        /**
         * redis token 方式
         * authenticationManage() 调用此方法才能支持 password 模式。
         * userDetailsService() 设置用户验证服务。
         * tokenStore() 指定 token 的存储方式。
         * tokenEnhancer() token增强
         */
        endpoints.allowedTokenEndpointRequestMethods(HttpMethod.POST, HttpMethod.GET)
                .authenticationManager(authenticationManager)
//                .userDetailsService(myUserDetailsService)
                .tokenStore(redisTokenStore)
                .tokenEnhancer(tokenEnhancer)
//                .tokenServices(tokenServices())
//                .exceptionTranslator(myWebResponseExceptionTranslator)
        ;
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        JdbcClientDetailsService clientDetailsService = new JdbcClientDetailsService(dataSource);
        clientDetailsService.setInsertClientDetailsSql(SecurityConstants.DEFAULT_INSERT_STATEMENT);
        clientDetailsService.setDeleteClientDetailsSql(SecurityConstants.DEFAULT_DELETE_STATEMENT);
        clientDetailsService.setUpdateClientDetailsSql(SecurityConstants.DEFAULT_UPDATE_STATEMENT);
        clientDetailsService.setUpdateClientSecretSql(SecurityConstants.DEFAULT_UPDATE_SECRET_STATEMENT);
        clientDetailsService.setSelectClientDetailsSql(SecurityConstants.DEFAULT_SELECT_STATEMENT);
        clientDetailsService.setFindClientDetailsSql(SecurityConstants.DEFAULT_FIND_STATEMENT);
        clients.withClientDetails(clientDetailsService);
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