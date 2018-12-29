package com.izaodao.projects.springboot.elasticsearch.client.request;

import org.elasticsearch.search.builder.SearchSourceBuilder;

/**
 * @Auther: Mengqingnan
 * @Description: 全文检索构建query builder 抽象
 * @Date: 2018-12-17 20:24
 * Copyright (c) 2018, zaodao All Rights Reserved.
 */
public interface IElasticsearchQueryBuilders {
    SearchSourceBuilder queryMatchAll();
}
