package com.mine.common.dynamic.gateway.support;

import org.springframework.context.ApplicationEvent;

/**
 * @Description 路由初始化事件
 * @Author
 * @Date
 */
public class DynamicRouteInitEvent extends ApplicationEvent {
	public DynamicRouteInitEvent(Object source) {
		super(source);
	}
}
