package com.mine.oauth.handler;


import com.mine.common.core.constant.RedisPrefixConstants;
import com.mine.common.feign.api.upmsx.RemoteSysLogService;
import com.mine.common.feign.entity.upmsx.SysLog;
import com.mine.common.log.util.SysLogUtil;
import com.mine.common.security.constant.GrantTypeConstant;
import com.mine.common.security.handler.AbstractAuthenticationFailureEvenHandler;
import com.mine.common.security.model.MyUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author jax-li
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class MyAuthenticationFailureEvenHandler extends AbstractAuthenticationFailureEvenHandler {

    private final RemoteSysLogService remoteSysLogService;
    private String grantType = "";

    @Override
    public void handle(AuthenticationException exception, Authentication authentication) {
        log.debug("用户：{} 登录失败：{}", authentication.getPrincipal(), exception.getLocalizedMessage());
        if (authentication.getDetails() instanceof Map) {
            Map<String, String> details = (Map) authentication.getDetails();
            this.grantType = details.get("grant_type");
        }
        // 1.记录登录日志,进行锁定账户等操作
        SysLog sysLog = SysLogUtil.getSysLog(null);
        sysLog.setTitle("登录");
        sysLog.setType(9);
        sysLog.setNote(grantType);
        remoteSysLogService.saveLog(sysLog);
    }
}