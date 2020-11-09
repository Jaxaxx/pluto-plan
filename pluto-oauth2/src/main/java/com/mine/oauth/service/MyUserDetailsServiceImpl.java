package com.mine.oauth.service;

import com.mine.common.core.constant.SecurityConstants;
import com.mine.common.feign.api.upmsx.RemoteSysUserBaseService;
import com.mine.common.feign.entity.SysUserBaseVO;
import com.mine.common.security.config.MySecurityMessageSource;
import com.mine.common.security.model.MyUser;
import com.mine.common.security.service.MyUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author jax-li
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MyUserDetailsServiceImpl implements MyUserDetailsService {

    protected MessageSourceAccessor messages = MySecurityMessageSource.getAccessor();

    private final RemoteSysUserBaseService remoteSysUserBaseService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return getUserDetail(remoteSysUserBaseService.getUserByUserName(username));
    }

    private UserDetails getUserDetail(SysUserBaseVO vo) {

        if (Objects.isNull(vo) || Objects.isNull(vo.getId())) {

            throw new AuthenticationCredentialsNotFoundException(messages.getMessage(
                    "AbstractUserDetailsAuthenticationProvider.userNameNotFound",
                    "The UserName is not Found"));
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        return new MyUser(vo.getId(),
                vo.getMobile(),
                vo.getUserName(),
                SecurityConstants.BCRYPT + vo.getPassword(),
                vo.getUserName(),
                true,
                true,
                true,
                true,
                authorities);
    }

    @Override
    public UserDetails loadAppUserByPhone(String principal) {
        return null;
    }

    @Override
    public UserDetails loadUserBySocial(String principal) {
        return null;
    }
}