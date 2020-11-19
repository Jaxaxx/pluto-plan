package com.mine.upmsx.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mine.upmsx.entity.SysRole;
import com.mine.upmsx.vo.SysRoleVO;

import java.util.List;

/**
 * 【角色表】代理层
 *
 * @author jax-li
 * @date 2020-11-18
 */
public interface ISysRoleService extends IService<SysRole> {

    List<SysRoleVO>  getByUserId(Long userId);

}
