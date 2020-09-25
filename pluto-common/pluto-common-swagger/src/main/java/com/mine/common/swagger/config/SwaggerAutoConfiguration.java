package com.mine.common.swagger.config;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
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

public class SwaggerAutoConfiguration {

    @Autowired
    private Environment env;

    @Bean
    public SwaggerProperties swaggerProperties() {
        return new SwaggerProperties();
    }

    /**
     * 默认的排除路径，排除Spring Boot默认的错误处理路径和端点
     */
    private static final List<String> DEFAULT_EXCLUDE_PATH = Arrays.asList("/error", "/actuator/**", "/feign/**");
    private static final String BASE_PATH = "/**";

    @Bean
    public Docket api() {

        //noinspection unchecked
        List<Predicate<String>> basePath = new ArrayList();
        basePath.add(PathSelectors.ant(BASE_PATH));

        // exclude-path处理
        List<Predicate<String>> excludePath = new ArrayList<>();
        DEFAULT_EXCLUDE_PATH.forEach(path -> excludePath.add(PathSelectors.ant(path)));

        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage(swaggerProperties().getInfo().getBasePackage()))
                .paths(Predicates.and(Predicates.not(Predicates.or(excludePath)), Predicates.or(basePath)))
                .build()
                .apiInfo(apiInfo())
                .securitySchemes(Collections.singletonList(securityScheme()))
                .securityContexts(Collections.singletonList(securityContext()))
                .enable(swaggerProperties().getEnabled());
    }

    /**
     * @return
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(swaggerProperties().getInfo().getTitle() + "\t" + env.getProperty("spring.application.name"))
                .description(swaggerProperties().getInfo().getDescription())
                .version(swaggerProperties().getInfo().getVersion())
                .license(swaggerProperties().getInfo().getLicense())
                .termsOfServiceUrl(swaggerProperties().getInfo().getTermsIfServiceUrl())
                .build();
    }

    /**
     * 使用密码模式
     *
     * @return
     */
    private SecurityScheme securityScheme() {
        GrantType grantType = new ResourceOwnerPasswordCredentialsGrant(swaggerProperties().getInfo().getAccessTokenUri());

        return new OAuthBuilder()
                .name("spring_oauth")
                .grantTypes(Collections.singletonList(grantType))
                .scopes(Arrays.asList(scopes()))
                .build();
    }

    /**
     * 设置 swagger2 认证的安全上下文
     *
     * @return
     */
    private SecurityContext securityContext() {

        return SecurityContext.builder()
                .securityReferences(Collections.singletonList(new SecurityReference("spring_oauth", scopes())))
                .forPaths(PathSelectors.any())
                .build();
    }

    /**
     * 允许认证的scope
     *
     * @return
     */
    private AuthorizationScope[] scopes() {
        return new AuthorizationScope[]{
                new AuthorizationScope("all", "All scope is trusted!")
        };
    }

}