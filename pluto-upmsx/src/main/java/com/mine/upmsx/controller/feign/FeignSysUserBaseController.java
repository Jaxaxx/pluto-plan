package com.mine.upmsx.controller.feign;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mine.common.core.result.Result;
import com.mine.common.core.util.WebUtils;
import com.mine.common.feign.entity.SysUserBaseVO;
import com.mine.common.security.model.MyUser;
import com.mine.common.security.util.SecurityUtils;
import com.mine.upmsx.entity.SysUserBase;
import com.mine.upmsx.service.ISysUserBaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * ß
 *
 * @author jax-li
 */
@RestController
@RequestMapping("/feign")
@RequiredArgsConstructor
public class FeignSysUserBaseController {

    private final ISysUserBaseService sysUserBaseService;

    @GetMapping("/sysUserBase/{clientId}/{userName}")
    public SysUserBaseVO getUserByUserName(
            @PathVariable("clientId") String clientId,
            @PathVariable("userName") String userName) {
        return sysUserBaseService.getLoginInfo(clientId,userName);
    }

    @GetMapping("/test")
    public Result<?> test() {

        MyUser user = SecurityUtils.getUser();
        String str = JSONUtil.toJsonStr(JSONUtil.parse(user));

        return Result.ok(str);
    }

}
