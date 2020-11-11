//package com.mine.common.gateway.config;
//
//import org.apache.commons.lang.StringUtils;
//import org.springframework.cloud.gateway.filter.GatewayFilter;
//import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
//import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//
///**
// * 解决swagger try-it-out 应用名称丢失问题
// */
//@Component
//public class SwaggerHeaderFilter extends AbstractGatewayFilterFactory {
//    private static final String HEADER_NAME = "X-Forwarded-Prefix";
//
//    @Override
//    public GatewayFilter apply(Object config) {
//        return (exchange, chain) -> {
//            ServerHttpRequest request = exchange.getRequest();
//            String path = request.getURI().getPath();
//            if (!StringUtils.endsWithIgnoreCase(path, MySwaggerResourceProvider.SWAGGER2URL)) {
//                return chain.filter(exchange);
//            }
//
//            String basePath = path.substring(0, path.lastIndexOf(MySwaggerResourceProvider.SWAGGER2URL));
//
//            ServerHttpRequest newRequest = request.mutate().header(HEADER_NAME, basePath).build();
//            ServerWebExchange newExchange = exchange.mutate().request(newRequest).build();
//            return chain.filter(newExchange);
//        };
//    }
//}