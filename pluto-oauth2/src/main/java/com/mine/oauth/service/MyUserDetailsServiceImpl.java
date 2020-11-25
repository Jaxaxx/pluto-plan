package com.mine.oauth.service;

import com.mine.common.core.constant.SecurityConstants;
import com.mine.common.core.util.WebUtils;
import com.mine.common.feign.api.upmsx.RemoteSysUserBaseService;
import com.mine.common.feign.entity.upmsx.SysUserBase;
import com.mine.common.security.config.MySecurityMessageSource;
import com.mine.common.security.constant.GrantTypeConstant;
import com.mine.common.security.model.MyUser;
import com.mine.common.security.util.SecurityUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * @author jax-li
 */
@Service(value = SecurityConstants.USER_DETAIL_SERVICE_NAME)
@RequiredArgsConstructor
public class MyUserDetailsServiceImpl implements UserDetailsService {

    private static final String defaultRolePrefix = "ROLE_";
    protected MessageSourceAccessor messages = MySecurityMessageSource.getAccessor();

    private final RemoteSysUserBaseService remoteSysUserBaseService;

    @Override
    public UserDetails loadUserByUsername(String username) {
        return getLoginInfo(username);
    }

    private UserDetails getLoginInfo(@NonNull String username) {
        // 用户信息校验之前，SecurityContextHolder.getContext().getAuthentication() 存放的是clientId
        final String clientId = SecurityUtils.getUsername();
        SysUserBase user = remoteSysUserBaseService.getUserByUserName(clientId, username);
        // check userinfo
        check(user);
        final String grantType = WebUtils.getRequest().getParameter("grant_type");
        // 密码使用bcrypt对比
        if (grantType.equals(GrantTypeConstant.PASSWORD)) {
            user.setPassword(SecurityConstants.BCRYPT + (user.getPassword()));
        }
        // 验证码_短信_一键登录...使用noop对比
        if (grantType.equals(GrantTypeConstant.SMS)) {
            user.setPassword(SecurityConstants.NOOP + user.getPassword());
        }
        return conversion(user);
    }

    private void check(SysUserBase vo) {

        if (Objects.isNull(vo) || Objects.isNull(vo.getId())) {
            throw new UsernameNotFoundException(messages.getMessage(
                    "AbstractUserDetailsAuthenticationProvider.userNameNotFound",
                    "User does not exist"));
        }
        // TODO 其他校验逻辑
    }

    private UserDetails conversion(SysUserBase user) {
        Set<String> roleSet = user.getRoles();
        String[] roles = roleSet.stream().map(role -> {
            if (!StringUtils.startsWithIgnoreCase(role, defaultRolePrefix)) {
                role = defaultRolePrefix + role;
            }
            return role;
        }).toArray(String[]::new);

        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(roles);
        return new MyUser(user.getId(),
                user.getMobile(),
                user.getUserName(),
                user.getPassword(),
                user.getUserName(),
                user.getIsEnabled(),
                true,
                true,
                !user.getIsLocked(),
                authorities);
    }

}