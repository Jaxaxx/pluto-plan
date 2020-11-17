package com.mine.upmsx.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.mine.common.feign.entity.upmsx.SysUserBase;
import com.mine.upmsx.dto.SysUserBaseDTO;
import com.mine.upmsx.vo.UserVO;

import java.time.LocalDateTime;

/**
 * @description: 【登录信息表】代理层
 * @author: Generated by Jax-li
 * @create: 2020-08-12
 * @version: v1.0
 */
public interface ISysUserBaseService extends IService<SysUserBase> {

    SysUserBase getLoginInfo(String clientId, String userName);

    void updateLastLoginTime(Long userId, LocalDateTime now);

    /**
     * 用户注册
     * @param dto   用户基本信息dto
     */
    void sign(SysUserBaseDTO dto);

    UserVO info();

}
