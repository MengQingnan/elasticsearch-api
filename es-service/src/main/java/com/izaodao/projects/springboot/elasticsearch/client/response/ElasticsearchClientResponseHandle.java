package com.izaodao.projects.springboot.elasticsearch.client.response;

import org.elasticsearch.action.ActionResponse;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.get.GetIndexResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Auther: Mengqingnan
 * @Description: es response  handle
 * @Date: 2018/11/13 4:01 PM
 * Copyright (c) 2018, zaodao All Rights Reserved.
 */
public final class ElasticsearchClientResponseHandle implements IElasticsearchClientResponseHandle {
    private static final Logger LOGGER = LoggerFactory.getLogger(ElasticsearchClientResponseHandle.class);

    /**
     * 定义线程池，规定10条线程，专门处理response
     */
    private ExecutorService responseHandleThreadPool = Executors.newFixedThreadPool(10);

    public  void handleResponse(ActionResponse response) {
        if (response instanceof CreateIndexResponse) {
            handleResponse((CreateIndexResponse) response);
        } else if (response instanceof IndexResponse) {
            handleResponse((IndexResponse) response);
        } else if (response instanceof GetIndexResponse) {
            handleResponse((GetResponse) response);
        } else if (response instanceof UpdateResponse) {
            handleResponse((UpdateResponse) response);
        } else if (response instanceof DeleteResponse) {
            handleResponse((DeleteResponse) response);
        }
    }

    /**
     * asyncHandleResponse
     *
     * @param response 请求结果
     * @Description 异步处理请求结果
     * @Date 2018/11/13 7:15 PM
     */
    public void asyncHandleResponse(ActionResponse response) {
        responseHandleThreadPool.submit(new ElasticsearchClientResponseHandleTask(
            this, response));
    }

    private void handleResponse(CreateIndexResponse createIndexResponse) {
        LOGGER.debug("result:"+createIndexResponse.isAcknowledged());
    }

    private void handleResponse(IndexResponse indexResponse) {
        LOGGER.debug("result:"+indexResponse.getResult());
    }

    private void handleResponse(GetResponse getResponse) {
        LOGGER.debug("result:"+getResponse.getSourceAsString());
    }

    private void handleResponse(UpdateResponse updateResponse) {
        LOGGER.debug("result:"+updateResponse.getResult());
    }

    private void handleResponse(DeleteResponse deleteResponse) {
        LOGGER.debug("result:"+deleteResponse.getResult());
    }
}
