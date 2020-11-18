package com.mine.common.feign.api.auth;

import com.mine.common.core.constant.ServiceNameConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Map;

@FeignClient(value = ServiceNameConstants.AUTH,path = "/oauth")
public interface RemoteAuthService {

    @PostMapping("token")
    ResponseEntity<OAuth2AccessToken> token(Principal principal,
                                            @RequestParam Map<String, String> parameters,
                                            @RequestHeader(name = "Authorization",required = true) String token);

}
