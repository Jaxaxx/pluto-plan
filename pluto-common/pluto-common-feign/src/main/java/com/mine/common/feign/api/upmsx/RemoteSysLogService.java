package com.mine.common.feign.api.upmsx;

import com.mine.common.feign.interceptor.MyFeignRequestInterceptor;
import com.mine.common.feign.entity.upmsx.SysLog;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "upmsx", configuration = MyFeignRequestInterceptor.class)
public interface RemoteSysLogService {

    @PostMapping("/feign/sysLog/")
    void saveLog(SysLog sysLog);
}
