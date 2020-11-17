package com.mine.upmsx.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;


/**
 * 【用户信息表】交互层
 *
 * @author jax-li
 * @date 2020-11-17
 */
@Data
@ApiModel(value = "【用户信息表】交互层")
public class SysUserInfoDTO {

    /**
     * 主键
     */
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 身份证号码
     */
    private String idCard;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 性别：1-男 2-女 0-未知
     */
    private Integer gender;

    /**
     * 生日
     */
    private LocalDate birthday;

    /**
     * 微信号
     */
    private String wechat;

    /**
     * 头像图片 uri
     */
    private String avatar;

    /**
     * 移动电话号码
     */
    private String mobile;

    /**
     * 地址：省
     */
    private Integer province;

    /**
     * 地址：市
     */
    private Integer city;

    /**
     * 地址：区
     */
    private Integer district;

    /**
     * 地址：具体地址
     */
    private String address;

    /**
     * 备注
     */
    private String note;

    /**
     * 是否删除
     */
    private Boolean isDeleted;

    /**
     * 创建人id
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
     * 更新人id
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