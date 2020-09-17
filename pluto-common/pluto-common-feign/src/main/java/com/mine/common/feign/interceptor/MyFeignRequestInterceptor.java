package com.mine.common.feign.interceptor;

import cn.hutool.http.Header;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.mine.common.core.util.WebUtils;
import com.mine.common.security.config.PermitAllUrlProperties;
import feign.Feign;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.security.oauth2.client.AccessTokenContextRelay;
import org.springframework.cloud.security.oauth2.client.feign.OAuth2FeignRequestInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

/**
 * @Description 扩展OAuth2FeignRequestInterceptor
 * @Author
 * @Date
 */
@Slf4j
@ConditionalOnClass(Feign.class)
@ConditionalOnProperty("security.oauth2.client.client-id")
public class MyFeignRequestInterceptor extends OAuth2FeignRequestInterceptor {

    private static final String AUTHORIZATION_PREFIX = "bearer";
    private final OAuth2ClientContext oAuth2ClientContext;
    private final AccessTokenContextRelay accessTokenContextRelay;


    @Autowired
    private PermitAllUrlProperties permitAllUrlProperties;

    public MyFeignRequestInterceptor(OAuth2ClientContext oAuth2ClientContext
            , OAuth2ProtectedResourceDetails resource, AccessTokenContextRelay accessTokenContextRelay) {
        super(oAuth2ClientContext, resource);
        this.oAuth2ClientContext = oAuth2ClientContext;
        this.accessTokenContextRelay = accessTokenContextRelay;
    }


    /**
     * Create a template with the header of provided name and extracted extract
     * 1. 如果使用 非web 请求，header 区别
     * 2. 根据authentication 还原请求token
     *
     * @param template
     */
    @Override
    public void apply(RequestTemplate template) {
        List<String> ignoreUrls = permitAllUrlProperties.getIgnoreUrls();
        if (CollectionUtils.isNotEmpty(ignoreUrls)) {
            for (String ignoreUrl : ignoreUrls) {
                if (template.url().startsWith(ignoreUrl.replace("*", ""))) {
                    return;
                }
            }
        }
//        HttpServletRequest request = WebUtils.getRequest();
//        String accessToken = request.getHeader(Header.AUTHORIZATION.name());
//        if (!accessToken.toLowerCase().startsWith(AUTHORIZATION_PREFIX)) {
//            log.debug("No authorization required interface : {}", template.url());
//            return;
//        }
//        template.header(Header.AUTHORIZATION.name(), new String[]{accessToken});

        accessTokenContextRelay.copyToken();
        if (oAuth2ClientContext != null && oAuth2ClientContext.getAccessToken() != null) {
            super.apply(template);
        }

    }
}
