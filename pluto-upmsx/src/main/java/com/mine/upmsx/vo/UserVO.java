package com.mine.upmsx.vo;

import com.mine.common.feign.entity.SysUserBaseVO;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserVO {

    private SysUserBaseVO base;

    private SysUserInfoVO info;

    private List<SysRoleVO> roles;

}
