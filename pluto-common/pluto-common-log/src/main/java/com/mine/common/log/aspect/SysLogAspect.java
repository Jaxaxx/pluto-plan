package com.mine.common.log.aspect;

import cn.hutool.core.util.URLUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.http.HttpUtil;
import com.mine.common.core.util.WebUtils;
import com.mine.common.log.annotation.SysLog;
import com.mine.common.log.constant.LogTypeEnum;
import com.mine.common.log.event.SysLogEvent;
import com.mine.common.log.util.AccessLogUtil;
import com.mine.common.security.util.SecurityUtils;
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

    @Around("@annotation(AsysLog)")
    @SneakyThrows
    public Object around(ProceedingJoinPoint point, SysLog AsysLog) {

        com.mine.common.feign.entity.upmsx.SysLog sysLog = getSysLog(AsysLog);
        // 发送异步日志事件
        Long startTime = System.currentTimeMillis();
        Object result = new Object();

        try {
            result = point.proceed();
        } catch (Exception e) {
            sysLog.setType(LogTypeEnum.ERROR.getType());
            sysLog.setException(e.getMessage());
            throw e;
        } finally {
            long endTime = System.currentTimeMillis();
            sysLog.setTime(endTime - startTime);
            publisher.publishEvent(new SysLogEvent(sysLog));
        }
        return result;
    }

    /**
     * 构建日志存储对象
     *
     * @param AsysLog
     * @return
     */
    private com.mine.common.feign.entity.upmsx.SysLog getSysLog(SysLog AsysLog) {
        HttpServletRequest request = ((ServletRequestAttributes) Objects
                .requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        com.mine.common.feign.entity.upmsx.SysLog sysLog = new com.mine.common.feign.entity.upmsx.SysLog();
        sysLog.setTitle(AsysLog.value());
        sysLog.setType(LogTypeEnum.NORMAL.getType());
        sysLog.setRemoteAddr(ServletUtil.getClientIP(request));
        sysLog.setRequestUri(URLUtil.getPath(request.getRequestURI()));
        sysLog.setMethod(request.getMethod());
        sysLog.setUserAgent(request.getHeader("user-agent"));
        sysLog.setParams(HttpUtil.toParams(request.getParameterMap()));
        sysLog.setServiceId(request.getHeader("X-Forwarded-Prefix"));
        sysLog.setCreateUserId(SecurityUtils.getUser().getId());
        sysLog.setCreateUserName(SecurityUtils.getUsername());
        return sysLog;
    }

}
