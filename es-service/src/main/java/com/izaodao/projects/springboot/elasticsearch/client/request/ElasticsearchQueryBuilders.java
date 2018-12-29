package com.izaodao.projects.springboot.elasticsearch.client.request;

import org.elasticsearch.search.builder.SearchSourceBuilder;

/**
 * @Auther: Mengqingnan
 * @Description: 全文检索构建query builder
 * @Date: 2018-12-17 20:23
 * Copyright (c) 2018, zaodao All Rights Reserved.
 */
public class ElasticsearchQueryBuilders implements IElasticsearchQueryBuilders{

    @Override
    public SearchSourceBuilder queryMatchAll() {
        return null;
    }
}
