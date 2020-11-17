package com.mine.common.log.aspect;

import cn.hutool.core.util.URLUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.http.HttpUtil;
import com.mine.common.log.annotation.SysLog;
import com.mine.common.log.constant.LogTypeEnum;
import com.mine.common.log.event.SysLogEvent;
import com.mine.common.security.filter.BodyReaderHttpServletRequestWrapper;
import com.mine.common.security.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Objects;

/**
 * 操作日志使用spring event异步入库
 *
 * @author L.cm
 */
@Order(-1)
@Aspect
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
    public static com.mine.common.feign.entity.upmsx.SysLog getSysLog(SysLog AsysLog) {
        HttpServletRequest request = ((ServletRequestAttributes) Objects
                .requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        com.mine.common.feign.entity.upmsx.SysLog sysLog = new com.mine.common.feign.entity.upmsx.SysLog();
        if (Objects.nonNull(AsysLog)) {
            sysLog.setTitle(AsysLog.value());
        }
        String paramString = "";
        try {
            paramString = new BodyReaderHttpServletRequestWrapper(request).getBodyString(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
        sysLog.setType(LogTypeEnum.NORMAL.getType());
        sysLog.setRemoteAddr(ServletUtil.getClientIP(request));
        sysLog.setRequestUri(URLUtil.getPath(request.getRequestURI()));
        sysLog.setMethod(request.getMethod());
        sysLog.setParams(paramString);
        sysLog.setUserAgent(request.getHeader("user-agent"));
        sysLog.setServiceId(request.getHeader("X-Forwarded-Prefix"));
        sysLog.setCreateUserId(SecurityUtils.getUser().getId());
        sysLog.setCreateUserName(SecurityUtils.getUsername());
        return sysLog;
    }

}
