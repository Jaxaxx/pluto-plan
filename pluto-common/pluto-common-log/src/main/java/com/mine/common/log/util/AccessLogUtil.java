package com.mine.common.log.util;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.mine.common.core.result.Result;
import com.mine.common.security.filter.BodyReaderHttpServletRequestWrapper;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.StringJoiner;

@Slf4j
@Component
public class AccessLogUtil {

    @Async
    public void log(HttpServletRequest request, ProceedingJoinPoint joinPoint, Object result) {
        StringJoiner logInfo = new StringJoiner("\n", "\n", "");
        logInfo.add(request.getMethod() + "\t" + request.getRequestURI() + "\t" + getTitle(joinPoint));
        String paramString = "";
        try {
            paramString = HttpUtil.toParams(request.getParameterMap());
            if (paramString.isEmpty()) {
                paramString = new BodyReaderHttpServletRequestWrapper(request).getBodyString(request);
            }
        } catch (IOException e) {

        }
        logInfo.add("request_param:" + "\t" + paramString);
        logInfo.add("response_data:" + "\t"  + (Objects.isNull(result) ? JSONUtil.toJsonStr(Result.ok()) : JSONUtil.toJsonStr(result)));
        log.debug(logInfo.toString());
    }

    private String getTitle(JoinPoint joinPoint) {
        Method method = getMethod(joinPoint);
        ApiOperation apiOperation = method.getAnnotation(ApiOperation.class);
        String value = "";
        if (null != apiOperation) {
            value = apiOperation.value();
        }
        return value;
    }

    private Method getMethod(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        return methodSignature.getMethod();
    }

}
