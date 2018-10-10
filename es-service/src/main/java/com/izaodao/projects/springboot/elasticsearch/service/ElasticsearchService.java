package com.izaodao.projects.springboot.elasticsearch.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.izaodao.projects.springboot.elasticsearch.domain.EsOperParamters;
import com.izaodao.projects.springboot.elasticsearch.interfaces.IElasticsearchService;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @Auther: Mengqingnan
 * @Description:
 * @Date: 2018/9/29 下午4:06
 * Copyright (c) 2018, zaodao All Rights Reserved.
 */
@Component
@Service(interfaceClass = IElasticsearchService.class, version = "zaodao_mqn", retries = 1, timeout = 200000)
public class ElasticsearchService implements IElasticsearchService {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Override
    public String getInfoById(String id, EsOperParamters esOperParamters) {

        CreateIndexRequest request = new CreateIndexRequest("twitter1111");

        try {
            restHighLevelClient.indices().create(request,RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
