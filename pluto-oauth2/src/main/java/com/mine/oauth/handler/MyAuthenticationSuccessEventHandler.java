package com.mine.oauth.handler;

import com.mine.common.core.constant.RedisPrefixConstants;
import com.mine.common.core.util.WebUtils;
import com.mine.common.feign.api.upmsx.RemoteSysLogService;
import com.mine.common.feign.api.upmsx.RemoteSysUserBaseService;
import com.mine.common.feign.entity.upmsx.SysLog;
import com.mine.common.log.util.SysLogUtil;
import com.mine.common.security.constant.GrantTypeConstant;
import com.mine.common.security.handler.AbstractAuthenticationSuccessEventHandler;
import com.mine.common.security.model.MyUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author jax-li
 */
@Slf4j
@Component
@RequiredArgsConstructor
@SuppressWarnings({"rawtypes", "unchecked"})
public class MyAuthenticationSuccessEventHandler extends AbstractAuthenticationSuccessEventHandler {

    private final RedisTemplate redisTemplate;
    private final RemoteSysUserBaseService remoteSysUserBaseService;
    private final RemoteSysLogService remoteSysLogService;
    private String grantType = "";

    /**
     * 处理登录成功方法
     * <p>
     * 获取到登录的authentication 对象
     */
    @Override
    public void handle(Authentication authentication) {
        MyUser user = (MyUser) authentication.getPrincipal();
        log.info("认证成功，: {}", user.getUsername());
        // sms模式delete验证码
        if (authentication.getDetails() instanceof Map) {
            Map<String, String> details = (Map) authentication.getDetails();
            this.grantType = details.get("grant_type");
            if (grantType.equals(GrantTypeConstant.SMS)) {
                redisTemplate.delete(RedisPrefixConstants.VERIFY_CODE + (user.getPhone()));
            }
        }
        // 1.登录成功修改最后登录时间
        remoteSysUserBaseService.updateLastLoginTime(user.getId(), LocalDateTime.now());
        // 2.记录登录日志
        SysLog sysLog = SysLogUtil.getSysLog(null);
        sysLog.setTitle("登录");
        sysLog.setNote(grantType);
        sysLog.setCreateUserId(user.getId());
        sysLog.setCreateUserName(user.getUsername());
        remoteSysLogService.saveLog(sysLog);
    }
}