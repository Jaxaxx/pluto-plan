package com.mine.oauth.config;

import com.mine.common.security.exception.MyWebResponseExceptionTranslator;
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
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;

/**
 * @author jax-li
 */
@Configuration
@RequiredArgsConstructor
@EnableAuthorizationServer
public class MyAuthorizationServerConfigurerAdapter extends AuthorizationServerConfigurerAdapter {

    private final DefaultTokenServices tokenService;
    private final UserDetailsService myUserDetailsService;
    private final AuthenticationManager authenticationManager;
    private final ClientDetailsService jdbcClientDetailsService;

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
                .tokenServices(tokenService)
                .exceptionTranslator(new MyWebResponseExceptionTranslator())
        ;
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(jdbcClientDetailsService);
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