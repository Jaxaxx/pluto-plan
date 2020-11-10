package com.mine.common.feign.api.upmsx;

import com.mine.common.core.constant.ServiceNameConstants;
import com.mine.common.feign.entity.upmsx.SysLog;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author LiMing
 */
@FeignClient(value = ServiceNameConstants.UPMS)
public interface RemoteSysLogService {

    @PostMapping("/feign/sysLog/")
    void saveLog(SysLog sysLog);
}
