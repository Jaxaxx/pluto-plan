package com.mine.upmsx.vo;

import com.mine.upmsx.dto.SysRoleDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;


/**
 * 【角色表】响应层
 *
 * @author jax-li
 * @date 2020-11-18
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "【角色表】返回层")
public class SysRoleVO {

    /**
     * 自增id
     */
    private Integer id;

    /**
     * 角色code
     */
    private String code;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 用户类型：1-xx 2-xx
     */
    private Integer type;

    /**
     * 角色等级
     */
    private Integer level;

    /**
     * 父级id
     */
    private Integer parentId;

    /**
     * 备注
     */
    private String note;

}
