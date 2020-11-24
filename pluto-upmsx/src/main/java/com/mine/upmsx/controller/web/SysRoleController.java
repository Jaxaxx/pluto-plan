package com.mine.upmsx.controller.web;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mine.common.core.result.Result;

import com.mine.upmsx.dto.SysRoleDTO;
import com.mine.upmsx.entity.SysRole;
import com.mine.upmsx.service.ISysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;



/**
 * 【角色表】控制层
 *
 * @author jax-li
 * @date 2020-11-18
 */
@Slf4j
@Api(tags = {"【角色表】模块API"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/sysRole")
public class SysRoleController {

    private final ISysRoleService  sysRoleService;

    @ApiOperation(value = "角色表列表查询")
    @GetMapping
    public Result<?> list(SysRoleDTO dto){
        return Result.ok(sysRoleService.list());
    }

    @ApiOperation(value = "角色表详细查询")
    @GetMapping("/{id}")
    public Result<?> detail(@PathVariable("id") Long id ){
        return Result.ok(sysRoleService.getById(id));
    }

    @ApiOperation(value = "角色表添加")
    @PostMapping
    public Result<?> insert(@RequestBody SysRoleDTO dto){
        SysRole entity = BeanUtil.copyProperties(dto, SysRole.class);
        sysRoleService.save(entity);
        return Result.ok();
    }

    @ApiOperation(value = "角色表更新")
    @PutMapping
    public Result<?> update(@RequestBody SysRoleDTO dto){
        SysRole entity = BeanUtil.copyProperties(dto, SysRole.class);
        sysRoleService.updateById(entity);
        return Result.ok();
    }

    @ApiOperation(value = "角色表删除")
    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable("id") Long id){
        sysRoleService.removeById(id);
        return Result.ok();
    }
}

