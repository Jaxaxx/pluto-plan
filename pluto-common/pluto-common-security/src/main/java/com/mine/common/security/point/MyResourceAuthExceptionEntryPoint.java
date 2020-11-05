package com.mine.common.security.point;

import com.alibaba.nacos.common.http.param.MediaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mine.common.core.constant.MyAuthResponseEnum;
import com.mine.common.core.util.R;
import lombok.SneakyThrows;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * token 资源服务token验证不通过异常端点
 *
 * @author LiMing
 */
public class MyResourceAuthExceptionEntryPoint implements AuthenticationEntryPoint {

    @Override
    @SneakyThrows
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authenticationException) {
        response.setContentType(MediaType.APPLICATION_JSON);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        R<Object> ret = R.failed(MyAuthResponseEnum.UNAUTHORIZED);
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(response.getOutputStream(), ret);
        } catch (Exception e) {
            throw new ServletException();
        }
    }
}
