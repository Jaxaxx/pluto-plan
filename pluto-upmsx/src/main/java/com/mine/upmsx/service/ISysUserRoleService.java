package com.mine.upmsx.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mine.upmsx.entity.SysUserRole;

/**
 * 【用户权限关联表】代理层
 *
 * @author jax-li
 * @date 2020-11-18
 */
public interface ISysUserRoleService extends IService<SysUserRole> {

    void add(Long userId);
}
