package com.mine.common.log.event;

import com.mine.common.feign.entity.upmsx.SysLog;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author lengleng 系统日志事件
 */
@Getter
@AllArgsConstructor
public class SysLogEvent {

    private SysLog sysLog;

}
