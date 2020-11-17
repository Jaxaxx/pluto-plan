package com.mine.common.log.util;

import cn.hutool.core.util.URLUtil;
import cn.hutool.extra.servlet.ServletUtil;
import com.mine.common.log.annotation.SysLog;
import com.mine.common.log.constant.LogTypeEnum;
import com.mine.common.security.filter.BodyReaderHttpServletRequestWrapper;
import com.mine.common.security.util.SecurityUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Objects;

/**
 * 系统日志工具
 *
 * @author LiMing
 */
public class SysLogUtil {

    /**
     * 构建日志存储对象
     *
     * @param AsysLog
     * @return
     */
    public static com.mine.common.feign.entity.upmsx.SysLog getSysLog(SysLog AsysLog) {
        HttpServletRequest request = ((ServletRequestAttributes) Objects
                .requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        com.mine.common.feign.entity.upmsx.SysLog sysLog = new com.mine.common.feign.entity.upmsx.SysLog();
        if (Objects.nonNull(AsysLog)) {
            sysLog.setTitle(AsysLog.value());
        }
        String paramString = "";
        try {
            paramString = new BodyReaderHttpServletRequestWrapper(request).getBodyString(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
        sysLog.setType(LogTypeEnum.NORMAL.getType());
        sysLog.setRemoteAddr(ServletUtil.getClientIP(request));
        sysLog.setRequestUri(URLUtil.getPath(request.getRequestURI()));
        sysLog.setMethod(request.getMethod());
        sysLog.setParams(paramString);
        sysLog.setUserAgent(request.getHeader("user-agent"));
        sysLog.setServiceId(request.getHeader("X-Forwarded-Prefix"));
        sysLog.setCreateUserId(SecurityUtils.getUser().getId());
        sysLog.setCreateUserName(SecurityUtils.getUsername());
        return sysLog;
    }

}
