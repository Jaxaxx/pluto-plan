package com.mine.common.security.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

/**
 * @Description
 * @Author
 * @Date
 */
@JsonSerialize(using = MyAuth2ExceptionSerializer.class)
public class MyAuth2Exception extends OAuth2Exception {

    @Getter
    private String errorCode;

    public MyAuth2Exception(String msg, String t) {
        super(msg);
    }

    public MyAuth2Exception(String msg) {
        super(msg);
        this.errorCode = errorCode;
    }
}