package com.mine.upmsx.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;


/**
 * 【用户信息表】响应层
 *
 * @author jax-li
 * @date 2020-11-17
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "【用户信息表】返回层")
public class SysUserInfoVO {

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

}
