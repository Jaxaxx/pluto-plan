package com.mine.common.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.web.AuthenticationEntryPoint;

/**
 * @author Jax-li
 */
@RequiredArgsConstructor
public class MyResourceServerConfigurerAdapter extends ResourceServerConfigurerAdapter {

    private final AuthenticationEntryPoint myResourceAuthExceptionEntryPoint;
    private final ResourceServerTokenServices tokenService;
    private final PermitAllUrlProperties permitAllUrlProperties;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        // token校验失败异常处理端点
        resources.authenticationEntryPoint(myResourceAuthExceptionEntryPoint);
        resources.tokenServices(tokenService);
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