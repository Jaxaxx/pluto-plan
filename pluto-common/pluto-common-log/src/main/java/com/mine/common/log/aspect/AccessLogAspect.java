package com.mine.common.log.aspect;

import com.mine.common.core.util.WebUtils;
import com.mine.common.log.util.AccessLogUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(-11)
@Aspect
@Slf4j
@Component
public class AccessLogAspect {

    @SneakyThrows
    @Around("execution(* com.mine.*.controller.web.*.*(..))")
    public Object around(ProceedingJoinPoint point) {

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

}
