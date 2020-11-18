package com.mine.common.security;

import com.mine.common.core.constant.SecurityConstants;
import com.mine.common.security.converter.MyUserConverter;
import com.mine.common.security.model.MyUser;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.oauth2.OAuth2ClientProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.*;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Jax-li
 */
@ComponentScan("com.mine.common.security")
@RequiredArgsConstructor
public class MySecurityAutoConfiguration {

    private final ResourceServerProperties resourceServerProperties;
    private final OAuth2ClientProperties oAuth2ClientProperties;
    private final RedisConnectionFactory redisConnectionFactory;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    /**
     * redis token 规则
     *
     * @return
     */
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
     * @return
     */
    @Bean
    public TokenEnhancer tokenEnhancer() {
        return (accessToken, authentication) -> {
            if (SecurityConstants.CLIENT_CREDENTIALS
                    .equals(authentication.getOAuth2Request().getGrantType())) {
                return accessToken;
            }
            final Map<String, Object> additionalInfo = new HashMap<String, Object>(16);
            MyUser myUser = (MyUser) authentication.getUserAuthentication().getPrincipal();
            additionalInfo.put(SecurityConstants.DETAILS_USER_ID, myUser.getId());
            additionalInfo.put(SecurityConstants.DETAILS_PHONE, myUser.getPhone());
            additionalInfo.put(SecurityConstants.DETAILS_LICENSE, SecurityConstants.MY_LICENSE);
            additionalInfo.put(SecurityConstants.DETAILS_NAME, myUser.getName());
            additionalInfo.put("code", 123456);
            ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
            return accessToken;
        };
    }

    /**
     * remoteTokenServices.setAccessTokenConverter(accessTokenConverter);
     * 配置转换器，否则authentication.getPrincipal()获取到的只有用户名
     */
    @Bean
    @Primary
    public ResourceServerTokenServices tokenService() {
        DefaultAccessTokenConverter accessTokenConverter = new DefaultAccessTokenConverter();
        accessTokenConverter.setUserTokenConverter(new MyUserConverter());

        RemoteTokenServices remoteTokenServices = new RemoteTokenServices();
        remoteTokenServices.setAccessTokenConverter(accessTokenConverter);
        remoteTokenServices.setClientId(oAuth2ClientProperties.getClientId());
        remoteTokenServices.setClientSecret(oAuth2ClientProperties.getClientSecret());
        remoteTokenServices.setCheckTokenEndpointUrl(resourceServerProperties.getTokenInfoUri());
        return remoteTokenServices;
    }

}
