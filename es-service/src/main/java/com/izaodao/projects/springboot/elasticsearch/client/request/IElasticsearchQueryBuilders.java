package com.izaodao.projects.springboot.elasticsearch.client.request;

import com.izaodao.projects.springboot.elasticsearch.search.EsQuery;
import org.elasticsearch.search.builder.SearchSourceBuilder;

/**
 * @Auther: Mengqingnan
 * @Description: 全文检索构建query builder 抽象
 * @Date: 2018-12-17 20:24
 * Copyright (c) 2018, zaodao All Rights Reserved.
 */
public interface IElasticsearchQueryBuilders {
    /** 
     * produceSearchSourceBuilder
     * @Description 根据EsQuery 返回对应的查询实体
     * @param esQuery 查询参数
     * @return org.elasticsearch.search.builder.SearchSourceBuilder
     * @Date 2019-03-09 14:51 
     */
    SearchSourceBuilder produceSearchSourceBuilder(EsQuery esQuery);
}
