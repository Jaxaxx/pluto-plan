package com.mine.common.security.point;

import com.alibaba.nacos.common.http.param.MediaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mine.common.core.util.R;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * token 资源服务token验证不通过异常端点
 *
 * @author LiMing
 */
@Slf4j
public class MyResourceAuthExceptionEntryPoint implements AuthenticationEntryPoint {

    protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

    @Override
    @SneakyThrows
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authenticationException) {
        String retMsg = messages.getMessage("AbstractAccessDecisionManager.unauthorized", "Authentication failed or expired");
        log.error("资源服务鉴权失败::{}", retMsg);
        response.setContentType(MediaType.APPLICATION_JSON);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        R<Object> ret = R.failed(retMsg);
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(response.getOutputStream(), ret);
        } catch (Exception e) {
            throw new ServletException();
        }
    }
}
