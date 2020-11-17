package com.mine.upmsx.service;

import com.mine.upmsx.entity.SysUserClient;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 【客户端用户信息表】代理层
 *
 * @author jax-li
 * @date 2020-11-15
 */
public interface ISysUserClientService extends IService<SysUserClient> {

    void add(Long userId);
}
