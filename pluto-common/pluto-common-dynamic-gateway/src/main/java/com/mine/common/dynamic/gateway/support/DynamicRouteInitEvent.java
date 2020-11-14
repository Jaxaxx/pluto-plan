package com.mine.common.dynamic.gateway.support;

import org.springframework.context.ApplicationEvent;

/**
 * @Description 路由初始化事件
 * @Author
 * @Date
 */
public class DynamicRouteInitEvent extends ApplicationEvent {
	/**
	 *
	 */
	private static final long serialVersionUID = 1130435432793239420L;

	public DynamicRouteInitEvent(Object source) {
		super(source);
	}
}
