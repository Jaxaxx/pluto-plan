package com.mine.upmsx.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.Version;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import java.util.Date;


/**
 * 【客户端信息表】实体类
 *
 * @author jax-li
 * @date 2020-11-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_auth_client")
public class SysAuthClient extends Model<SysAuthClient> {

    @ApiModelProperty(value = "客户端ID")
    private String clientId;

    @ApiModelProperty(value = "资源ID集合,多个资源时用逗号(,)分隔")
    private String resourceIds;

    @ApiModelProperty(value = "客户端名称")
    private String clientName;

    @ApiModelProperty(value = "客户端密匙")
    private String clientSecret;

    @ApiModelProperty(value = "客户端申请的权限范围")
    private String scope;

    @ApiModelProperty(value = "客户端支持的grant_type")
    private String authorizedGrantTypes;

    @ApiModelProperty(value = "重定向URI")
    private String webServerRedirectUri;

    @ApiModelProperty(value = "客户端所拥有的Spring Security的权限值，多个用逗号(,)分隔")
    private String authorities;

    @ApiModelProperty(value = "访问令牌有效时间值(单位:秒)")
    private Integer accessTokenValidity;

    @ApiModelProperty(value = "更新令牌有效时间值(单位:秒)")
    private Integer refreshTokenValidity;

    @ApiModelProperty(value = "预留字段")
    private String additionalInformation;

    @ApiModelProperty(value = "用户是否自动Approval操作")
    private String autoapprove;

    @ApiModelProperty(value = "是否删除 1-是 0 否")
    @TableField(fill = FieldFill.INSERT)
    @TableLogic
    private Boolean isDeleted;

    @ApiModelProperty(value = "创建用户Id")
    @TableField(fill = FieldFill.INSERT)
    private Long createUserId;

    @ApiModelProperty(value = "创建用户名称")
    @TableField(fill = FieldFill.INSERT)
    private String createUserName;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新用户Id")
    @TableField(fill = FieldFill.UPDATE)
    private Long updateUserId;

    @ApiModelProperty(value = "更新用户名称")
    @TableField(fill = FieldFill.UPDATE)
    private String updateUserName;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;


    @Override
    protected Serializable pkVal() {
        return this.clientId;
    }

}
