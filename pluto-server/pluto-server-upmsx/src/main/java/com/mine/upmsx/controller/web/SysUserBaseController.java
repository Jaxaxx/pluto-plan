package com.mine.upmsx.controller.web;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.mine.common.core.result.Result;
import com.mine.common.log.annotation.SysLog;
import com.mine.upmsx.dto.SysUserBaseDTO;
import com.mine.upmsx.service.ISysUserBaseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * @description: 【登录信息表】控制层
 * @author: Generated by Jax-li
 * @create: 2020-08-12
 * @version: v1.0
 */
@Slf4j
@Api(tags = {"【登录信息表】模块API"})
@RestController
@RequestMapping("/sysUserBase")
@RequiredArgsConstructor
public class SysUserBaseController {

    private final ISysUserBaseService iSysUserBaseService;

    @SentinelResource("resource")
    @ApiOperation(value = "登录信息表列表查询", notes = "登录信息表API")
    @GetMapping("/list")
    public Result getList(SysUserBaseDTO dto) {
        return Result.ok(iSysUserBaseService.list(dto));
    }

    @ApiOperation(value = "登录信息表详细查询", notes = "登录信息表API")
    @GetMapping("/{id}")
    public Result detail(@PathVariable("id") Long id) {
        return Result.ok(iSysUserBaseService.detail(id));
    }

    @SysLog("新增用户")
    @ApiOperation(value = "登录信息表添加", notes = "登录信息表API")
    @PostMapping
    public Result insert(@RequestBody SysUserBaseDTO dto) {
        iSysUserBaseService.insert(dto);
        return Result.ok();
    }

    @ApiOperation(value = "登录信息表更新", notes = "登录信息表API")
    @PutMapping
    public Result update(@RequestBody SysUserBaseDTO dto) {
        iSysUserBaseService.update(dto);
        return Result.ok();
    }

    @ApiOperation(value = "登录信息表删除", notes = "登录信息表API")
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("id") Long id) {
        iSysUserBaseService.delete(id);
        return Result.ok();
    }



}

