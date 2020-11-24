package com.mine.oauth.config;

import com.mine.common.core.constant.SecurityConstants;
import com.mine.common.security.converter.MyUserConverter;
import com.mine.common.security.model.MyUser;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.token.*;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author jax-li
 */
@Configuration
@RequiredArgsConstructor
@EnableAuthorizationServer
@SuppressWarnings({"rawtypes", "unchecked"})
public class MyAuthorizationServerConfigurerAdapter extends AuthorizationServerConfigurerAdapter {

    // Factory Bean
    // ======================================================

    private final UserDetailsService myUserDetailsService;
    private final AuthenticationManager authenticationManager;
    private final WebResponseExceptionTranslator myWebResponseExceptionTranslator;
    private final ClientDetailsService jdbcClientDetailsService;

    // expand Bean
    // ======================================================

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
            ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
            return accessToken;
        };
    }

    /**
     * redis token 规则
     *
     * @return
     */
    @Bean
    public TokenStore jwtTokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        final JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey("pluto-plan");
        return converter;
    }

    // function
    // ======================================================

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {

        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancer(), jwtAccessTokenConverter()));

        endpoints.allowedTokenEndpointRequestMethods(HttpMethod.POST, HttpMethod.GET)
                .authenticationManager(authenticationManager)
                .userDetailsService(myUserDetailsService)
                .tokenStore(jwtTokenStore())
                .accessTokenConverter(jwtAccessTokenConverter())
                .tokenEnhancer(tokenEnhancerChain)
                .exceptionTranslator(myWebResponseExceptionTranslator)
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