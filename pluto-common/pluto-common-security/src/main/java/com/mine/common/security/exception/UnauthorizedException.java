package com.mine.common.security.exception;

import org.springframework.http.HttpStatus;

/**
 * @author lengleng
 * @date 2018/7/8
 */
public class UnauthorizedException extends MyAuth2Exception {

    public UnauthorizedException(String msg, Throwable t) {
        super(msg);
    }

    @Override
    public String getOAuth2ErrorCode() {
        return "unauthorized";
    }

    @Override
    public int getHttpErrorCode() {
        return HttpStatus.UNAUTHORIZED.value();
    }

}