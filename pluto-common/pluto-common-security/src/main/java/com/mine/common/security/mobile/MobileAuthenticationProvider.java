package com.mine.common.security.mobile;

import com.mine.common.core.constant.SecurityConstants;
import com.mine.common.security.service.MyUserDetailsService;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @Description 手机登录校验逻辑
 * 验证码登录、社交登录
 * @Author
 * @Date
 */
@Slf4j
public class MobileAuthenticationProvider implements AuthenticationProvider {

    private MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

    @Setter
    @Getter
    private MyUserDetailsService myUserDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        MobileAuthenticationToken mobileAuthenticationToken = (MobileAuthenticationToken) authentication;

        String principal = mobileAuthenticationToken.getPrincipal().toString();

        String uri = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI();

        UserDetails userDetails = null;

        if (SecurityConstants.SMS_APP_TOKEN_URL.equalsIgnoreCase(uri)) {
            userDetails = myUserDetailsService.loadAppUserByPhone(principal);
        } else {
            userDetails = myUserDetailsService.loadUserBySocial(principal);
        }

        if (userDetails == null) {
            log.debug("Authentication failed: no credentials provided");

            throw new BadCredentialsException(messages.getMessage(
                    "AbstractUserDetailsAuthenticationProvider.noopBindAccount",
                    "Noop Bind Account"));
        }

        MobileAuthenticationToken authenticationToken = new MobileAuthenticationToken(userDetails, authentication.getAuthorities());
        authenticationToken.setDetails(mobileAuthenticationToken.getDetails());

        return authenticationToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return MobileAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
