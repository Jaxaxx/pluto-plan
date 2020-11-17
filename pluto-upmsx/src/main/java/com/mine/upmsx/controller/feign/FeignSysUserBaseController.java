package com.mine.upmsx.controller.feign;

import cn.hutool.json.JSONUtil;
import com.mine.common.core.result.Result;
import com.mine.common.feign.entity.SysUserBaseVO;
import com.mine.common.security.model.MyUser;
import com.mine.common.security.util.SecurityUtils;
import com.mine.upmsx.service.ISysUserBaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

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
        return sysUserBaseService.getLoginInfo(clientId, userName);
    }

    @GetMapping("/test")
    public Result<?> test() {

        MyUser user = SecurityUtils.getUser();
        String str = JSONUtil.toJsonStr(JSONUtil.parse(user));

        return Result.ok(str);
    }

    @PatchMapping("/sysUserBase")
    public void updateLastLoginTime(@RequestParam("userId") Long userId,
                                    @RequestParam("lastLoginTime") LocalDateTime lastLoginTime) {
        sysUserBaseService.updateLastLoginTime(userId, lastLoginTime);
    }

}
