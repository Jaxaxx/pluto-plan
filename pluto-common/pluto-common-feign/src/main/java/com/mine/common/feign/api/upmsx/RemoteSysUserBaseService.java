package com.mine.common.feign.api.upmsx;

import com.mine.common.core.util.R;
import com.mine.common.feign.interceptor.MyFeignRequestInterceptor;
import com.mine.common.feign.entity.SysUserBaseVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "upmsx", configuration = MyFeignRequestInterceptor.class)
public interface RemoteSysUserBaseService {

    @GetMapping("/feign/sysUserBase/{userName}")
    R<SysUserBaseVO> getUserByUserName(@PathVariable("userName") String userName);

    @GetMapping("/feign/test")
    R<String> test();
}
