package com.mine.upmsx.controller.feign;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mine.common.core.util.R;
import com.mine.common.feign.entity.SysUserBaseVO;
import com.mine.common.security.model.MyUser;
import com.mine.common.security.util.SecurityUtils;
import com.mine.upmsx.entity.SysUserBase;
import com.mine.upmsx.service.ISysUserBaseService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/feign")
@AllArgsConstructor
public class FeignSysUserBaseController {

    private ISysUserBaseService sysUserBaseService;

    @GetMapping("/sysUserBase/{userName}")
    public R<SysUserBaseVO> getUserByUserName(@PathVariable("userName") String userName) {
        SysUserBase userBase = sysUserBaseService.getOne(new QueryWrapper<SysUserBase>().lambda().eq(SysUserBase::getUserName, userName));
        SysUserBaseVO sysUserBaseVO = BeanUtil.copyProperties(userBase, SysUserBaseVO.class);
        return new R<>().ok(sysUserBaseVO);
    }

    @GetMapping("/test")
    public R<String> test() {

        MyUser user = SecurityUtils.getUser();
        String str = JSONUtil.toJsonStr(JSONUtil.parse(user));

        return new R<>().ok(str);
    }

}
