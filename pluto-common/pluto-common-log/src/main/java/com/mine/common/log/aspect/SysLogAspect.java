package com.mine.common.log.aspect;

import cn.hutool.core.util.URLUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.http.HttpUtil;
import com.mine.common.core.util.WebUtils;
import com.mine.common.log.annotation.SysLog;
import com.mine.common.log.constant.LogTypeEnum;
import com.mine.common.log.event.SysLogEvent;
import com.mine.common.log.util.AccessLogUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * 操作日志使用spring event异步入库
 *
 * @author L.cm
 */
@Order(-1)
@Aspect
@Slf4j
@Component
@RequiredArgsConstructor
public class SysLogAspect {

    private final ApplicationEventPublisher publisher;

    @Around("@annotation(sysLog)")
    @SneakyThrows
    public Object around(ProceedingJoinPoint point, SysLog sysLog) {

        com.mine.common.feign.entity.upmsx.SysLog logVo = getSysLog(sysLog);
        // 发送异步日志事件
        Long startTime = System.currentTimeMillis();
        Object obj = new Object();

        try {
            obj = point.proceed();
        } catch (Exception e) {
            logVo.setType(LogTypeEnum.ERROR.getType());
            logVo.setException(e.getMessage());
            throw e;
        } finally {
            long endTime = System.currentTimeMillis();
            logVo.setTime(endTime - startTime);
            publisher.publishEvent(new SysLogEvent(logVo));
        }
        return obj;
    }

    /**
     * 构建日志存储对象
     *
     * @param sysLog
     * @return
     */
    private com.mine.common.feign.entity.upmsx.SysLog getSysLog(SysLog sysLog) {
        HttpServletRequest request = ((ServletRequestAttributes) Objects
                .requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        com.mine.common.feign.entity.upmsx.SysLog logEneity = new com.mine.common.feign.entity.upmsx.SysLog();
        logEneity.setTitle(sysLog.value());
        logEneity.setType(LogTypeEnum.NORMAL.getType());
        logEneity.setRemoteAddr(ServletUtil.getClientIP(request));
        logEneity.setRequestUri(URLUtil.getPath(request.getRequestURI()));
        logEneity.setMethod(request.getMethod());
        logEneity.setUserAgent(request.getHeader("user-agent"));
        logEneity.setParams(HttpUtil.toParams(request.getParameterMap()));
        logEneity.setServiceId(request.getHeader("X-Forwarded-Prefix"));
        logEneity.setRequestAttributes(RequestContextHolder.currentRequestAttributes());
        return logEneity;
    }

}
