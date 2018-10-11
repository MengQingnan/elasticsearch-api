package com.izaodao.projects.springboot.elasticsearch.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.izaodao.projects.springboot.elasticsearch.domain.EsOperParamters;
import com.izaodao.projects.springboot.elasticsearch.interfaces.IElasticsearchService;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
        System.out.println("11111");
        return null;
    }
}
