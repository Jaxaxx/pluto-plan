package com.mine.upmsx.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mine.upmsx.entity.SysUserInfo;
import com.mine.upmsx.vo.SysUserInfoVO;

/**
 * 【用户信息表】代理层
 *
 * @author jax-li
 * @date 2020-11-17
 */
public interface ISysUserInfoService extends IService<SysUserInfo> {

    void add(Long userId);

    SysUserInfoVO getByUserId(Long userId);
}
