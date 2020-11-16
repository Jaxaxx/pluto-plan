package com.mine.system;

import com.mine.common.feign.annotation.EnableMyFeignClients;
import com.mine.common.security.annotation.EnableMyResourceServer;
import com.mine.common.swagger.annotation.EnableMySwagger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableMySwagger
@EnableMyFeignClients
@EnableMyResourceServer
@SpringBootApplication
public class SystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(SystemApplication.class, args);
    }

}
