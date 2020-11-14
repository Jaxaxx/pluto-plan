package com.mine.oauth;

import com.mine.common.feign.annotation.EnableMyFeignClients;
import com.mine.common.swagger.annotation.EnableMySwagger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


/**
 * @author jax-li
 */
@EnableMySwagger
@EnableMyFeignClients
@SpringBootApplication
@EnableDiscoveryClient
public class Auth2Application {

    public static void main(String[] args) {
        SpringApplication.run(Auth2Application.class, args);
    }

}