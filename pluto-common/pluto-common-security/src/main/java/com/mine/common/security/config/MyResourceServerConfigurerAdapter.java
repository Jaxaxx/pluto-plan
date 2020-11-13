package com.mine.common.security.config;

import com.mine.common.security.converter.MyUserConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.oauth2.OAuth2ClientProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.security.web.AuthenticationEntryPoint;

/**
 * @author Jax-li
 */
@RequiredArgsConstructor
public class MyResourceServerConfigurerAdapter extends ResourceServerConfigurerAdapter {

    private final ResourceServerProperties resourceServerProperties;
    private final OAuth2ClientProperties oAuth2ClientProperties;
    private final RedisConnectionFactory redisConnectionFactory;
    private final PermitAllUrlProperties permitAllUrlProperties;
    private final AuthenticationEntryPoint myResourceAuthExceptionEntryPoint;

    @Bean
    public TokenStore redisTokenStore() {
        return new RedisTokenStore(redisConnectionFactory);
    }

    /**
     * remoteTokenServices.setAccessTokenConverter(accessTokenConverter);
     * 配置转换器，否则authentication.getPrincipal()获取到的只有用户名
     */
    @Bean
    @Primary
    public RemoteTokenServices tokenService() {
        DefaultAccessTokenConverter accessTokenConverter = new DefaultAccessTokenConverter();
        accessTokenConverter.setUserTokenConverter(new MyUserConverter());

        RemoteTokenServices remoteTokenServices = new RemoteTokenServices();
        remoteTokenServices.setAccessTokenConverter(accessTokenConverter);
        remoteTokenServices.setClientId(oAuth2ClientProperties.getClientId());
        remoteTokenServices.setClientSecret(oAuth2ClientProperties.getClientSecret());
        remoteTokenServices.setCheckTokenEndpointUrl(resourceServerProperties.getTokenInfoUri());
        return remoteTokenServices;
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        // token校验失败异常处理端点
        resources.authenticationEntryPoint(myResourceAuthExceptionEntryPoint);
        resources.tokenServices(tokenService());
    }

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        //所有请求必须认证通过
        httpSecurity.authorizeRequests()
                .antMatchers(permitAllUrlProperties.getIgnoreUrls().stream().toArray(String[]::new))
                .permitAll()
                .and()
                .headers()
                .frameOptions()
                .disable()
                .and()
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .csrf()
                .disable()
        ;

    }

}