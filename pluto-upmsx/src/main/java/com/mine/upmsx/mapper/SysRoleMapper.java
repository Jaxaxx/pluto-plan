package com.mine.upmsx.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mine.upmsx.entity.SysRole;
import com.mine.upmsx.vo.SysRoleVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 【角色表】映射器
 *
 * @author jax-li
 * @date 2020-11-18
 */
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {

    List<SysRoleVO> selectRoleByUserId(@Param("userId") Long userId);
}
