package com.mine.common.swagger;

import com.google.common.collect.Lists;
import com.mine.common.swagger.config.SwaggerProperties;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import springfox.documentation.builders.*;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * @author Jax-li
 */
@Slf4j
@RequiredArgsConstructor
@ComponentScan("com.mine.common.swagger")
public class SwaggerAutoConfiguration {

    final Environment environment;
    final SwaggerProperties swaggerProperties;

    private static final String REFERENCE = "spring_oauth";

    @Bean
    public Docket api() {
        log.info("[Swagger Starter] enabled status : {}", swaggerProperties.getEnabled());
        log.debug("[Swagger-Property]::{}", swaggerProperties.toString());
        Docket docket = new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(Collections.singletonList(securityScheme()))
                .securityContexts(Collections.singletonList(securityContext()))
                .enable(swaggerProperties.getEnabled());
        return docket;
    }

    /**
     * @return ApiInfo
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(swaggerProperties.getInfo().getTitle() + "\t" + environment.getProperty("spring.application.name"))
                .description(swaggerProperties.getInfo().getDescription())
                .version(swaggerProperties.getInfo().getVersion())
                .license(swaggerProperties.getInfo().getLicense())
                .termsOfServiceUrl(swaggerProperties.getInfo().getTermsIfServiceUrl())
                .build();
    }

    /**
     * 使用密码模式
     *
     * @return SecurityScheme
     */
    private SecurityScheme securityScheme() {
        return OAuth2Scheme.OAUTH2_PASSWORD_FLOW_BUILDER
                .tokenUrl(swaggerProperties.getInfo().getAccessTokenUri())
                .name(REFERENCE)
                .scopes(Lists.newArrayList(scopes()))
                .build();
    }

    /**
     * 设置 swagger2 认证的安全上下文
     *
     * @return SecurityContext
     */
    private SecurityContext securityContext() {

        return SecurityContext.builder()
                .securityReferences(Collections.singletonList(new SecurityReference(REFERENCE, scopes())))
                .forPaths(PathSelectors.any())
                .build();
    }

    /**
     * 允许认证的scope
     *
     * @return AuthorizationScope[]
     */
    private AuthorizationScope[] scopes() {
        return new AuthorizationScope[]{
                new AuthorizationScope("all", "All scope is trusted!")
        };
    }

}