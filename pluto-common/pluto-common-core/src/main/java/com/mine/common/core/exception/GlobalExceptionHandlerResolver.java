package com.mine.common.core.exception;

import com.mine.common.core.util.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @author LiMing
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandlerResolver {

    /**
     * 全局异常.
     *
     * @param e the e
     * @return R
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    public R handleGlobalException(HttpServletRequest request, Exception e) {
        log.error("全局异常信息 api=[{}],ex={}", request.getRequestURI(), e.getMessage(), e);
        R<Object> objectR = R.failed(e.getMessage());
        if (e instanceof HttpRequestMethodNotSupportedException) {
            objectR.setMsg("Request method is not supported");
        } else if (e instanceof HttpMessageNotReadableException
                || e instanceof HttpMediaTypeNotSupportedException
                || e instanceof MissingServletRequestParameterException) {
            objectR.setMsg("Request parameters are not supported");
        }
        return objectR;
    }
}
