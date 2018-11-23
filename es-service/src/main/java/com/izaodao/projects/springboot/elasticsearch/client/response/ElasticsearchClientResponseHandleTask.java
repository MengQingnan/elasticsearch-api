package com.izaodao.projects.springboot.elasticsearch.client.response;

import org.elasticsearch.action.ActionResponse;

/**
 * @Auther: Mengqingnan
 * @Description: es response  handle task
 * @Date: 2018/11/13 6:49 PM
 * Copyright (c) 2018, zaodao All Rights Reserved.
 */
public class ElasticsearchClientResponseHandleTask implements Runnable {
    private IElasticsearchClientResponseHandle elasticsearchClientResponseHandle;
    private ActionResponse response;

    public ElasticsearchClientResponseHandleTask(IElasticsearchClientResponseHandle elasticsearchClientResponseHandle
        , ActionResponse response) {
        this.elasticsearchClientResponseHandle = elasticsearchClientResponseHandle;
        this.response = response;
    }

    @Override
    public void run() {
        elasticsearchClientResponseHandle.handleResponse(response);
    }
}
