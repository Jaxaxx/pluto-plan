package com.mine.upmsx.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @description: 【日志表】交互层
 * @author: Generated by jax-li
 * @create: 2020-08-20
 */
@Data
@ApiModel(value = "【日志表】交互层")
public class SysLogDTO {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "日志类型")
    private Integer type;

    @ApiModelProperty(value = "日志标题")
    private String title;

    private String serviceId;

    @ApiModelProperty(value = "操作IP地址")
    private String remoteAddr;

    @ApiModelProperty(value = "用户代理")
    private String userAgent;

    @ApiModelProperty(value = "请求URI")
    private String requestUri;

    @ApiModelProperty(value = "操作方式")
    private String method;

    @ApiModelProperty(value = "操作提交的数据")
    private String params;

    @ApiModelProperty(value = "执行时间")
    private Long time;

    @ApiModelProperty(value = "异常信息")
    private String exception;

    @ApiModelProperty(value = "备注")
    private String note;

    @ApiModelProperty(value = "是否删除：0-否，1-是")
    private Integer isDeleted;

    private Long createUserId;

    @ApiModelProperty(value = "创建用户名称")
    private String createUserName;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    private Long updateUserId;

    @ApiModelProperty(value = "更新用户名称")
    private String updateUserName;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

}
