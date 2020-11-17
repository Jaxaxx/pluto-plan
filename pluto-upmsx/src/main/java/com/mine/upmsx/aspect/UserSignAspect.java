package com.mine.upmsx.aspect;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import com.mine.common.core.util.WebUtils;
import com.mine.common.security.util.SecurityUtils;
import com.mine.upmsx.annotation.SysSign;
import lombok.SneakyThrows;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.security.web.authentication.www.BasicAuthenticationConverter.AUTHENTICATION_SCHEME_BASIC;

@Aspect
@Component
public class UserSignAspect {

    private static final String BASIC_PREFIX = "basic";

    @Before("@annotation(sysSign)")
    public void before(SysSign sysSign) {
        System.out.println("前置增强");

        HttpServletRequest request = WebUtils.getRequest();
        String header = request.getHeader(AUTHORIZATION);

        if (header == null) {
            throw new BadCredentialsException("Empty basic authentication token");
        }

        header = header.trim();
        if (!StringUtils.startsWithIgnoreCase(header, AUTHENTICATION_SCHEME_BASIC)) {
            throw new BadCredentialsException("Empty basic authentication token");
        }

        if (header.equalsIgnoreCase(AUTHENTICATION_SCHEME_BASIC)) {
            throw new BadCredentialsException("Empty basic authentication token");
        }

        byte[] base64Token = header.substring(6).getBytes(StandardCharsets.UTF_8);
        byte[] decoded;
        try {
            decoded = java.util.Base64.getDecoder().decode(base64Token);
        }
        catch (IllegalArgumentException e) {
            throw new BadCredentialsException("Failed to decode basic authentication token");
        }

        String token = new String(decoded, StandardCharsets.UTF_8);

        int delim = token.indexOf(":");

        if (delim == -1) {
            throw new BadCredentialsException("Invalid basic authentication token");
        }
        final String clientId = token.substring(0, delim);
        final String clientSecret = token.substring(delim + 1);
        // TODO 查询数据库clientId和clientSecret是否有效
        Authentication authentication = new PreAuthenticatedAuthenticationToken(clientId, clientSecret);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

}
