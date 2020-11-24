package com.mine.upmsx.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mine.common.core.constant.RoleConstants;
import com.mine.upmsx.entity.SysUserRole;
import com.mine.upmsx.mapper.SysUserRoleMapper;
import com.mine.upmsx.service.ISysUserRoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 【用户权限关联表】实现层
 *
 * @author jax-li
 * @date 2020-11-18
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements ISysUserRoleService {

    @Override
    public void add(Long userId) {
        // 注册赋值用户对应角色....
        final SysUserRole entity = new SysUserRole();
        entity.setUserId(userId);
        entity.setRoleId(RoleConstants.ROLE_ADMIN.getId());
        baseMapper.insert(entity);
    }

}
