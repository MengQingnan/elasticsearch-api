package com.izaodao.projects.springboot.elasticsearch.interfaces;

import com.izaodao.projects.springboot.elasticsearch.domain.EsOperParamters;
import com.izaodao.projects.springboot.elasticsearch.domain.EsOperResult;

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

    /**
     * indexSync
     *
     * @param index     素银
     * @param type      类型
     * @param paramJson 实际参数
     * @return EsOperResult
     * @Description 同步构建索引
     * @Date 2018/11/8 6:47 PM
     */
    EsOperResult indexSync(String index, String type, String id, String paramJson);

    /**
     * indexSync
     *
     * @param index     素银
     * @param type      类型
     * @param paramJson 实际参数
     * @return EsOperResult
     * @Description 同步构建索引
     * @Date 2018/11/8 6:47 PM
     */
    EsOperResult indexAsync(String index, String type, String id, String paramJson);
}