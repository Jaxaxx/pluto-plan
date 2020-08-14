package com.mine.common.feign.annotation;

import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@EnableHystrix
@EnableFeignClients(basePackages = {"com.mine.common.feign.api"})
public @interface EnableMyFeignClients {
}
