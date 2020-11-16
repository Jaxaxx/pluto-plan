package com.mine.system.controller;

import com.mine.common.security.util.SecurityUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/feign/test")
public class TestController {

    @GetMapping()
    public Object test() {
        return SecurityUtils.getAuthentication();
    }

}
