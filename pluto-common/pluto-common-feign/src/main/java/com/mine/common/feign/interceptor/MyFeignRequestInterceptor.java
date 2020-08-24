package com.mine.common.feign.interceptor;

import cn.hutool.http.Header;
import com.mine.common.core.util.WebUtils;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description 扩展OAuth2FeignRequestInterceptor
 * @Author
 * @Date
 */
@Slf4j
@AllArgsConstructor
public class MyFeignRequestInterceptor implements RequestInterceptor {

    private static final String AUTHORIZATION_PREFIX = "bearer";

    /**
     * Create a template with the header of provided name and extracted extract
     * 1. 如果使用 非web 请求，header 区别
     * 2. 根据authentication 还原请求token
     *
     * @param template
     */
    @Override
    public void apply(RequestTemplate template) {
        HttpServletRequest request = WebUtils.getRequest();
        String accessToken = request.getHeader(Header.AUTHORIZATION.name());
        if (!accessToken.toLowerCase().startsWith(AUTHORIZATION_PREFIX)) {
            log.debug("No authorization required interface : {}", template.url());
            return;
        }
        template.header(Header.AUTHORIZATION.name(), new String[]{accessToken});
    }
}
