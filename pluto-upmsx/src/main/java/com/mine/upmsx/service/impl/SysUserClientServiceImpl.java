package com.mine.upmsx.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mine.common.security.util.SecurityUtils;
import com.mine.upmsx.entity.SysUserClient;
import com.mine.upmsx.mapper.SysUserClientMapper;
import com.mine.upmsx.service.ISysUserClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 【客户端用户信息表】实现层
 *
 * @author jax-li
 * @date 2020-11-15
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysUserClientServiceImpl extends ServiceImpl<SysUserClientMapper, SysUserClient> implements ISysUserClientService {

    @Override
    public void add(Long userId) {
        SysUserClient entity = new SysUserClient();
        entity.setUserId(userId);
        entity.setClientId(SecurityUtils.getClientId());
        baseMapper.insert(entity);
    }
}
