package com.mine.oauth;

import com.mine.common.feign.annotation.EnableMyFeignClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 * @author jax-li
 */
@EnableSwagger2
@EnableMyFeignClients
@SpringBootApplication
@EnableDiscoveryClient
public class Auth2Application {

    public static void main(String[] args) {
        SpringApplication.run(Auth2Application.class, args);
    }

}