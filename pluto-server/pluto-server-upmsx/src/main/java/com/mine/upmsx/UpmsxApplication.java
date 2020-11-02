package com.mine.upmsx;

import com.mine.common.feign.annotation.EnableMyFeignClients;
import com.mine.common.security.annotation.EnableMyResourceServer;
import com.mine.common.swagger.annotation.EnableMySwagger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


/**
 * @author LiMing
 */
@EnableMySwagger
@EnableMyFeignClients
@EnableMyResourceServer
@SpringBootApplication
@EnableDiscoveryClient
public class UpmsxApplication {

    public static void main(String[] args) {
        SpringApplication.run(UpmsxApplication.class, args);
    }

}
