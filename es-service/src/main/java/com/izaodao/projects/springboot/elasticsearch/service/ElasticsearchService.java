package com.izaodao.projects.springboot.elasticsearch.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.izaodao.projects.springboot.elasticsearch.client.IZaodaoRestHighLevelClient;
import com.izaodao.projects.springboot.elasticsearch.domain.EsOperParamters;
import com.izaodao.projects.springboot.elasticsearch.domain.EsOperResult;
import com.izaodao.projects.springboot.elasticsearch.interfaces.IElasticsearchService;
import com.izaodao.projects.springboot.elasticsearch.rest.result.ElasticsearchResultHandle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Auther: Mengqingnan
 * @Description:
 * @Date: 2018/9/29 下午4:06
 * Copyright (c) 2018, zaodao All Rights Reserved.
 */
@Component
@Service(interfaceClass = IElasticsearchService.class, version = "zaodao_mqn", retries = 1, timeout = 200000)
public class ElasticsearchService extends ElasticsearchResultHandle implements IElasticsearchService {

    @Autowired
    private IZaodaoRestHighLevelClient zaodaoRestHighLevelClient;

    @Override
    public String getInfoById(String id, EsOperParamters esOperParamters) {
        return null;
    }

    @Override
    public EsOperResult indexAsync(String index, String type, String id, String paramJson) {
        Map<String, Object> params = JSON.parseObject(paramJson, Map.class);
        zaodaoRestHighLevelClient.indexAsync(index, type, id, params);
        return handleAsyncResult();
    }

    @Override
    public EsOperResult indexSync(String index, String type, String id, String paramJson) {
        Map<String, Object> params = JSON.parseObject(paramJson, Map.class);

        return handleSyncResult(zaodaoRestHighLevelClient.indexSync(index, type, id, params));
    }

}
