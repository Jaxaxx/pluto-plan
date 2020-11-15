package com.mine.oauth.service;

import cn.hutool.core.collection.CollectionUtil;
import com.mine.common.core.constant.SecurityConstants;
import com.mine.common.feign.api.upmsx.RemoteSysLogService;
import com.mine.common.feign.api.upmsx.RemoteSysUserBaseService;
import com.mine.common.feign.entity.SysUserBaseVO;
import com.mine.common.feign.entity.upmsx.SysLog;
import com.mine.common.log.aspect.SysLogAspect;
import com.mine.common.security.config.MySecurityMessageSource;
import com.mine.common.security.model.MyUser;
import com.mine.common.security.service.MyUserDetailsService;
import com.mine.common.security.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author jax-li
 */
@Service
@RequiredArgsConstructor
public class MyUserDetailsServiceImpl implements MyUserDetailsService {

    protected MessageSourceAccessor messages = MySecurityMessageSource.getAccessor();

    private final RemoteSysUserBaseService remoteSysUserBaseService;
    private final RemoteSysLogService remoteSysLogService;

    @Override
    public UserDetails loadUserByUsername(String username) {
        // 用户信息校验之前，SecurityContextHolder.getContext().getAuthentication() 存放的是clientid
        final String clientId = SecurityUtils.getUsername();
        return getUserDetail(remoteSysUserBaseService.getUserByUserName(clientId, username));
    }

    private UserDetails getUserDetail(SysUserBaseVO vo) {
        checkUserDetail(vo);

        Set<String> authSet = new HashSet<>();
        authSet.add("ROLE_ADMIN");
        Collection<? extends GrantedAuthority> authorities = AuthorityUtils
                .createAuthorityList(authSet.toArray(new String[0]));

        return new MyUser(vo.getId(),
                vo.getMobile(),
                vo.getUserName(),
                SecurityConstants.BCRYPT + vo.getPassword(),
                vo.getUserName(),
                !vo.getIsEnabled(),
                true,
                true,
                !vo.getIsLocked(),
                authorities);
    }

    private void checkUserDetail(SysUserBaseVO vo) {

        if (Objects.isNull(vo) || Objects.isNull(vo.getId())) {
            throw new UsernameNotFoundException(messages.getMessage(
                    "AbstractUserDetailsAuthenticationProvider.userNameNotFound",
                    "The Username does not exist"));
        }
        // TODO 其他校验逻辑
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