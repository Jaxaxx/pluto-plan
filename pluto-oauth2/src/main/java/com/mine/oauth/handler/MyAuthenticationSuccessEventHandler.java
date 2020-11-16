package com.mine.oauth.handler;

import com.mine.common.core.constant.RedisPrefixConstants;
import com.mine.common.security.handler.AbstractAuthenticationSuccessEventHandler;
import com.mine.common.security.model.MyUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

/**
 * @author jax-li
 */
@Slf4j
@Component
@RequiredArgsConstructor
@SuppressWarnings({"rawtypes","unchecked"})
public class MyAuthenticationSuccessEventHandler extends AbstractAuthenticationSuccessEventHandler {

    private final RedisTemplate redisTemplate;

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
        redisTemplate.delete(RedisPrefixConstants.VERIFY_CODE + (user.getPhone()));
    }
}