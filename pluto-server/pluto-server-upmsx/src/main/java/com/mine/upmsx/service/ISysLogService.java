package com.mine.upmsx.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mine.common.feign.entity.upmsx.SysLog;
import com.mine.upmsx.vo.SysLogVO;
import com.mine.upmsx.dto.SysLogDTO;

import java.util.List;

/**
 * @description: 【日志表】代理层
 * @author: Generated by jax-li
 * @create: 2020-08-20
 */
public interface ISysLogService extends IService<SysLog> {

    List<SysLogVO> list(SysLogDTO dto);

    SysLogVO detail(Long id);

    void insert(SysLogDTO dto);

    void update(SysLogDTO dto);

    void delete(Long id);

}
