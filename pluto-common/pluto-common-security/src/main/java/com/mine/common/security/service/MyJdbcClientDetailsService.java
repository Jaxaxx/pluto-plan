package com.mine.common.security.service;

import lombok.SneakyThrows;
import org.springframework.security.oauth2.common.exceptions.InvalidClientException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;

import javax.sql.DataSource;

public class MyJdbcClientDetailsService extends JdbcClientDetailsService {

    public MyJdbcClientDetailsService(DataSource dataSource) {
        super(dataSource);
    }

    /**
     * 重写原生方法
     *
     * @param clientId
     * @return
     * @throws InvalidClientException
     */
    @Override
    @SneakyThrows
    public ClientDetails loadClientByClientId(String clientId) {
        return super.loadClientByClientId(clientId);
    }
}
