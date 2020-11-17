package com.mine.upmsx.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mine.upmsx.entity.SysUserInfo;
import com.mine.upmsx.mapper.SysUserInfoMapper;
import com.mine.upmsx.service.ISysUserInfoService;
import com.mine.upmsx.vo.SysUserInfoVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 【用户信息表】实现层
 *
 * @author jax-li
 * @date 2020-11-17
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysUserInfoServiceImpl extends ServiceImpl<SysUserInfoMapper, SysUserInfo> implements ISysUserInfoService {

    @Override
    public void add(Long userId) {
        SysUserInfo entity = new SysUserInfo();
        entity.setUserId(userId);
        // ...
        baseMapper.insert(entity);
    }

    @Override
    public SysUserInfoVO getByUserId(Long userId) {
        SysUserInfo info = baseMapper.selectOne(Wrappers.lambdaQuery(SysUserInfo.class).eq(SysUserInfo::getUserId, userId));
        return BeanUtil.toBean(info, SysUserInfoVO.class);
    }
}
