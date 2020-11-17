package com.mine.common.core.exception;

import com.mine.common.core.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @author Jax-li
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
    public Result<?> handleGlobalException(HttpServletRequest request, Exception ex) {
        log.error("全局异常信息 api=[{}],ex={}", request.getRequestURI(), ex.getMessage(), Objects.isNull(ex) ? "null" : ex);
        Result<Object> result = Result.fail(ex.getMessage());
        if (ex instanceof HttpRequestMethodNotSupportedException) {
            result.setMessage("Request method is not supported");
        } else if (ex instanceof HttpMessageNotReadableException
                || ex instanceof HttpMediaTypeNotSupportedException
                || ex instanceof MissingServletRequestParameterException) {
            result.setMessage("Request parameters are not supported");
        } else if (ex instanceof DataAccessException) {
            result.setMessage(ex.getCause().getMessage());
        }
        return result;
    }
}
