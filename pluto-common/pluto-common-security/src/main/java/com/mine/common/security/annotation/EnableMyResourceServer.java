package com.mine.common.security.annotation;

import com.mine.common.security.config.MyResourceServerAutoConfiguration;
import com.mine.common.security.config.MySecurityBeanDefinitionRegister;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Import({MyResourceServerAutoConfiguration.class,MySecurityBeanDefinitionRegister.class})
public @interface EnableMyResourceServer {

}
