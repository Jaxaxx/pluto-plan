package com.mine.common.gateway.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Jax-li
 */
public class MySwaggerResourceProvider implements SwaggerResourcesProvider {
    /**
     * 网关应用名称
     */
    @Value("${spring.application.name}")
    private String self;
    /**
     * swagger2默认的url后缀
     */
    private static final String SWAGGER2URL = "/v2/api-docs";
    /**
     * 网关路由
     */
    private final RouteLocator routeLocator;

    @Autowired
    public MySwaggerResourceProvider(RouteLocator routeLocator) {
        this.routeLocator = routeLocator;
    }

    @Override
    public List<SwaggerResource> get() {
        List<SwaggerResource> resources = new ArrayList<>();
        List<String> routeHosts = new ArrayList<>();
        routeLocator
                .getRoutes()
                .filter(route -> route.getUri().getHost() != null)
                .filter(route -> !self.equalsIgnoreCase(route.getUri().getHost()))
                .subscribe(route -> routeHosts.add(route.getUri().getHost().toLowerCase()));

        // 记录已经添加过的server，存在同一个应用注册了多个服务在注册中心
        Set<String> instances = new HashSet<>();
        routeHosts.forEach(instance -> {
            // 拼接url，样式为/serviceId/v2/api-info，当网关调用这个接口时，会自动通过负载均衡寻找对应的主机
            String url = "/" + instance + SWAGGER2URL;
            if (!instances.contains(url)) {
                instances.add(url);
                SwaggerResource swaggerResource = new SwaggerResource();
                swaggerResource.setUrl(url);
                swaggerResource.setName(instance);
                resources.add(swaggerResource);
            }
        });
        return resources;
    }
}