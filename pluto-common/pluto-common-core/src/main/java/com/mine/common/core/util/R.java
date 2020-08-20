package com.mine.common.core.util;

import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @param <T>
 * @Description 响应信息主体
 * @Author
 * @Date
 */
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class R<T> implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 成功标记
     */
    private static final Integer SUCCESS = 0;
    /**
     * 成功标记
     */
    private static final String SUCCESS_MSG = "success";
    /**
     * 失败标记
     */
    private static final Integer FAIL = 1;
    /**
     * 成功标记
     */
    private static final String FAIL_MSG = "FAIL";


    @Getter
    @Setter
    private int code;

    @Getter
    @Setter
    private String msg;

    @Getter
    @Setter
    private T data;

    public static <T> R<T> ok() {
        return restResult(SUCCESS, SUCCESS_MSG, null);
    }

    public static <T> R<T> ok(T data) {
        return restResult(SUCCESS, SUCCESS_MSG, data);
    }

    public static <T> R<T> ok(String msg, T data) {
        return restResult(SUCCESS, msg, data);
    }

    public static <T> R<T> failed() {
        return restResult(FAIL, FAIL_MSG, null);
    }

    public static <T> R<T> failed(String msg) {
        return restResult(FAIL, msg, null);
    }

    public static <T> R<T> failed(T data) {
        return restResult(FAIL, FAIL_MSG, data);
    }

    public static <T> R<T> failed(String msg, T data) {
        return restResult(FAIL, msg, data);
    }

    private static <T> R<T> restResult(int code, String msg, T data) {
        R<T> apiResult = new R<>();
        apiResult.setCode(code);
        apiResult.setMsg(msg);
        apiResult.setData(data);
        return apiResult;
    }
}
