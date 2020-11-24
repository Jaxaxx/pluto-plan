package com.mine.upmsx.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;


/**
 * 【用户权限关联表】交互层
 *
 * @author jax-li
 * @date 2020-11-18
 */
@Data
@ApiModel(value = "【用户权限关联表】交互层")
public class SysUserRoleDTO {

    /**
     * 主键id
     */
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 角色id
     */
    private Integer roleId;

    /**
     * 是否删除 1 是 0 否
     */
    private Boolean isDeleted;

    /**
     * 创建用户Id
     */
    private Long createUserId;

    /**
     * 创建用户名称
     */
    private String createUserName;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新用户Id
     */
    private Long updateUserId;

    /**
     * 更新用户名称
     */
    private String updateUserName;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

}