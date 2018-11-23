package com.izaodao.projects.springboot.elasticsearch.client.request;

import org.elasticsearch.action.ActionRequest;

/**
 * @Auther: Mengqingnan
 * @Description: IRequestFactory
 * @Date: 2018/11/19 2:05 PM
 * Copyright (c) 2018, zaodao All Rights Reserved.
 */
public interface IElasticsearchRequestFactory {
    /**
     * obtainRequest
     *
     * @param requestEnum paramters
     * @return org.elasticsearch.action.ActionRequest
     * @Description obtain actionrequest by enum
     * @Date 2018/11/21 6:46 PM
     */
    ActionRequest obtainRequest(RequestEnum requestEnum);

    /**
     * obtainRequest
     *
     * @param clazz paramters
     * @return T    abstract result which it extends ActionRequest
     * @Description obtain actionrequest by reflect
     * @Date 2018/11/21 7:41 PM
     */
    <T extends ActionRequest> T obtainRequest(Class<? extends ActionRequest> clazz);
}
