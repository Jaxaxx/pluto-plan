//package com.mine.common.security.handler;
//
//import cn.hutool.core.map.MapUtil;
//import cn.hutool.core.util.CharsetUtil;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.mine.common.security.util.AuthUtils;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Lazy;
//import org.springframework.http.MediaType;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.oauth2.common.OAuth2AccessToken;
//import org.springframework.security.oauth2.provider.*;
//import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
//import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.io.PrintWriter;
//
//@Slf4j
//@Configuration
//public class MobileLoginSuccessHandler implements AuthenticationSuccessHandler {
//
//    private static final String GRANT_TYPE_MOBILE = "mobile";
//    @Autowired
//    private ClientDetailsService clientDetailsService;
//    @Autowired
//    private ObjectMapper objectMapper;
//    @Autowired
//    @Lazy
//    private AuthorizationServerTokenServices defaultAuthorizationServerTokenServices;
//
//    /**
//     * Called when a user has been successfully authenticated.
//     * <p>
//     * 调用spring security oauth API 生成 oAuth2AccessToken
//     *
//     * @param request        the request which caused the successful authentication
//     * @param response       the response
//     * @param authentication the <tt>Authentication</tt> object which was created during
//     */
//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
//        String[] tokens = AuthUtils.extractAndDecodeHeader(request);
//        assert tokens.length == 2;
//        String clientId = tokens[0];
//        String clientSecret = tokens[1];
//
//        ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);
//        TokenRequest tokenRequest = new TokenRequest(MapUtil.newHashMap(), clientId, clientDetails.getScope(), GRANT_TYPE_MOBILE);
//        OAuth2Request auth2Request = tokenRequest.createOAuth2Request(clientDetails);
//        OAuth2Authentication auth2Authentication = new OAuth2Authentication(auth2Request, authentication);
//        OAuth2AccessToken auth2AccessToken = defaultAuthorizationServerTokenServices.createAccessToken(auth2Authentication);
//        log.info("获取token成功 :{}", auth2AccessToken.getValue());
//
//        response.setCharacterEncoding(CharsetUtil.UTF_8);
//        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
//        try {
//            PrintWriter printWriter = response.getWriter();
//            printWriter.append(objectMapper.writeValueAsString(auth2AccessToken));
//        } catch (IOException e) {
//            throw new BadCredentialsException("Failed to decode basic authentication token");
//        }
//    }
//}
