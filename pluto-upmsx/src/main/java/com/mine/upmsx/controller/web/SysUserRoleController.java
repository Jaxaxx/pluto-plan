package com.mine.upmsx.controller.web;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mine.common.core.result.Result;

import com.mine.upmsx.dto.SysUserRoleDTO;
import com.mine.upmsx.entity.SysUserRole;
import com.mine.upmsx.service.ISysUserRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;



/**
 * 【用户权限关联表】控制层
 *
 * @author jax-li
 * @date 2020-11-18
 */
@Slf4j
@Api(tags = {"【用户权限关联表】模块API"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/sysUserRole")
public class SysUserRoleController {

    private final ISysUserRoleService  sysUserRoleService;

    @ApiOperation(value = "用户权限关联表列表查询")
    @GetMapping
    public Result<?> list(SysUserRoleDTO dto){
        return Result.ok(sysUserRoleService.list());
    }

    @ApiOperation(value = "用户权限关联表详细查询")
    @GetMapping("/{id}")
    public Result<?> detail(@PathVariable("id") Long id ){
        return Result.ok(sysUserRoleService.getById(id));
    }

    @ApiOperation(value = "用户权限关联表添加")
    @PostMapping
    public Result<?> insert(@RequestBody SysUserRoleDTO dto){
        SysUserRole entity = BeanUtil.copyProperties(dto, SysUserRole.class);
        sysUserRoleService.save(entity);
        return Result.ok();
    }

    @ApiOperation(value = "用户权限关联表更新")
    @PutMapping
    public Result<?> update(@RequestBody SysUserRoleDTO dto){
        SysUserRole entity = BeanUtil.copyProperties(dto, SysUserRole.class);
        sysUserRoleService.updateById(entity);
        return Result.ok();
    }

    @ApiOperation(value = "用户权限关联表删除")
    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable("id") Long id){
        sysUserRoleService.removeById(id);
        return Result.ok();
    }
}

