package com.izaodao.projects.springboot.elasticsearch.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Auther: Mengqingnan
 * @Description: 索引setting 注解
 * @Date: 2018/10/16 8:42 PM
 * Copyright (c) 2018, zaodao All Rights Reserved.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface IndexSettings {
    String index() default "";

    String type() default "";

//    String refreshInterval() default  "1s";
//
//    int numberOfShards() default  5;
//
//    int numberOfReplicas() default  1;
//
//    String  storeType() default  "fs";
}
