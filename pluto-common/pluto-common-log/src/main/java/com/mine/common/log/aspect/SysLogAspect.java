package com.mine.common.log.aspect;

import com.mine.common.log.annotation.SysLog;
import com.mine.common.log.event.SysLogEvent;
import com.mine.common.log.util.LogTypeEnum;
import com.mine.common.log.util.SysLogUtils;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.ApplicationEventPublisher;

/**
 * 操作日志使用spring event异步入库
 *
 * @author L.cm
 */
@Aspect
@Slf4j
@RequiredArgsConstructor
public class SysLogAspect {

    private final ApplicationEventPublisher publisher;

    @Around("@annotation(sysLog)")
    @SneakyThrows
    public Object around(ProceedingJoinPoint point, SysLog sysLog) {
        String strClassName = point.getTarget().getClass().getName();
        String strMethodName = point.getSignature().getName();
        log.debug("[类名]:{},[方法]:{}", strClassName, strMethodName);

        com.mine.common.feign.entity.upmsx.SysLog logVo = SysLogUtils.getSysLog();
        logVo.setTitle(sysLog.value());

        // 发送异步日志事件
        Long startTime = System.currentTimeMillis();
        Object obj;

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

}
