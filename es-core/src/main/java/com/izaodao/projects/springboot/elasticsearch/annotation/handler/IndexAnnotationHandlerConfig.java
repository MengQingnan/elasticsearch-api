package com.izaodao.projects.springboot.elasticsearch.annotation.handler;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * @Auther: Mengqingnan
 * @Description:
 * @Date: 2018/10/31 2:36 PM
 * Copyright (c) 2018, zaodao All Rights Reserved.
 */
@Configuration
public class IndexAnnotationHandlerConfig {

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnClass(ApplicationContext.class)
    public SpringContextHolder initSpringContextHolder(){
        return new SpringContextHolder();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnBean(SpringContextHolder.class)
    public IndexAnnotationHandler initIndexAnnotationHandler(SpringContextHolder springContextHolder) throws IOException, ClassNotFoundException {
        IndexAnnotationHandler indexAnnotationHandler = new IndexAnnotationHandler(springContextHolder);

        indexAnnotationHandler.doIndexAnnotationHandler();
        return indexAnnotationHandler;
    }
}
