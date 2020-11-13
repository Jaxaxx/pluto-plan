package com.mine.upmsx.controller.feign;


import cn.hutool.core.bean.BeanUtil;
import com.mine.common.core.result.Result;
import com.mine.common.feign.entity.upmsx.SysLog;
import com.mine.upmsx.dto.SysLogDTO;
import com.mine.upmsx.service.ISysLogService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jax-li
 */
@RestController
@RequestMapping("/feign/sysLog")
@RequiredArgsConstructor
public class FeignSysLogController {

    private final ISysLogService iSysLogService;

    @ApiOperation(value = "日志表添加")
    @PostMapping
    public Result insert(@RequestBody SysLogDTO dto) {
        SysLog sysLog = BeanUtil.copyProperties(dto, SysLog.class);
        iSysLogService.save(sysLog);
        return Result.ok();
    }

}
