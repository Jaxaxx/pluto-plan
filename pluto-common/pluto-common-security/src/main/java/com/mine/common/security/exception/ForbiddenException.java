package com.mine.common.security.exception;

import org.springframework.http.HttpStatus;

/**
 * @Description
 * @Author
 * @Date
 */
public class ForbiddenException extends MyAuth2Exception {

    public ForbiddenException(String msg, Throwable t) {
        super(msg);
    }

    @Override
    public String getOAuth2ErrorCode() {
        return "access_denied";
    }

    @Override
    public int getHttpErrorCode() {
        return HttpStatus.FORBIDDEN.value();
    }

}