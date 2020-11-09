package com.mine.common.mybatis.handler;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.mine.common.security.model.MyUser;
import com.mine.common.security.util.SecurityUtils;
import org.apache.ibatis.reflection.MetaObject;

import java.util.Date;

/**
 * @ClassName MyMetaObjectHandler
 * @Description 自定义公共字段填充处理器
 * @Author Y
 * @Date 2020-01-06 18:21
 */
public class MyMetaObjectHandler implements MetaObjectHandler {

    /**
     * 逻辑删除否 0-未删除,1-已删除
     */
    private static final Byte TABLE_LOGIC_FALSE = 0;

    /**
     * 插入操作自动填充
     *
     * @author:
     * @param:
     * @return:
     * @date:
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        Object createUserName = getFieldValByName("createUserName", metaObject);
        Object createUserId = getFieldValByName("createUserId", metaObject);
        Object createTime = getFieldValByName("createTime", metaObject);
        Object deleted = getFieldValByName("isDeleted", metaObject);
        MyUser user = SecurityUtils.getUser();
        Date currentDate = DateUtil.date();
        if (createUserName == null) {
            String name = ObjectUtil.isNull(user) ? "N/A" : StrUtil.isBlank(user.getName()) ? user.getUsername() : user.getName();
            setFieldValByName("createUserName", name, metaObject);
        }
        if (createUserId == null) {
            Long userId = ObjectUtil.isNull(user) ? null : user.getId();
            setFieldValByName("createUserId", userId, metaObject);
        }
        if (createTime == null) {
            setFieldValByName("createTime", currentDate, metaObject);
        }
        if (deleted == null) {
            setFieldValByName("isDeleted", TABLE_LOGIC_FALSE, metaObject);
        }
    }

    /**
     * 修改操作自动填充
     *
     * @author:
     * @param:
     * @return:
     * @date:
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        Object updateUserName = getFieldValByName("updateUserName", metaObject);
        Object updateUserId = getFieldValByName("updateUserId", metaObject);
        Object updateTime = getFieldValByName("updateTime", metaObject);
        MyUser user = SecurityUtils.getUser();
        DateTime currentDate = DateUtil.date();
        if (updateUserName == null) {
            String name = ObjectUtil.isNull(user) ? "N/A" : StrUtil.isBlank(user.getName()) ? user.getUsername() : user.getName();
            setFieldValByName("updateUserName", name, metaObject);
        }
        if (updateUserId == null) {
            Long userId = ObjectUtil.isNull(user) ? null : user.getId();
            setFieldValByName("updateUserId", userId, metaObject);
        }
        if (updateTime == null) {
            setFieldValByName("updateTime", currentDate, metaObject);
        }
    }

}
