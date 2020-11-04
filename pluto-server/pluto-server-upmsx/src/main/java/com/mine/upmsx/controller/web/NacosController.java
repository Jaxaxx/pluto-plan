package com.mine.upmsx.controller.web;

import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.ServiceInfo;
import org.springframework.beans.factory.annotation.Value;
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

    @NacosInjected
    private NamingService namingService;

    @GetMapping(value = "test")
    public List<ServiceInfo> get(@RequestParam String serviceName) throws NacosException {
        return namingService.getSubscribeServices();
//        return namingService.getAllInstances(serviceName);
    }

}
