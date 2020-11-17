package com.mine.common.security.util;


import com.mine.common.security.model.MyUser;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

/**
 * @author Jax-li
 */
@Slf4j
@UtilityClass
public class SecurityUtils {

    /**
     * 获取Authentication
     *
     * @return
     */
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * userObj
     * 获取当前用名
     *
     * @return String
     */
    public String getUsername() {
        Object principal = getAuthentication().getPrincipal();
        if (principal instanceof String) {
            return principal.toString();
        } else if (principal instanceof MyUser) {
            return ((MyUser) principal).getUsername();
        } else if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        }
        return null;
    }

    /**
     * 获取客户端clientId
     *
     * @return
     */
    public String getClientId() {
        Authentication authentication = getAuthentication();
        if (authentication instanceof OAuth2Authentication) {
            OAuth2Authentication auth2Authentication = (OAuth2Authentication) authentication;
            return auth2Authentication.getOAuth2Request().getClientId();
        }
        return authentication.getName();
    }

    /**
     * 获取用户
     *
     * @return MyUser
     * <p>
     * 获取当前用户的全部信息 EnableWxesResourceServer true
     * 获取当前用户的用户名 EnableWxesResourceServer false
     */
    public MyUser getUser() {
        Authentication authentication = getAuthentication();
        if (authentication == null) {
            return null;
        }
        return getUser(authentication);
    }

    private MyUser getUser(Authentication authentication) {
        Object principal = authentication.getPrincipal();
        if (principal instanceof MyUser) {
            MyUser user = (MyUser) principal;
            String token = ((OAuth2AuthenticationDetails) (authentication).getDetails()).getTokenValue();
            user.setToken(token);
            return user;
        }
        return null;
    }

    /**
     * 获取当前登录人的token
     *
     * @return
     */
    public String getToken() {
        Authentication authentication = getAuthentication();
        if (authentication == null) {
            return null;
        }
        return getToken(authentication);
    }

    private String getToken(Authentication authentication) {
        try {
            String token = ((OAuth2AuthenticationDetails) (authentication).getDetails()).getTokenValue();
            return token;
        } catch (Exception e) {
            log.error("请求未携带token");
            return null;
        }
    }

}