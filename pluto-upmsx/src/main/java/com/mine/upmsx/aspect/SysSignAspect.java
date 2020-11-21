package com.mine.upmsx.aspect;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.mine.common.core.constant.SecurityConstants;
import com.mine.common.core.result.Result;
import com.mine.common.core.util.WebUtils;
import com.mine.common.feign.api.auth.RemoteAuthService;
import com.mine.common.feign.entity.upmsx.SysUserBase;
import com.mine.common.security.config.MySecurityMessageSource;
import com.mine.common.security.filter.BodyReaderHttpServletRequestWrapper;
import com.mine.common.security.util.SecurityUtils;
import com.mine.upmsx.annotation.SysSign;
import com.mine.upmsx.entity.SysAuthClient;
import com.mine.upmsx.service.ISysAuthClientService;
import com.mine.upmsx.service.ISysUserBaseService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.security.web.authentication.www.BasicAuthenticationConverter.AUTHENTICATION_SCHEME_BASIC;

/**
 * 自定义注册basic认证
 *
 * @author LiMing
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class SysSignAspect {

    private final ISysAuthClientService sysAuthClientService;
    private final PasswordEncoder passwordEncoder;
    private final RemoteAuthService remoteAuthService;
    private final ClientDetailsService clientDetailsService;
    private final ISysUserBaseService sysUserBaseService;
    protected MessageSourceAccessor messages = MySecurityMessageSource.getAccessor();

    @SneakyThrows
    @Around("@annotation(sysSign)")
    @SuppressWarnings("unchecked")
    public Object signAround(ProceedingJoinPoint pjp, SysSign sysSign) {
        HttpServletRequest request = WebUtils.getRequest();
        String bodyString = new BodyReaderHttpServletRequestWrapper(request).getBodyString(request);
        Map<String, String> requestParamMap = JSONUtil.toBean(bodyString, Map.class);
        // basic token
        doBefore(request);
        // check username
        checkUserName(requestParamMap);

        Object result = pjp.proceed();
        try {
            return doAfter(request, requestParamMap);
        } catch (Exception e) {
            log.error("注册自动获取token出错");
            e.printStackTrace();
        }
        return result;
    }

    private void checkUserName(Map<String, String> requestParamMap) {

        final String userName = requestParamMap.get("userName");

        if (StringUtils.isEmpty(userName)) {
            String usernameIsEmpty = messages.getMessage(
                    "SignUserAspect.emptyUsername",
                    "Username is empty"
            );
            throw new BadCredentialsException(usernameIsEmpty);
        }

        int count = sysUserBaseService.count(Wrappers.<SysUserBase>lambdaQuery()
                .eq(SysUserBase::getUserName, userName)
        );
        if (count > 0) {
            String usernameAlreadyExists = messages.getMessage(
                    "SignUserAspect.UsernameAlreadyExists",
                    new Object[]{userName},
                    "username already exists");
            throw new BadCredentialsException(usernameAlreadyExists);
        }
    }

    public void doBefore(HttpServletRequest request) {
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

    @SneakyThrows
    private Object doAfter(HttpServletRequest request, Map<String, String> requestParamMap) {

        final String userName = requestParamMap.get("userName");
        final String password = requestParamMap.get("password");
        final String Authorization = request.getHeader(AUTHORIZATION);
        Map<String, String> parameters = new HashMap<>();
        parameters.put("grant_type", "password");
        parameters.put("username", userName);
        parameters.put("password", password);

        User user = new User(userName, password, SecurityUtils.getAuthentication().getAuthorities());
        Principal principal = new UsernamePasswordAuthenticationToken(user, password);

        ResponseEntity<OAuth2AccessToken> responseEntity = remoteAuthService.token(principal, parameters, Authorization);

        String clientId = SecurityUtils.getClientId();
        ClientDetails authenticatedClient = clientDetailsService.loadClientByClientId(clientId);
        if (authenticatedClient.getScope().contains("swagger")) {
            return Result.ok(responseEntity.getBody());
        }

        return Result.ok(Objects.requireNonNull(responseEntity.getBody()).getAdditionalInformation().get("data"));
    }

}
