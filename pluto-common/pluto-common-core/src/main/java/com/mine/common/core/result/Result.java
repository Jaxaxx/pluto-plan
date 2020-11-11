package com.mine.common.core.result;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.mine.common.core.constant.ResultCode;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 响应信息主体
 *
 * @param <T>
 * @author jax-li
 */
@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 是否成功：true，成功；false，失败
     */
    private Boolean success;

    /**
     * 成功时返回 ok，失败时返回具体错误消息
     */
    private String message;

    /**
     * <P>业务代码</P>
     * 成功为0,失败时返回具体错误码
     */
    private Integer code;

    /**
     * 成功时具体返回值，失败时为 null
     */
    private T data;

    public static <T> Result<T> ok() {
        return ok(null);
    }

    public static <T> Result<T> ok(T data) {
        return result(Boolean.TRUE, ResultCode.OK.getCode(), ResultCode.OK.getMsg(), data);
    }

    public static <T> Result<T> fail(Throwable ex) {
        return fail(ex.getMessage());
    }

    public static <T> Result<T> fail(String message) {
        return fail(ResultCode.FAIL.getCode(), message);
    }

    public static <T> Result<T> fail(Integer code, String message) {
        return result(Boolean.FALSE, code, message, null);
    }

    private static <T> Result<T> result(Boolean success, int code, String message, T data) {
        Result<T> result = new Result<>();
        result.setSuccess(success);
        result.setCode(code);
        result.setMessage(message);
        result.setData(data);
        return result;
    }

    private Result() {
    }
}
