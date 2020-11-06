package com.mine.common.core.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mine.common.core.constant.MyResponseEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @param <T>
 * @Description 响应信息主体
 * @Author
 * @Date
 */
@ToString
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class R<T> implements Serializable {

    /**
     * code。
     */
    @Getter
    public enum GlobalStatus {

        // 成功
        OK(0, "OK"),

        // 失败
        FAIL(1, "FAIL");

        private final int code;
        private final String msg;

        GlobalStatus(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }
    }

    private static final long serialVersionUID = 1L;

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
        return restResult(GlobalStatus.OK.getCode(), GlobalStatus.OK.getMsg(), null);
    }

    public static <T> R<T> ok(T data) {
        return restResult(GlobalStatus.OK.getCode(), GlobalStatus.OK.getMsg(), data);
    }

    public static <T> R<T> ok(String msg, T data) {
        return restResult(GlobalStatus.OK.getCode(), msg, data);
    }

    public static <T> R<T> failed() {
        return restResult(GlobalStatus.FAIL.getCode(), GlobalStatus.FAIL.getMsg(), null);
    }

    public static <T> R<T> failed(String msg) {
        return restResult(GlobalStatus.FAIL.getCode(), msg, null);
    }

    public static <T> R<T> failed(MyResponseEnum responseEnum) {
        return restResult(responseEnum.getStatusCode(), responseEnum.getStatusText(), null);
    }

    public static <T> R<T> failed(T data) {
        return restResult(GlobalStatus.FAIL.getCode(), GlobalStatus.FAIL.getMsg(), data);
    }

    public static <T> R<T> failed(String msg, T data) {
        return restResult(GlobalStatus.FAIL.getCode(), msg, data);
    }

    private static <T> R<T> restResult(int code, String msg, T data) {
        R<T> apiResult = new R<>();
        apiResult.setCode(code);
        apiResult.setMsg(msg);
        apiResult.setData(data);
        return apiResult;
    }
}
