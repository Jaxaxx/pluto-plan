/*
 * Copyright 2004, 2005, 2006 Acegi Technology Pty Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.security.web.authentication.www;

import com.alibaba.nacos.common.http.param.MediaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mine.common.core.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Used by the <code>ExceptionTranslationFilter</code> to commence authentication via the
 * {@link BasicAuthenticationFilter}.
 * <p>
 * Once a user agent is authenticated using BASIC authentication, logout requires that the
 * browser be closed or an unauthorized (401) header be sent. The simplest way of
 * achieving the latter is to call the
 * {@link #commence(HttpServletRequest, HttpServletResponse, AuthenticationException)}
 * method below. This will indicate to the browser its credentials are no longer
 * authorized, causing it to prompt the user to login again.
 *
 * @author Ben Alex
 */
public class BasicAuthenticationEntryPoint implements AuthenticationEntryPoint,
        InitializingBean {
    // ~ Instance fields
    // ================================================================================================
    private Logger log = LoggerFactory.getLogger(this.getClass());
    private String realmName;

    // ~ Methods
    // ========================================================================================================

    public void afterPropertiesSet() {
        Assert.hasText(realmName, "realmName must be specified");
    }

    /**
     * 获取token接口header中的密钥解密出错在这里转换
     *
     * @param request  request
     * @param response response
     * @param ex       ex
     * @throws IOException ex
     */
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException ex) throws IOException {
        response.addHeader("WWW-Authenticate", "Basic realm=\"" + realmName + "\"");
//		response.sendError(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase());
        response.setContentType(MediaType.APPLICATION_JSON);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        Result<Object> ret = Result.fail(ex);
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(response.getOutputStream(), ret);
        } catch (Exception e) {
            throw new IOException();
        }
    }

    public String getRealmName() {
        return realmName;
    }

    public void setRealmName(String realmName) {
        this.realmName = realmName;
    }

}
