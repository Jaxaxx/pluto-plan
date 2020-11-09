package com.mine.oauth.aspect;


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
 * @author LiMing
 */
@Component
@Aspect
public class AuthTokenAspect {

    /**
     * Around注解 改变controller返回值的
     *
     * @param pjp ProceedingJoinPoint
     * @return ResponseEntity
     * @throws Throwable
     */
    @Around("execution(* org.springframework.security.oauth2.provider.endpoint.TokenEndpoint.postAccessToken(..))")
    public Object handleControllerMethod(ProceedingJoinPoint pjp) throws Throwable {
        Object proceed = pjp.proceed();
        ResponseEntity<OAuth2AccessToken> responseEntity = (ResponseEntity<OAuth2AccessToken>) proceed;
        OAuth2AccessToken body = responseEntity.getBody();
        return ResponseEntity.ok(Result.ok(body));
    }
}