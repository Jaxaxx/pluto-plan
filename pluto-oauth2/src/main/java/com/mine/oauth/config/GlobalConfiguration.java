package com.mine.oauth.config;

import com.mine.common.core.constant.SecurityConstants;
import com.mine.common.security.model.MyUser;
import com.mine.common.security.service.MyJdbcClientDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.DefaultAuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Jax-li
 */
@Component
@RequiredArgsConstructor
public class GlobalConfiguration {

    private final DataSource dataSource;
    private final RedisConnectionFactory redisConnectionFactory;

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

    /**
     * token 增强
     *
     * @return
     */
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

    /**
     * 自定义token生成策略
     *
     * @return
     */
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

    @Primary
    @Bean
    public ClientDetailsService jdbcClientDetailsService() {
        JdbcClientDetailsService clientDetailsService = new MyJdbcClientDetailsService(dataSource);
        clientDetailsService.setInsertClientDetailsSql(SecurityConstants.DEFAULT_INSERT_STATEMENT);
        clientDetailsService.setDeleteClientDetailsSql(SecurityConstants.DEFAULT_DELETE_STATEMENT);
        clientDetailsService.setUpdateClientDetailsSql(SecurityConstants.DEFAULT_UPDATE_STATEMENT);
        clientDetailsService.setUpdateClientSecretSql(SecurityConstants.DEFAULT_UPDATE_SECRET_STATEMENT);
        clientDetailsService.setSelectClientDetailsSql(SecurityConstants.DEFAULT_SELECT_STATEMENT);
        clientDetailsService.setFindClientDetailsSql(SecurityConstants.DEFAULT_FIND_STATEMENT);
        return clientDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
