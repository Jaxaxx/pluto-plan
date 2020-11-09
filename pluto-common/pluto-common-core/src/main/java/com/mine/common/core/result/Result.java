package com.mine.common.core.result;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.mine.common.core.constant.ResultCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * 响应信息主体
 *
 * @param <T>
 * @author jax-li
 */
@ToString
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

    /**
     * 时间戳
     */
    private Long timestamp;

    public static <T> Result<T> ok() {
        return ok(null);
    }

    public static <T> Result<T> ok(T data) {
        return result(Boolean.TRUE, ResultCode.OK.getMsg(), ResultCode.OK.getCode(), data);
    }

    public static <T> Result<T> fail(Throwable ex) {
        return fail(ex.getMessage());
    }

    public static <T> Result<T> fail(String message) {
        return fail(message, ResultCode.FAIL.getCode());
    }

    public static <T> Result<T> fail(String message, Integer code) {
        return result(Boolean.FALSE, message, code, null);
    }

    private static <T> Result<T> result(Boolean success, String message, int code, T data) {
        Result<T> result = new Result<>();
        result.setSuccess(success);
        result.setMessage(message);
        result.setCode(code);
        result.setData(data);
        result.setTimestamp(LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli());
        return result;
    }

    private void setSuccess(Boolean success) {
        this.success = success;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private void setCode(Integer code) {
        this.code = code;
    }

    private void setData(T data) {
        this.data = data;
    }

    private void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
