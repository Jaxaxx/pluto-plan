package com.mine.common.security.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

/**
 * @Description
 * @Author
 * @Date
 */
@JsonSerialize(using = MyAuth2ExceptionSerializer.class)
public class MyAuth2Exception extends OAuth2Exception {

    /**
     *
     */
    private static final long serialVersionUID = 8534009250423816903L;

    public MyAuth2Exception(String msg, Throwable t) {
        super(msg, t);
    }

    public MyAuth2Exception(String msg) {
        super(msg);
    }

}