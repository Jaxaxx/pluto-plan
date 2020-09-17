package com.mine.common.log.util;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.mine.common.core.util.R;
import io.swagger.annotations.ApiOperation;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.StringJoiner;

@Slf4j
@UtilityClass
public class AccessLogUtil {

    public void log(HttpServletRequest request, ProceedingJoinPoint joinPoint, Object result, Long time) {
        StringJoiner logInfo = new StringJoiner("\n", "\n", "");
        logInfo.add("====↓=↓=↓===== " + request.getRequestURL());
        logInfo.add("===REQ_INFO=== " + getTitle(joinPoint));
        logInfo.add("===REQ_ADDR=== " + request.getMethod() + "\t" + request.getRequestURI());
        logInfo.add("===REQ_ARGS=== " + HttpUtil.toParams(request.getParameterMap()));
        logInfo.add("===REQ_TIME=== " + time + "ms");
        logInfo.add("===RES_DATA=== " + (Objects.isNull(result) ? JSONUtil.toJsonStr(R.ok()) : JSONUtil.toJsonStr(result)));
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
