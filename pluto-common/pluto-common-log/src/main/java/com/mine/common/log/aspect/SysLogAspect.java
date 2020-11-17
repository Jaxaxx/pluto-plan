package com.mine.common.log.aspect;

import cn.hutool.core.util.URLUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.http.HttpUtil;
import com.mine.common.log.annotation.SysLog;
import com.mine.common.log.constant.LogTypeEnum;
import com.mine.common.log.event.SysLogEvent;
import com.mine.common.log.util.SysLogUtil;
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
        com.mine.common.feign.entity.upmsx.SysLog sysLog = SysLogUtil.getSysLog(AsysLog);
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

}
