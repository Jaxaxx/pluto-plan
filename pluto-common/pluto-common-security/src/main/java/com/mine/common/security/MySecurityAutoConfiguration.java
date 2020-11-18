package com.mine.common.security;

import com.mine.common.core.constant.SecurityConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;

import javax.sql.DataSource;

/**
 * @author Jax-li
 */
@ComponentScan("com.mine.common.security")
@RequiredArgsConstructor
public class MySecurityAutoConfiguration {

    private final DataSource dataSource;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
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

}
