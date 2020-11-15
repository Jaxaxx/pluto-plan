package com.mine.upmsx.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Date;


/**
 * 【客户端用户信息表】交互层
 *
 * @author jax-li
 * @date 2020-11-15
 */
@Data
@ApiModel(value = "【客户端用户信息表】交互层")
public class SysUserClientDTO {

    /**
     * 主键
     */
    private Long id;

    /**
     * 客户端appid
     */
    private String clientId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 备注
     */
    private String note;

    /**
     * 是否删除 0-未删除，1-已删除
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
    private Date createTime;

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
    private Date updateTime;

}