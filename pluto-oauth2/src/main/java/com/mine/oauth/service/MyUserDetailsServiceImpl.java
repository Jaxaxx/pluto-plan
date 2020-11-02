package com.mine.oauth.service;

import com.mine.common.core.util.R;
import com.mine.common.feign.api.upmsx.RemoteSysUserBaseService;
import com.mine.common.feign.entity.SysUserBaseVO;
import com.mine.common.security.model.MyUser;
import com.mine.common.security.service.MyUserDetailsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@AllArgsConstructor
public class MyUserDetailsServiceImpl implements MyUserDetailsService {

    private final RemoteSysUserBaseService remoteSysUserBaseService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUserBaseVO resultVo = remoteSysUserBaseService.getUserByUserName(username);
        return getUserDetail(resultVo);
    }

    private UserDetails getUserDetail(SysUserBaseVO vo) {
        if (Objects.isNull(vo) || Objects.isNull(vo.getId())) {
            throw new UsernameNotFoundException("用户不存在");
        }
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));


        return new MyUser(vo.getId(), vo.getMobile(), vo.getUserName(), vo.getPassword(), vo.getUserName(), true, true, true, true, authorities);
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