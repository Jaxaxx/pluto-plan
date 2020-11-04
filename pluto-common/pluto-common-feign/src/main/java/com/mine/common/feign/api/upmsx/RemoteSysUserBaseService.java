package com.mine.common.feign.api.upmsx;

import com.mine.common.core.util.R;
import com.mine.common.feign.entity.SysUserBaseVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author jax-li
 */
@FeignClient(value = "upmsx")
public interface RemoteSysUserBaseService {

    /**
     * 通过用户名查询用户基本信息
     *
     * @param userName 用户名
     * @return SysUserBaseVO系统用户基础VO
     */
    @GetMapping("/feign/sysUserBase/{userName}")
    SysUserBaseVO getUserByUserName(@PathVariable("userName") String userName);

    @GetMapping("/feign/test")
    R<String> test();
}
