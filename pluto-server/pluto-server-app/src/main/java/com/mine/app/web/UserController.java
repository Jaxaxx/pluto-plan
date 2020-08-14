package com.mine.app.web;

import com.mine.common.core.util.R;
import com.mine.common.feign.api.upmsx.RemoteSysUserBaseService;
import com.mine.common.feign.entity.SysUserBaseVO;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Api
@RestController
@AllArgsConstructor
public class UserController {

    private RemoteSysUserBaseService remoteSysUserBaseService;

    @GetMapping(value = "get")
    public Object get() {

        R<SysUserBaseVO> myuser = remoteSysUserBaseService.getUserByUserName("myuser");

        R<String> test = remoteSysUserBaseService.test();


        return test.getData();
    }

}