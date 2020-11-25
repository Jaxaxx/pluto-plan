package com.mine.oauth.config;

import com.mine.common.security.config.PermitAllUrlProperties;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author jax-li
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class MySecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

    private final UserDetailsService myUserDetailsService;
    private final PermitAllUrlProperties permitAllUrlProperties;

    @Bean
    @Override
    @SneakyThrows
    public AuthenticationManager authenticationManagerBean() {
        return super.authenticationManagerBean();
    }

    @Override
    @SneakyThrows
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.userDetailsService(myUserDetailsService);
    }

    /**
     * 允许匿名访问所有接口 主要是 oauth/swagger/actuator
     *
     * @param http
     */
    @Override
    @SneakyThrows
    protected void configure(HttpSecurity http) {
        http.authorizeRequests()
                .antMatchers(permitAllUrlProperties.getIgnoreUrls().toArray(new String[0]))
                .permitAll()
                .anyRequest()
                .authenticated()
        ;
    }

    /**
     * 放行静态资源
     *
     * @param web
     */
    @Override
    @SneakyThrows
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/css/**");
    }
}