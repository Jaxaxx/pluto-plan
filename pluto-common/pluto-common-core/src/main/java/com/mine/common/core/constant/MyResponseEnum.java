package com.mine.common.core.constant;

import org.springframework.http.HttpStatus;

/**
 * 认证返回
 *
 * @author LiMing
 */
public enum MyResponseEnum {

    /**
     * 授权失败,未授权
     */
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase()),
    /**
     * 服务器内部错误
     */
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());

    private final Integer statusCode;
    private final String statusText;

    MyResponseEnum(Integer statusCode, String statusText) {
        this.statusCode = statusCode;
        this.statusText = statusText;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public String getStatusText() {
        return statusText;
    }
}
