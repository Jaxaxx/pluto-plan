package com.mine.common.security.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface MyUserDetailsService extends UserDetailsService {

    UserDetails loadAppUserByPhone(String principal);

    UserDetails loadUserBySocial(String principal);
}