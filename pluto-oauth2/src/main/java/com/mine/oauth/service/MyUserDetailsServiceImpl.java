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

@Slf4j
@Service
@AllArgsConstructor
public class MyUserDetailsServiceImpl implements MyUserDetailsService {

    private final RemoteSysUserBaseService remoteSysUserBaseService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        /**
         *  Remote Request upms to get user_info
         */
        R<SysUserBaseVO> result = remoteSysUserBaseService.getUserByUserName(username);

        UserDetails userDetails = getUserDetail(result);

        return userDetails;
    }

    private UserDetails getUserDetail(R<SysUserBaseVO> data) {
        if (Objects.isNull(data) || Objects.isNull(data.getData()) || Objects.isNull(data.getData().getId())) {
            throw new UsernameNotFoundException("用户不存在");
        }
        SysUserBaseVO vo = data.getData();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));


        MyUser myUser = new MyUser(vo.getId(), vo.getMobile(), vo.getUserName(), vo.getPassword(), vo.getUserName(), true, true, true, true, authorities);

        return myUser;
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