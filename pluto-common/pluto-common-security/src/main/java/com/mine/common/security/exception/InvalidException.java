package com.mine.common.security.exception;

import org.springframework.http.HttpStatus;

/**
 * @author lengleng
 * @date 2018/7/8
 */
public class InvalidException extends MyAuth2Exception {

    public InvalidException(String msg, Throwable t) {
        super(msg);
    }

    @Override
    public String getOAuth2ErrorCode() {
        return "invalid_exception";
    }

    @Override
    public int getHttpErrorCode() {
        return HttpStatus.UPGRADE_REQUIRED.value();
    }

}