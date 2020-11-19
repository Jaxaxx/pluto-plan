package com.mine.upmsx.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mine.upmsx.entity.SysUserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

/**
 * 【用户权限关联表】映射器
 *
 * @author jax-li
 * @date 2020-11-18
 */
@Mapper
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {

    Set<String> getUserRoles(@Param("userId") Long userId);
}
