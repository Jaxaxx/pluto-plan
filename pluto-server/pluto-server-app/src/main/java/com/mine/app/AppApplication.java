package com.mine.app;

import com.mine.common.feign.annotation.EnableMyFeignClients;
import com.mine.common.security.annotation.EnableMyResourceServer;
import com.mine.common.swagger.annotation.EnableMySwagger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@EnableMySwagger
@EnableMyResourceServer
@EnableMyFeignClients
public class AppApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppApplication.class, args);
    }

}
