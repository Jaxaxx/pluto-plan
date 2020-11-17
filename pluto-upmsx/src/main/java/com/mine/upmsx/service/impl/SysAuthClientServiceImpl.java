package com.mine.upmsx.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mine.upmsx.entity.SysAuthClient;
import com.mine.upmsx.mapper.SysAuthClientMapper;
import com.mine.upmsx.service.ISysAuthClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 【客户端信息表】实现层
 *
 * @author jax-li
 * @date 2020-11-17
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysAuthClientServiceImpl extends ServiceImpl<SysAuthClientMapper, SysAuthClient> implements ISysAuthClientService {

}
