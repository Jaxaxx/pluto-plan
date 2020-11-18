package com.mine.upmsx.vo;

import com.mine.common.feign.entity.SysUserBaseVO;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserVO {

    private SysUserBaseVO base;

    private SysUserInfoVO info;

    private SysRoleVO role;

}
