package com.mine.oauth.service;

import com.mine.common.core.constant.SecurityConstants;
import com.mine.common.core.util.WebUtils;
import com.mine.common.feign.api.upmsx.RemoteSysUserBaseService;
import com.mine.common.feign.entity.SysUserBaseVO;
import com.mine.common.feign.entity.upmsx.SysUserBase;
import com.mine.common.security.config.MySecurityMessageSource;
import com.mine.common.security.constant.GrantTypeConstant;
import com.mine.common.security.model.MyUser;
import com.mine.common.security.util.SecurityUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author jax-li
 */
@Service(value = SecurityConstants.USER_DETAIL_SERVICE_NAME)
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    protected MessageSourceAccessor messages = MySecurityMessageSource.getAccessor();
    private final RemoteSysUserBaseService remoteSysUserBaseService;

    private UserDetails getLoginInfo(@NonNull String grantType, String username) {
        // 用户信息校验之前，SecurityContextHolder.getContext().getAuthentication() 存放的是clientid
        final String clientId = SecurityUtils.getUsername();
        SysUserBase user = remoteSysUserBaseService.getUserByUserName(clientId, username);
        // check userinfo
        check(user);
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

        final Set<String> role = new HashSet<>();
        role.add("ROLE_DEMO_ALL");
        role.add("ROLE_DEMO_READ");
        role.add("ROLE_DEMO_WRITE");

        final Set<String> permissions = new HashSet<>();
        permissions.add("PERM_DEMO_ALL");
        permissions.add("PERM_DEMO_READ");

        Set<String> authSet = new HashSet<>();
        authSet.addAll(role);
        authSet.addAll(permissions);

        Collection<? extends GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(authSet.toArray(new String[0]));
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

    @Override
    public UserDetails loadUserByUsername(String username) {
        String grantType = WebUtils.getRequest().getParameter("grant_type");
        return getLoginInfo(grantType, username);
    }

}