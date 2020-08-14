package com.mine.common.core.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @param <T>
 * @Description 响应信息主体
 * @Author
 * @Date
 */
@Data
@Builder
@AllArgsConstructor
@Accessors(chain = true)
public class R<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final Integer OK_CODE = 0;
    private static final String OK_MSG = "ok";
    private static final Integer FAIL_CODE = 1;
    private static final String FAIL_MSG = "fail";

    private Integer code = OK_CODE;
    private String msg = OK_MSG;
    private T data;

    public R() {
        super();
    }

    public R(T data) {
        super();
        this.data = data;
    }

    public R ok() {
        return new R();
    }

    public R ok(T data) {
        return new R(data);
    }

    public R fail(T data) {
        return new R().fail("fail", data);
    }

    public R fail(String msg) {
        return new R().fail(msg);
    }

    public R fail(String msg, T data) {
        return new R(FAIL_CODE, msg, data);
    }
}
