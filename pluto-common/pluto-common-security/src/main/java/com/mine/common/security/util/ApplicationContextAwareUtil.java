package com.mine.common.security.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 通过名称获取bean
 *
 * @author jax-li
 */
@Component
public class ApplicationContextAwareUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationContextAwareUtil.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(String beanName) throws BeansException {
        if (applicationContext == null) {
            throw new NoSuchBeanDefinitionException("need a singleton bean , but not found : {}", beanName);
        }
        return (T) applicationContext.getBean(beanName);
    }

}