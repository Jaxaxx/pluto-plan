package com.mine.common.dynamic.gateway.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.cloud.gateway.route.RouteDefinition;

import java.io.Serializable;

/**
 * @Description 扩展此类支持序列化a
 * 		See RouteDefinition.class
 * @Author
 * @Date
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class RouteDefinitionVO extends RouteDefinition implements Serializable {

	/**
	 * 路由前缀
	 */
	private String gatewayPrefix;
	/**
	 * 路由名称
	 */
	private String routeName;
}
