package com.mine.upmsx.controller.web;

import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.ServiceInfo;
import com.mine.common.security.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author LiMing
 */
@RestController
@RequestMapping("get")
public class NacosController {

    private DiscoveryClient discoveryClient;

    @GetMapping(value = "test")
    public List<?> get() throws NacosException {

        Authentication authentication = SecurityUtils.getAuthentication();
        String clientId = SecurityUtils.getClientId();

        return discoveryClient.getServices();
//        return namingService.getAllInstances(serviceName);
    }

}
