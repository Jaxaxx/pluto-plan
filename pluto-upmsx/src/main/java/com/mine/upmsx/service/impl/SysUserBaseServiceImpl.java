package com.mine.upmsx.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mine.common.core.util.MapperUtils;
import com.mine.common.feign.entity.SysUserBaseVO;
import com.mine.common.security.util.PasswordEncoderUtil;
import com.mine.upmsx.dto.SysUserBaseDTO;
import com.mine.upmsx.entity.SysUserBase;
import com.mine.upmsx.mapper.SysUserBaseMapper;
import com.mine.upmsx.service.ISysUserBaseService;
import com.mine.upmsx.service.ISysUserClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @description: 【登录信息表】实现层
 * @author: Generated by Jax-li
 * @create: 2020-08-12
 * @version: v1.0
 */
@Service
@RequiredArgsConstructor
public class SysUserBaseServiceImpl extends ServiceImpl<SysUserBaseMapper, SysUserBase> implements ISysUserBaseService {

    private final SysUserBaseMapper sysUserBaseMapper;
    private final ISysUserClientService sysUserClientService;

    @Override
    public List<SysUserBaseVO> list(SysUserBaseDTO dto) {
        List<SysUserBase> sysUserBases = sysUserBaseMapper.selectList(new QueryWrapper<>());
        List<SysUserBaseVO> sysUserBaseVOS = MapperUtils.INSTANCE.mapAsList(SysUserBaseVO.class, sysUserBases);
        return sysUserBaseVOS;
    }

    @Override
    public void update(SysUserBaseDTO dto) {
        SysUserBase sysUserBase = baseMapper.selectById(dto.getId());
        BeanUtil.copyProperties(dto, sysUserBase);
        baseMapper.updateById(sysUserBase);
    }

    @Override
    public void delete(Long id) {
        baseMapper.deleteById(id);
    }

    @Override
    public SysUserBaseVO getLoginInfo(String clientId, String userName) {
        return baseMapper.getLoginInfo(clientId, userName);
    }

    @Override
    public void updateLastLoginTime(Long userId, LocalDateTime now) {
        baseMapper.updateLastLoginTime(userId, now);
    }

    public Long insert(SysUserBaseDTO dto) {
        String password = dto.getPassword();
        String encode = PasswordEncoderUtil.encode(password);
        dto.setPassword(encode);
        SysUserBase sysUserBase = BeanUtil.copyProperties(dto, SysUserBase.class);
        baseMapper.insert(sysUserBase);
        return sysUserBase.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void sign(SysUserBaseDTO dto) {
        // 主表user_base保存信息
        final Long userId = this.insert(dto);
        // user_client表
        sysUserClientService.add(userId);
        // user_info
    }
}
