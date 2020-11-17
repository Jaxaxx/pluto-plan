package org.springframework.security.oauth2.provider.sms;

import com.mine.common.core.constant.SecurityConstants;
import com.mine.common.security.constant.GrantTypeConstant;
import com.mine.common.security.util.ApplicationContextAwareUtil;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AbstractTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Jax-li
 */
public class SmsTokenGranter extends AbstractTokenGranter {

    private static final String GRANT_TYPE = GrantTypeConstant.SMS;
    private static final String USER_DETAIL_SERVICE_NAME = SecurityConstants.USER_DETAIL_SERVICE_NAME;

    private final AuthenticationManager authenticationManager;

    public SmsTokenGranter(AuthenticationManager authenticationManager,
                           AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory) {
        this(authenticationManager, tokenServices, clientDetailsService, requestFactory, GRANT_TYPE);
    }

    protected SmsTokenGranter(AuthenticationManager authenticationManager, AuthorizationServerTokenServices tokenServices,
                              ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory, String grantType) {
        super(tokenServices, clientDetailsService, requestFactory, grantType);
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {

        Map<String, String> parameters = new LinkedHashMap<>(tokenRequest.getRequestParameters());

        String username = parameters.get("username");
        UserDetailsService userDetailsService = ApplicationContextAwareUtil.getBean(USER_DETAIL_SERVICE_NAME);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        try {
            // 先按照框架的过滤器链顺序校验用户名再校验验证码，否则提示不友好
            SmsGrantTypePreProcessor.doHandler();
        } catch (BadCredentialsException ex) {
            throw new InvalidGrantException(ex.getMessage());
        }
        String password = userDetails.getPassword();
        // Protect from downstream leaks of password
        Authentication userAuth = new UsernamePasswordAuthenticationToken(username, password);
        ((AbstractAuthenticationToken) userAuth).setDetails(parameters);
        try {
            userAuth = authenticationManager.authenticate(userAuth);
        } catch (AccountStatusException ase) {
            //covers expired, locked, disabled cases (mentioned in section 5.2, draft 31)
            throw new InvalidGrantException(ase.getMessage());
        } catch (BadCredentialsException e) {
            // If the username/password are wrong the spec says we should send 400/invalid grant
            throw new InvalidGrantException(e.getMessage());
        }
        if (userAuth == null || !userAuth.isAuthenticated()) {
            throw new InvalidGrantException("Could not authenticate user: " + username);
        }

        OAuth2Request storedOAuth2Request = getRequestFactory().createOAuth2Request(client, tokenRequest);
        return new OAuth2Authentication(storedOAuth2Request, userAuth);
    }

}
