package com.mine.app.controller.web;

import com.mine.common.core.util.R;
import com.mine.common.feign.api.upmsx.RemoteSysUserBaseService;
import com.mine.common.feign.entity.SysUserBaseVO;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jax-li
 */
@Api(tags = "用户模块API")
@RestController
@AllArgsConstructor
public class UserController {

    private final RemoteSysUserBaseService remoteSysUserBaseService;

    @GetMapping(value = "get")
    public Object get() {

        SysUserBaseVO vo = remoteSysUserBaseService.getUserByUserName("myuser");
        R<String> test = remoteSysUserBaseService.test();

        return test;
    }

}