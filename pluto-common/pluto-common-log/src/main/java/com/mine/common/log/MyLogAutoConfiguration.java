package com.mine.common.log;

import com.mine.common.feign.api.upmsx.RemoteSysLogService;
import com.mine.common.log.aspect.SysLogAspect;
import com.mine.common.log.event.SysLogListener;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@Configuration
@RequiredArgsConstructor
@ConditionalOnWebApplication
public class MyLogAutoConfiguration {

    private final RemoteSysLogService remoteLogService;

    @Bean
    public SysLogListener sysLogListener() {
        return new SysLogListener(remoteLogService);
    }

    @Bean
    public SysLogAspect sysLogAspect() {
        return new SysLogAspect();
    }

}
