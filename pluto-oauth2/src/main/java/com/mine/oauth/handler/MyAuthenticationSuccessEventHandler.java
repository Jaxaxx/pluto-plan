package com.mine.oauth.handler;

import com.mine.common.security.handler.AbstractAuthenticationSuccessEventHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

/**
 * @author jax-li
 */
@Slf4j
@Component
public class MyAuthenticationSuccessEventHandler extends AbstractAuthenticationSuccessEventHandler {

    /**
     * 处理登录成功方法
     * <p>
     * 获取到登录的authentication 对象
     */
    @Override
    public void handle(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        log.info("认证成功，: {}", user.getUsername());
    }
}