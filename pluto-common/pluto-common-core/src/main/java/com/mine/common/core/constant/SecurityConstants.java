
package com.mine.common.core.constant;

/**
 * @Description
 * @Author
 * @Date
 */
public interface SecurityConstants {
    /**
     * oauth 相关前缀
     */
    String OAUTH_PREFIX = "oauth:";
    /**
     * app短信登录URL
     */
    String SMS_APP_TOKEN_URL = "/mobile/token/app/sms";
    /**
     * 自定义登录URL
     */
    String MOBILE_TOKEN_URL = "/mobile/token/**";

    /**
     * 客户端模式
     */
    String CLIENT_CREDENTIALS = "client_credentials";

    /**
     * 用户ID字段
     */
    String DETAILS_USER_ID = "user_id";
    /**
     * 用户名字段
     */
    String DETAILS_USERNAME = "user_name";
    /**
     * uuid
     */
    String DETAILS_NAME = "name";
    /**
     * phone
     */
    String DETAILS_PHONE = "phone";
    /**
     * 协议字段
     */
    String DETAILS_LICENSE = "license";
    /**
     * 项目的license
     */
    String MY_LICENSE = "made by Jax-li";

}
