package com.mine.common.security.aspect;


import com.mine.common.core.result.Result;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Component;

/**
 * 通过AOP切面来改变OAuth2 生成access_token返回值格式
 *
 * @author Jax-li
 */
@Component
@Aspect
public class AuthTokenAspect {

    /**
     * swagger 固定 scope
     */
    private static final String SWAGGER_SCOPE = "swagger";

    /**
     * Around注解 改变oauth/token返回值
     *
     * @param pjp ProceedingJoinPoint
     * @return ResponseEntity
     * @throws Throwable ex
     */
    @Around("execution(* org.springframework.security.oauth2.provider.endpoint.TokenEndpoint.postAccessToken(..))")
    @SuppressWarnings("unchecked")
    public Object handleAuthToken(ProceedingJoinPoint pjp) throws Throwable {
        Object proceed = pjp.proceed();
        ResponseEntity<OAuth2AccessToken> responseEntity = (ResponseEntity<OAuth2AccessToken>) proceed;
        OAuth2AccessToken body = responseEntity.getBody();
        if (body != null && body.getScope().contains(SWAGGER_SCOPE)) {
            // swagger 验证不修改返回值，否则swagger 无法认证
            return proceed;
        }
        return ResponseEntity.ok(Result.ok(body));
    }
}