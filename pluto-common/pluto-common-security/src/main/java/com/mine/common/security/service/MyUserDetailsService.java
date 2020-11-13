package com.mine.common.security.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author Jax-li
 */
public interface MyUserDetailsService extends UserDetailsService {

    /**
     * loadAppUserByPhone
     *
     * @param principal
     * @return UserDetails
     */
    UserDetails loadAppUserByPhone(String principal);

    UserDetails loadUserBySocial(String principal);
}