package com.mine.upmsx.controller.web;

import com.mine.common.feign.api.system.RemoteTestService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Api
@RestController
@RequiredArgsConstructor
public class TestFeignController {

    private final RemoteTestService remoteTestService;

    @GetMapping("test")
    public Object test() {
        return remoteTestService.test();
    }

}
