package com.mine.upmsx.controller.web;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mine.common.core.result.Result;

import com.mine.upmsx.dto.SysUserInfoDTO;
import com.mine.upmsx.entity.SysUserInfo;
import com.mine.upmsx.service.ISysUserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;



/**
 * 【用户信息表】控制层
 *
 * @author jax-li
 * @date 2020-11-17
 */
@Slf4j
@Api(tags = {"【用户信息表】模块API"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/sysUserInfo")
public class SysUserInfoController {

    private final ISysUserInfoService  sysUserInfoService;

    @ApiOperation(value = "用户信息表列表查询")
    @GetMapping
    public Result<?> list(SysUserInfoDTO dto){
        return Result.ok(sysUserInfoService.list());
    }

    @PreAuthorize("hasAnyAuthority('ROLE_READ') OR hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "用户信息表详细查询")
    @GetMapping("/{id}")
    public Result<?> detail(@PathVariable("id") Long id ){
        return Result.ok(sysUserInfoService.getById(id));
    }

    @ApiOperation(value = "用户信息表添加")
    @PostMapping
    public Result<?> insert(@RequestBody SysUserInfoDTO dto){
        SysUserInfo entity = BeanUtil.copyProperties(dto, SysUserInfo.class);
        sysUserInfoService.save(entity);
        return Result.ok();
    }

    @ApiOperation(value = "用户信息表更新")
    @PutMapping
    public Result<?> update(@RequestBody SysUserInfoDTO dto){
        SysUserInfo entity = BeanUtil.copyProperties(dto, SysUserInfo.class);
        sysUserInfoService.updateById(entity);
        return Result.ok();
    }

    @ApiOperation(value = "用户信息表删除")
    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable("id") Long id){
        sysUserInfoService.removeById(id);
        return Result.ok();
    }
}

