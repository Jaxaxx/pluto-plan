package com.mine.common.swagger.aspect;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import springfox.documentation.spring.web.json.Json;

import javax.servlet.http.HttpServletRequest;

@Component
@Aspect
@RequiredArgsConstructor
public class SwaggerAspect {

    private final Environment environment;

    @Around("execution(* springfox.documentation.oas.web.OpenApiControllerWebMvc.*(..))")
    public Object handleControllerMethod(ProceedingJoinPoint pjp) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Object proceed = pjp.proceed();
        JSONObject responseEntity = JSONObject.parseObject(JSON.toJSONString(proceed), JSONObject.class);
        JSONObject body = responseEntity.getJSONObject("body");
        JSONArray sourceServers = body.getJSONArray("servers");
        JSONObject serverValue = (JSONObject) sourceServers.get(0);
        String sourcecUrl = serverValue.getString("url");
        serverValue.put("url", sourcecUrl + "/" + environment.getProperty("spring.application.name"));
        JSONArray servers = new JSONArray();
        servers.add(serverValue);
        body.put("servers", servers);
        return ResponseEntity.ok(new Json(body.toString()));
    }
}
