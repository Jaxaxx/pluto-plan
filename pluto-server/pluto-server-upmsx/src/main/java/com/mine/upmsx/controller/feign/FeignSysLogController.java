package com.mine.upmsx.controller.feign;


import com.mine.common.core.util.R;
import com.mine.upmsx.dto.SysLogDTO;
import com.mine.upmsx.service.ISysLogService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/feign/sysLog")
@RequiredArgsConstructor
public class FeignSysLogController {

    private final ISysLogService iSysLogService;

    @ApiOperation(value = "日志表添加")
    @PostMapping
    public R insert(@RequestBody SysLogDTO dto) {
        iSysLogService.insert(dto);
        return R.ok();
    }

}
