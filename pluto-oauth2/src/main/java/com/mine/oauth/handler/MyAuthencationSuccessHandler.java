package com.mine.oauth.handler;

import com.mine.common.security.handler.AbstractAuthenticationSuccessEventHandler;
import com.mine.common.security.model.MyUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Component;

/**
 * @Description
 * @Author
 * @Date
 */
@Slf4j
@Component
public class MyAuthencationSuccessHandler extends AbstractAuthenticationSuccessEventHandler {

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