package com.izaodao.projects.springboot.elasticsearch.client.response;

import org.elasticsearch.action.ActionResponse;

/**
 * @Auther: Mengqingnan
 * @Description:
 * @Date: 2018/11/19 2:21 PM
 * Copyright (c) 2018, zaodao All Rights Reserved.
 */
public interface IElasticsearchClientResponseHandle {
    void handleResponse(ActionResponse response);

    void asyncHandleResponse(ActionResponse response);
}
