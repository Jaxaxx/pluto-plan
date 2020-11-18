package com.mine.upmsx.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mine.upmsx.entity.SysRole;
import com.mine.upmsx.mapper.SysRoleMapper;
import com.mine.upmsx.service.ISysRoleService;
import com.mine.upmsx.vo.SysRoleVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 【角色表】实现层
 *
 * @author jax-li
 * @date 2020-11-18
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {

    @Override
    public SysRoleVO getByUserId(Long userId) {
        return baseMapper.selectRoleByUserId(userId);
    }
}
