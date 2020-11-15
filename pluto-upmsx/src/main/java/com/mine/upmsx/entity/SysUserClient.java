package com.mine.upmsx.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * 【客户端用户信息表】实体类
 *
 * @author jax-li
 * @date 2020-11-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_user_client")
public class SysUserClient extends Model<SysUserClient> {


    @ApiModelProperty(value = "主键")
    @TableId
    private Long id;

    @ApiModelProperty(value = "客户端appid")
    private String clientId;

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "备注")
    private String note;

    @ApiModelProperty(value = "是否删除 0-未删除，1-已删除")
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
        return this.id;
    }

}
