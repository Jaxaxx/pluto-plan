package com.mine.common.dynamic.gateway.annotation;

import com.mine.common.dynamic.gateway.MyDynamicRouteAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @Description 开启 动态路由
 * @Author
 * @Date
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(MyDynamicRouteAutoConfiguration.class)
public @interface EnableMyDynamicRoute {
}
