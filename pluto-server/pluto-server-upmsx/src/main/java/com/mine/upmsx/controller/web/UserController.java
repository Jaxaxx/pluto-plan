package com.mine.upmsx.controller.web;

import com.mine.common.security.model.MyUser;
import com.mine.common.security.util.SecurityUtils;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Api
@RestController
public class UserController {

    @GetMapping(value = "get")
    public Object get() {

        MyUser user = SecurityUtils.getUser();

        String clientId = SecurityUtils.getClientId();

        String token = SecurityUtils.getToken();

        String username = SecurityUtils.getUsername();

        return user;
    }

    @PostMapping("test2")
    public Object hello2() {
        int i = 1 / 0;
        return "success2";
    }

    @GetMapping("test3")
    public Object hello3() {
        return null;
    }

    @GetMapping(value = "test4")
    public void hello6() {
        System.out.println(System.currentTimeMillis() + "这是一个测试");
    }

    @GetMapping(value = "test5")
    public Object hello5(@RequestParam(value = "name") String name) {
        return "hello " + name;
    }
}