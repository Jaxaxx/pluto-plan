package com.mine.common.feign.api.system;

import com.mine.common.core.constant.ServiceNameConstants;
import com.mine.common.feign.entity.upmsx.SysLog;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author Jax-li
 */
@FeignClient(value = ServiceNameConstants.SYSTEM)
public interface RemoteTestService {

    @GetMapping("/feign/test/")
    Object test();
}
