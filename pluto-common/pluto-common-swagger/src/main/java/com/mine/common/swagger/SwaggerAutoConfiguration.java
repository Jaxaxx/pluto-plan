package com.mine.common.swagger;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Lists;
import com.mine.common.swagger.config.SwaggerProperties;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.OAuthBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Jax-li
 */
@Slf4j
@RequiredArgsConstructor
@ComponentScan("com.mine.common.swagger")
public class SwaggerAutoConfiguration {

    final Environment environment;
    final SwaggerProperties swaggerProperties;

    /**
     * 默认的排除路径，排除Spring Boot默认的错误处理路径和端点
     */
//    private static final List<String> DEFAULT_EXCLUDE_PATH = Arrays.asList("/error", "/actuator/**", "/feign/**");
//    private static final String BASE_PATH = "/**";
    private static final String REFERENCE = "spring_oauth";

    @Bean
    public Docket api() {
        log.info("[Swagger Starter] enabled status : {}", swaggerProperties.getEnabled());
        log.debug("[Swagger-Property]::{}", swaggerProperties.toString());
//        List<Predicate<String>> basePath = Lists.newArrayList();
//        basePath.add(PathSelectors.ant(BASE_PATH));
        // exclude-path处理
//        List<Predicate<String>> excludePath = new ArrayList<>();
//        DEFAULT_EXCLUDE_PATH.forEach(path -> excludePath.add(PathSelectors.ant(path)));
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
//                .apis(RequestHandlerSelectors.basePackage(swaggerProperties.getInfo().getBasePackage()))
//                .paths(Predicates.and(Predicates.not(Predicates.or(excludePath)), Predicates.or(basePath)))
                .build()
                .apiInfo(apiInfo())
                .securitySchemes(Collections.singletonList(securityScheme()))
                .securityContexts(Collections.singletonList(securityContext()))
                .enable(swaggerProperties.getEnabled());
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
        GrantType grantType = new ResourceOwnerPasswordCredentialsGrant(swaggerProperties.getInfo().getAccessTokenUri());

        return new OAuthBuilder()
                .name(REFERENCE)
                .grantTypes(Collections.singletonList(grantType))
                .scopes(Arrays.asList(scopes()))
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
                new AuthorizationScope("swagger", "swagger scope is trusted!")
        };
    }

}