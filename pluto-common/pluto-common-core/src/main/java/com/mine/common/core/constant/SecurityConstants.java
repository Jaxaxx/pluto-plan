
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
     * name
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

    String OAUTH_TABLE_NAME = "sys_oauth_client_details";

    String CLIENT_FIELDS_FOR_UPDATE = "resource_ids, scope, "
            + "authorized_grant_types, web_server_redirect_uri, authorities, access_token_validity, "
            + "refresh_token_validity, additional_information, autoapprove";

    String CLIENT_FIELDS = "client_secret, " + CLIENT_FIELDS_FOR_UPDATE;

    String CLIENT_FIELDS_SELECT = "CONCAT('{noop}',client_secret) as client_secret, " + CLIENT_FIELDS_FOR_UPDATE;

    String BASE_FIND_STATEMENT = "select client_id, " + CLIENT_FIELDS_SELECT + " from " + OAUTH_TABLE_NAME;

    String DEFAULT_FIND_STATEMENT = BASE_FIND_STATEMENT + " order by client_id";

    String DEFAULT_SELECT_STATEMENT = BASE_FIND_STATEMENT + " where client_id = ?";

    String DEFAULT_INSERT_STATEMENT = "insert into " + OAUTH_TABLE_NAME + " (" + CLIENT_FIELDS
            + ", client_id) values (?,?,?,?,?,?,?,?,?,?,?)";

    String DEFAULT_UPDATE_STATEMENT = "update " + OAUTH_TABLE_NAME + " " + "set "
            + CLIENT_FIELDS_FOR_UPDATE.replaceAll(", ", "=?, ") + "=? where client_id = ?";

    String DEFAULT_UPDATE_SECRET_STATEMENT = "update " + OAUTH_TABLE_NAME + " "
            + "set client_secret = ? where client_id = ?";

    String DEFAULT_DELETE_STATEMENT = "delete from " + OAUTH_TABLE_NAME + " where client_id = ?";
    /**
     * {bcrypt} 加密的特征码
     */
    String BCRYPT = "{bcrypt}";

}
