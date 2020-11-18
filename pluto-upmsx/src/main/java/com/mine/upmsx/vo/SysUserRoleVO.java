package com.mine.upmsx.vo;

import com.mine.upmsx.dto.SysUserRoleDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;


/**
 * 【用户权限关联表】响应层
 *
 * @author jax-li
 * @date 2020-11-18
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "【用户权限关联表】返回层")
public class SysUserRoleVO {

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
