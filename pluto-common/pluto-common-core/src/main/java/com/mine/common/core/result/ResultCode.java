package com.mine.common.core.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author jax-li
 */
@Getter
@AllArgsConstructor
public enum ResultCode {

    // 成功
    OK(0,"ok"),
    // 失败
    FAIL(1,"fail");

    private final Integer code;
    private final String msg;

}
