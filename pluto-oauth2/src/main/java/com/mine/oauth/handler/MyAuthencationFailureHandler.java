package com.mine.oauth.handler;


import com.mine.common.security.handler.AbstractAuthenticationFailureEvenHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

/**
 * @Description
 * @Author
 * @Date
 */
@Slf4j
@Component
public class MyAuthencationFailureHandler extends AbstractAuthenticationFailureEvenHandler {

    @Override
    public void handle(AuthenticationException exception, Authentication authentication) {
        log.info("用户：{} 登录失败，异常：{}", authentication.getPrincipal(), exception.getLocalizedMessage());
    }
}