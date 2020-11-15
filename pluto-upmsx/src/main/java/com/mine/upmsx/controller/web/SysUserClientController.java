package com.mine.upmsx.controller.web;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mine.common.core.result.Result;
import com.mine.upmsx.dto.SysUserClientDTO;
import com.mine.upmsx.entity.SysUserClient;
import com.mine.upmsx.service.ISysUserClientService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;



/**
 * 【客户端用户信息表】控制层
 *
 * @author jax-li
 * @date 2020-11-15
 */
@Slf4j
@Api(tags = {"【客户端用户信息表】模块API"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/sysUserClient")
public class SysUserClientController {

    private final ISysUserClientService  sysUserClientService;

    @ApiOperation(value = "客户端用户信息表列表查询")
    @GetMapping
    public Result<?> list(){
        return Result.ok(sysUserClientService.list());
    }

    @ApiOperation(value = "客户端用户信息表详细查询")
    @GetMapping("/{id}")
    public Result<?> detail(@PathVariable("id") Long id ){
        return Result.ok(sysUserClientService.getById(id));
    }

    @ApiOperation(value = "客户端用户信息表添加")
    @PostMapping
    public Result<?> insert(@RequestBody SysUserClientDTO dto){
        SysUserClient entity = BeanUtil.copyProperties(dto, SysUserClient.class);
        sysUserClientService.save(entity);
        return Result.ok();
    }

    @ApiOperation(value = "客户端用户信息表更新")
    @PutMapping
    public Result<?> update(@RequestBody SysUserClientDTO dto){
        SysUserClient entity = BeanUtil.copyProperties(dto, SysUserClient.class);
        sysUserClientService.updateById(entity);
        return Result.ok();
    }

    @ApiOperation(value = "客户端用户信息表删除")
    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable("id") Long id){
        sysUserClientService.removeById(id);
        return Result.ok();
    }
}

