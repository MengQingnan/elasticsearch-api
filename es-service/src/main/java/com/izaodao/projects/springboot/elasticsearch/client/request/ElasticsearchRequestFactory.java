package com.izaodao.projects.springboot.elasticsearch.client.request;

import com.izaodao.projects.springboot.elasticsearch.config.properties.ZaodaoElasticsearchIndexProperties;
import org.elasticsearch.action.ActionRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Auther: Mengqingnan
 * @Description: request factory impl(此处应该有一个请求对象池， 暂时用new 的形式)
 * @Date: 2018/11/19 2:29 PM
 * Copyright (c) 2018, zaodao All Rights Reserved.
 */
public class ElasticsearchRequestFactory extends ElasticsearchRequestConfig implements IElasticsearchRequestFactory {
    private static final Logger LOGGER = LoggerFactory.getLogger(ElasticsearchRequestFactory.class);

    public ElasticsearchRequestFactory(ZaodaoElasticsearchIndexProperties elasticsearchIndexProperties) {
        super(elasticsearchIndexProperties);
    }

    @Override
    public ActionRequest obtainRequest(RequestEnum requestEnum) {
        ActionRequest actionRequest = handRequest(requestEnum);

        this.configActionRequest(actionRequest);

        return actionRequest;
    }

    @Override
    public <T extends ActionRequest> T obtainRequest(Class<? extends ActionRequest> clazz) {
        try {
            ActionRequest actionRequest =  clazz.newInstance();

            this.configActionRequest(actionRequest);

            return (T)actionRequest;
        } catch (InstantiationException e) {
            LOGGER.error("InstantiationException", e);
        } catch (IllegalAccessException e) {
            LOGGER.error("IllegalAccessException", e);
        }

        return null;
    }


    private ActionRequest handRequest(RequestEnum requestEnum) {
        ActionRequest actionRequest;

        switch (requestEnum){
            case CreateIndex:
                actionRequest = new CreateIndexRequest();
                break;
            case DeleteIndex:
                actionRequest = new DeleteIndexRequest();
                break;
            case GetIndex:
                actionRequest = new GetIndexRequest();
                break;
            case IndexDocument:
                actionRequest = new IndexRequest();
                break;
            case DeleteDocument:
                actionRequest = new DeleteRequest();
                break;
            case UpdateDocument:
                actionRequest = new UpdateRequest();
                break;
            case GetDocument:
                actionRequest = new GetRequest();
                break;
            case BlukDocument:
                actionRequest = new BulkRequest();
                break;
            case SearchDocument:
                actionRequest = new SearchRequest();
                break;
            default:
                actionRequest = null;
        }

        return actionRequest;
    }
}
