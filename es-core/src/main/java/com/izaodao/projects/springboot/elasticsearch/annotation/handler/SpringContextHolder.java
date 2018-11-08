package com.izaodao.projects.springboot.elasticsearch.annotation.handler;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @Auther: Mengqingnan
 * @Description:
 * @Date: 2018/10/31 11:37 AM
 * Copyright (c) 2018, zaodao All Rights Reserved.
 */
public class SpringContextHolder implements ApplicationContextAware {
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }


    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public Object getBean(String beanName) {
        return applicationContext.getBean(beanName);
    }

    public <T> T getBean(String beanName, Class<T> clazz) {
        return applicationContext.getBean(beanName, clazz);
    }
}
