package com.mine.common.security.converter;

import cn.hutool.core.convert.Convert;
import com.mine.common.core.constant.SecurityConstants;
import com.mine.common.security.model.MyUser;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.token.UserAuthenticationConverter;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 用户转换器
 *
 * @author Y
 * @date 2020-07-02 10:24
 */
public class MyUserConverter implements UserAuthenticationConverter {

    private static final String N_A = "N/A";

    @Override
    public Map<String, ?> convertUserAuthentication(Authentication userAuthentication) {
        Map<String, Object> authMap = new LinkedHashMap<>();
        authMap.put(USERNAME, userAuthentication.getName());
        if (userAuthentication.getAuthorities() != null && !userAuthentication.getAuthorities().isEmpty()) {
            authMap.put(AUTHORITIES, AuthorityUtils.authorityListToSet(userAuthentication.getAuthorities()));
        }
        return authMap;
    }

    @Override
    public Authentication extractAuthentication(Map<String, ?> map) {
        if (map.containsKey(USERNAME)) {
            Collection<? extends GrantedAuthority> authorities = getAuthorities(map);

            Long userId = Convert.toLong(map.get(SecurityConstants.DETAILS_USER_ID));
            String username = (String) map.get(SecurityConstants.DETAILS_USERNAME);
            String phone = (String) map.get(SecurityConstants.DETAILS_PHONE);
            String name = (String) map.get(SecurityConstants.DETAILS_NAME);
            MyUser user = new MyUser(userId, phone, username, N_A, name,true, true, true, true, authorities);
            return new UsernamePasswordAuthenticationToken(user, N_A, authorities);
        }
        return null;
    }

    /**
     * 获取权限资源信息
     */
    private Collection<? extends GrantedAuthority> getAuthorities(Map<String, ?> map) {
        Object authorities = map.get(AUTHORITIES);
        if (authorities instanceof String) {
            return AuthorityUtils.commaSeparatedStringToAuthorityList((String) authorities);
        }
        if (authorities instanceof Collection) {
            return AuthorityUtils.commaSeparatedStringToAuthorityList(
                    StringUtils.collectionToCommaDelimitedString((Collection<?>) authorities));
        }
        return AuthorityUtils.commaSeparatedStringToAuthorityList("");
    }

}