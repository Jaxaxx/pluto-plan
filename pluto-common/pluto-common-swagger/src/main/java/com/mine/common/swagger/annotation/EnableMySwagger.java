package com.mine.common.swagger.annotation;

import com.mine.common.swagger.SwaggerAutoConfiguration;
import org.springframework.context.annotation.Import;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.lang.annotation.*;

/**
 * @author Jax-li
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(SwaggerAutoConfiguration.class)
@EnableOpenApi
public @interface EnableMySwagger {

}