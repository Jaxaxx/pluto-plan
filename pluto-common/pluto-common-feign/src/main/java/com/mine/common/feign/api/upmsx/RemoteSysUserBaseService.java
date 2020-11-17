package com.mine.common.feign.api.upmsx;

import com.mine.common.core.constant.ServiceNameConstants;
import com.mine.common.core.result.Result;
import com.mine.common.feign.entity.upmsx.SysUserBase;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

/**
 * @author jax-li
 */
@FeignClient(value = ServiceNameConstants.UPMS,path = "/feign/user/base")
public interface RemoteSysUserBaseService {

    /**
     * 通过用户名查询用户基本信息
     *
     * @param clientId 客户端id
     * @param userName 用户名
     * @return SysUserBaseVO系统用户基础VO
     */
    @GetMapping("/{clientId}/{userName}")
    SysUserBase getUserByUserName(
            @PathVariable("clientId") String clientId,
            @PathVariable("userName") String userName);


    @GetMapping("/test")
    Result<String> test();

    @PatchMapping
    void updateLastLoginTime(@RequestParam("userId") Long userId,
                             @RequestParam("lastLoginTime")LocalDateTime lastLoginTime);
}
