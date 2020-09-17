package com.mine.common.log.aspect;

import com.mine.common.core.util.WebUtils;
import com.mine.common.log.util.AccessLogUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.core.log.LogFormatUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Order(-11)
@Aspect
@Slf4j
@Component
public class AccessLogAspect {

    private final Log logger = LogFactory.getLog(getClass());

    @SneakyThrows
    @Around("execution(* com.mine.*.controller.web.*.*(..))")
    public Object webAround(ProceedingJoinPoint point) {

        Long startTime = System.currentTimeMillis();
        Object obj = new Object();

        try {
            obj = point.proceed();
        } catch (Exception e) {
            throw e;
        } finally {
            long endTime = System.currentTimeMillis();
            AccessLogUtil.log(WebUtils.getRequest(), point, obj, endTime - startTime);
        }
        return obj;
    }

    @SneakyThrows
    @Around("execution(* com.mine.*.controller.feign.*.*(..))")
    public Object feignAround(ProceedingJoinPoint point) {
        Object obj = new Object();
        try {
            obj = point.proceed();
            HttpServletRequest request = WebUtils.getRequest();
            log.debug("[feign] : {} {}, {}", request.getMethod(), request.getRequestURI(), request.getParameterMap());
        } catch (Exception e) {
            throw e;
        } finally {

        }
        return obj;
    }

}
