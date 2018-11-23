package com.izaodao.projects.springboot.elasticsearch.client.response;

import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.ActionResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Auther: Mengqingnan
 * @Description: response listener
 * @Date: 2018/11/22 7:38 PM
 * Copyright (c) 2018, zaodao All Rights Reserved.
 */
public class ResponseActionListener<T extends ActionResponse> implements ActionListener<ActionResponse> {
    /**
     * logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ResponseActionListener.class);

    /**
     * response handle
     */
    private IElasticsearchClientResponseHandle elasticsearchClientResponseHandle;

    public ResponseActionListener(IElasticsearchClientResponseHandle elasticsearchClientResponseHandle) {
        this.elasticsearchClientResponseHandle = elasticsearchClientResponseHandle;
    }

    @Override
    public void onResponse(ActionResponse actionResponse) {
        elasticsearchClientResponseHandle.handleResponse(actionResponse);
    }

    @Override
    public void onFailure(Exception e) {
        LOGGER.error("async operator exception", e);
    }
}
