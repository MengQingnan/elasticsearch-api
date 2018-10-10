package com.izaodao.projects.springboot.elasticsearch.interfaces;

import com.izaodao.projects.springboot.elasticsearch.domain.EsOperParamters;

/**
 * @Auther: Mengqingnan
 * @Description: es 操作服务接口
 * @Date: 2018/9/29 下午3:57
 * Copyright (c) 2018, zaodao All Rights Reserved.
 */
public interface IElasticsearchService {
    /**
     * get
     *
     * @param id              es id
     * @param esOperParamters 其它参数类
     * @return java.lang.String
     * @Description
     * @Date 2018/9/29 下午4:00
     */
    String getInfoById(String id, EsOperParamters esOperParamters);
}