package com.mine.common.log.aspect;

import com.mine.common.core.util.SpringContextHolder;
import com.mine.common.feign.entity.upmsx.SysLog;
import com.mine.common.log.annotation.OperationLog;
import com.mine.common.log.event.SysLogEvent;
import com.mine.common.log.util.LogTypeEnum;
import com.mine.common.log.util.SysLogUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * 操作日志使用spring event异步入库
 *
 * @author L.cm
 */
@Aspect
@Slf4j
public class SysLogAspect {

    @Around("@annotation(com.mine.common.log.annotation.OperationLog)")
    @SneakyThrows
    public Object around(ProceedingJoinPoint point, OperationLog operationLog) {
        String strClassName = point.getTarget().getClass().getName();
        String strMethodName = point.getSignature().getName();
        log.debug("[类名]:{},[方法]:{}", strClassName, strMethodName);

        SysLog logVo = SysLogUtils.getSysLog();
        logVo.setTitle(operationLog.value());

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
            logVo.setTime(String.valueOf((endTime - startTime)));
            SpringContextHolder.publishEvent(new SysLogEvent(logVo));
        }

        return obj;
    }

}
