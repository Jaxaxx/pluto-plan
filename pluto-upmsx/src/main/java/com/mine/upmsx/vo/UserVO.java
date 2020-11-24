package com.mine.upmsx.vo;

import com.mine.common.feign.entity.SysUserBaseVO;
import lombok.Data;

import java.util.List;

@Data
public class UserVO {

    private SysUserBaseVO base;

    private SysUserInfoVO info;

    private List<SysRoleVO> roles;

}
