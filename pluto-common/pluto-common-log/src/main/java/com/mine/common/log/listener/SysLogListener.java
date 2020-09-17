package com.mine.common.log.listener;

import com.mine.common.feign.api.upmsx.RemoteSysLogService;
import com.mine.common.feign.entity.upmsx.SysLog;
import com.mine.common.log.event.SysLogEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;

/**
 * @author lengleng 异步监听日志事件
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class SysLogListener {

    private final RemoteSysLogService remoteSysLogService;

    @Async
    @EventListener(SysLogEvent.class)
    public void saveSysLog(SysLogEvent event) {
        SysLog sysLog = event.getSysLog();
        remoteSysLogService.saveLog(sysLog);
    }

}
