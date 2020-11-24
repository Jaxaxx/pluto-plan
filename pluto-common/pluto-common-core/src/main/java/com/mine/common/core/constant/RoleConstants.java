package com.mine.common.core.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 角色常量枚举
 */
@Getter
@AllArgsConstructor
public enum  RoleConstants {

    ROLE_ADMIN(1, "ADMIN");

    private final int id;
    private final String code;

}
