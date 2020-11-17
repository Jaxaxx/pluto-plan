package com.mine.upmsx.aspect;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.mine.common.core.constant.SecurityConstants;
import com.mine.common.core.util.WebUtils;
import com.mine.upmsx.annotation.SysSign;
import com.mine.upmsx.entity.SysAuthClient;
import com.mine.upmsx.service.ISysAuthClientService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.security.web.authentication.www.BasicAuthenticationConverter.AUTHENTICATION_SCHEME_BASIC;

/**
 * 自定义注册basic认证
 */
@Aspect
@Component
@RequiredArgsConstructor
public class UserSignAspect {

    private final ISysAuthClientService sysAuthClientService;
    private final PasswordEncoder passwordEncoder;

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
        } catch (IllegalArgumentException e) {
            throw new BadCredentialsException("Failed to decode basic authentication token");
        }

        String token = new String(decoded, StandardCharsets.UTF_8);

        int delim = token.indexOf(":");

        if (delim == -1) {
            throw new BadCredentialsException("Invalid basic authentication token");
        }
        final String clientId = token.substring(0, delim);
        final String clientSecret = token.substring(delim + 1);

        SysAuthClient authClient =
                sysAuthClientService.getOne(Wrappers.lambdaQuery(SysAuthClient.class)
                        .eq(SysAuthClient::getClientId, clientId)
                );

        if (authClient == null) {
            throw new BadCredentialsException("No client with requested id: " + clientId);
        }

        if (!passwordEncoder.matches(
                clientSecret, SecurityConstants.NOOP + authClient.getClientSecret())) {
            throw new BadCredentialsException("Bad credentials");
        }

        Authentication authentication = new PreAuthenticatedAuthenticationToken(clientId, clientSecret);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

}
