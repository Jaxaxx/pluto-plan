package com.mine.common.swagger.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "swagger")
public class SwaggerProperties {

    private Boolean enabled = true;

    private Info info = new Info();

    @Data
    @ConfigurationProperties(prefix = "swagger.info")
    public static class Info {
        private String title = " swagger of ";
        private String description = "the swagger2 document";
        private String version = "1.0.0";
        private String license = "By Mercedes Jax";
        private String termsIfServiceUrl = "https://github.com/haughty945/";
        private String basePackage = "com.mine";
        private String accessTokenUri = "http://pluto-plan:9999/auth/oauth/token}";
    }


}
