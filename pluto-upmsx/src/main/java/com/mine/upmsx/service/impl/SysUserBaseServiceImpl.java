package com.mine.upmsx.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mine.common.feign.entity.SysUserBaseVO;
import com.mine.common.feign.entity.upmsx.SysUserBase;
import com.mine.common.security.util.PasswordEncoderUtil;
import com.mine.common.security.util.SecurityUtils;
import com.mine.upmsx.dto.SysUserBaseDTO;
import com.mine.upmsx.mapper.SysUserBaseMapper;
import com.mine.upmsx.service.ISysUserBaseService;
import com.mine.upmsx.service.ISysUserClientService;
import com.mine.upmsx.service.ISysUserInfoService;
import com.mine.upmsx.vo.SysUserInfoVO;
import com.mine.upmsx.vo.UserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * @description: 【登录信息表】实现层
 * @author: Generated by Jax-li
 * @create: 2020-08-12
 * @version: v1.0
 */
@Service
@RequiredArgsConstructor
public class SysUserBaseServiceImpl extends ServiceImpl<SysUserBaseMapper, SysUserBase> implements ISysUserBaseService {

    private final ISysUserClientService sysUserClientService;
    private final ISysUserInfoService sysUserInfoService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void sign(SysUserBaseDTO dto) {
        // 主表user_base保存信息
        final Long userId = this.insert(dto);
        // user_client表
        sysUserClientService.add(userId);
        // user_info
        sysUserInfoService.add(userId);
    }

    public Long insert(SysUserBaseDTO dto) {
        checkUserBase();
        dto.setPassword(PasswordEncoderUtil.encode(dto.getPassword()));
        SysUserBase sysUserBase = BeanUtil.copyProperties(dto, SysUserBase.class);
        baseMapper.insert(sysUserBase);
        return sysUserBase.getId();
    }

    private void checkUserBase() {
        // TODO ...
    }

    @Override
    public SysUserBase getLoginInfo(String clientId, String userName) {
        return baseMapper.getLoginInfo(clientId, userName);
    }

    @Override
    public void updateLastLoginTime(Long userId, LocalDateTime now) {
        baseMapper.updateLastLoginTime(userId, now);
    }

    @Override
    public UserVO info() {
        Long userId = SecurityUtils.getUserId();
        SysUserBaseVO base = BeanUtil.toBean(this.getById(userId), SysUserBaseVO.class);
        SysUserInfoVO info = sysUserInfoService.getByUserId(userId);

        return UserVO.builder()
                .base(base)
                .info(info)
                .build();
    }
}
