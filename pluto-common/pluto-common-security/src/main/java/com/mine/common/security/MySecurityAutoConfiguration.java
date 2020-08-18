package com.mine.common.security;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.mine.common.security")
@AutoConfigureBefore(MySecurityAutoConfiguration.class)
public class MySecurityAutoConfiguration {



}
