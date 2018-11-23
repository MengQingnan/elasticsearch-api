package com.izaodao.projects.springboot.elasticsearch.rest.result;

import com.izaodao.projects.springboot.elasticsearch.domain.EsOperResult;
import org.elasticsearch.action.index.IndexResponse;

/**
 * @Auther: Mengqingnan
 * @Description: es 返回结果处理
 * @Date: 2018/11/8 8:01 PM
 * Copyright (c) 2018, zaodao All Rights Reserved.
 */
public class ElasticsearchResultHandle {

    protected EsOperResult handleSyncResult(IndexResponse indexResponse) {
        EsOperResult esOperResult = new EsOperResult();

        if (indexResponse == null) {
            esOperResult.setOperFlag(Boolean.FALSE);
        }

        esOperResult.setIndex(indexResponse.getIndex());
        esOperResult.setType(indexResponse.getType());
        esOperResult.setReesult(indexResponse.getResult().getLowercase());

        return esOperResult;
    }

    protected EsOperResult handleAsyncResult() {
        return new EsOperResult();
    }
}
