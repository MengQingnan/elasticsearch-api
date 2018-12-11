package com.izaodao.projects.springboot.elasticsearch.client;

import com.izaodao.projects.springboot.elasticsearch.client.request.IElasticsearchRequestFactory;
import com.izaodao.projects.springboot.elasticsearch.client.response.IElasticsearchClientResponseHandle;
import com.izaodao.projects.springboot.elasticsearch.client.response.ResponseActionListener;
import com.izaodao.projects.springboot.elasticsearch.domain.EsBulkOperParamters;
import com.izaodao.projects.springboot.elasticsearch.domain.EsMultiOperParamters;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetRequest;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.Strings;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @Auther: Mengqingnan
 * @Description: RestHighLevelClient 进行包装
 * @Date: 2018/10/11 1:44 PM
 * Copyright (c) 2018, zaodao All Rights Reserved.
 */
public class ZaodaoRestHighLevelClient extends RestHighLevelClient implements IZaodaoRestHighLevelClient {
    /**
     * logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ZaodaoRestHighLevelClient.class);

    /**
     * request factory
     */
    private IElasticsearchRequestFactory requestFactory;

    /**
     * response handle
     */
    private IElasticsearchClientResponseHandle elasticsearchClientResponseHandle;

    /**
     * action response listener （just a single object）
     */
    private final ActionListener responseActionListener;

    /**
     * 同步、异步标识
     */
    private enum SyncEnum {
        SYNC, ASYNC
    }

    ZaodaoRestHighLevelClient(RestClientBuilder restClientBuilder,
                              IElasticsearchClientResponseHandle elasticsearchClientResponseHandle,
                              IElasticsearchRequestFactory requestFactory) {
        super(restClientBuilder);
        this.elasticsearchClientResponseHandle = elasticsearchClientResponseHandle;
        this.requestFactory = requestFactory;
        responseActionListener = new ResponseActionListener(elasticsearchClientResponseHandle);
    }


    /**
     * 同步验证索引是否存在
     *
     * @param index 索引
     * @return boolean
     * @throws IOException
     */
    public boolean indexExist(String index) throws IOException {
        GetIndexRequest getIndexRequest = requestFactory.obtainRequest(GetIndexRequest.class);
        getIndexRequest.indices(index);

        return indices().exists(getIndexRequest, RequestOptions.DEFAULT);
    }

    /**
     * creatIndex
     *
     * @param index   索引
     * @param mapping 索引mapping
     * @return boolean
     * @Description 同步创建索引
     * @Date 2018/10/11 4:43 PM
     */
    public boolean creatIndex(String index, String type, Map<String, Object> mapping) throws IOException {
        if (!indexExist(index)) {
            CreateIndexRequest createIndexRequest = requestFactory.obtainRequest(CreateIndexRequest.class);

            createIndexRequest.index(index);
            createIndexRequest.mapping(type == null ? index : type, mapping);

            // 进行同步创建
            CreateIndexResponse createIndexResponse = indices().create(createIndexRequest, RequestOptions.DEFAULT);
            // 异步处理请求结果
            elasticsearchClientResponseHandle.asyncHandleResponse(createIndexResponse);

            return createIndexResponse.isAcknowledged();
        }
        return false;
    }

    /**
     * creatIndexAsync
     *
     * @param index   索引
     * @param type    类型
     * @param mapping 字段
     * @return void
     * @Description 异步创建索引
     * @Date 2018/10/16 2:05 PM
     */
    public void creatIndexAsync(String index, String type, Map<String, Object> mapping) throws IOException {
        if (!indexExist(index)) {
            CreateIndexRequest createIndexRequest = requestFactory.obtainRequest(CreateIndexRequest.class);

            createIndexRequest.index(index);
            createIndexRequest.mapping(type == null ? index : type, mapping);

            // 进行同步创建
            indices().createAsync(createIndexRequest, RequestOptions.DEFAULT, responseActionListener);
        }
    }

    @Override
    public GetResponse queryById(String index, String type, String id) {
        return queryById(index, type, id, new String[0]);
    }

    @Override
    public GetResponse queryById(String index, String type, String id, String[] includeFields) {
        GetRequest getRequest = requestFactory.obtainRequest(GetRequest.class);

        getRequest.index(index);
        getRequest.type(type);
        getRequest.id(id);

        if (includeFields.length > 0) {
            FetchSourceContext fetchSourceContext =
                new FetchSourceContext(true, includeFields, Strings.EMPTY_ARRAY);

            getRequest.fetchSourceContext(fetchSourceContext);
        }

        GetResponse getResponse = null;

        try {
            getResponse = get(getRequest, RequestOptions.DEFAULT);

            elasticsearchClientResponseHandle.asyncHandleResponse(getResponse);
        } catch (IOException e) {
            LOGGER.error(" delete data exception", e);
        }

        return getResponse;
    }

    @Override
    public MultiGetResponse multiQuery(List<EsMultiOperParamters> multiOperParamters) {
        MultiGetRequest multiGetRequest = requestFactory.obtainRequest(MultiGetRequest.class, multiOperParamters);

        MultiGetResponse multiGetItemResponses = null;

        try {
            multiGetItemResponses = mget(multiGetRequest, RequestOptions.DEFAULT);

            elasticsearchClientResponseHandle.asyncHandleResponse(multiGetItemResponses);

        } catch (IOException e) {
            LOGGER.error(" multQuery data exception", e);
        }

        return multiGetItemResponses;
    }

    @Override
    public BulkResponse bulkSync(List<EsBulkOperParamters> bulkOperParamters) {
        BulkRequest bulkRequest = requestFactory.obtainRequest(BulkRequest.class, bulkOperParamters);

        BulkResponse bulkResponse = null;

        try {
            bulkResponse = bulk(bulkRequest, RequestOptions.DEFAULT);

            elasticsearchClientResponseHandle.asyncHandleResponse(bulkResponse);
        } catch (IOException e) {
            LOGGER.error(" bulk data exception", e);
        }

        return bulkResponse;
    }

    @Override
    public void bulkAsync(List<EsBulkOperParamters> bulkOperParamters) {
        BulkRequest bulkRequest = requestFactory.obtainRequest(BulkRequest.class, bulkOperParamters);

        bulkAsync(bulkRequest, RequestOptions.DEFAULT, responseActionListener);
    }


    /**
     * indexSync
     *
     * @param index  索引
     * @param type   类型
     * @param id     id
     * @param params 参数内容
     * @return org.elasticsearch.action.index.IndexResponse
     * @Description 通过Map同步构建索引
     * @Date 2018/11/8 7:46 PM
     */
    public IndexResponse indexSync(String index, String type, String id, Map<String, Object> params) {
        return indexCommon(index, type, id, params, SyncEnum.SYNC);
    }

    /**
     * indexSync
     *
     * @param index      索引
     * @param type       类型
     * @param id         id
     * @param jsonParams 参数内容
     * @return org.elasticsearch.action.index.IndexResponse
     * @Description 通过json String同步构建索引
     * @Date 2018/11/8 7:46 PM
     */
    public IndexResponse indexSync(String index, String type, String id, String jsonParams) {
        return indexCommon(index, type, id, jsonParams, SyncEnum.SYNC);
    }

    /**
     * indexAsync
     *
     * @param index  索引
     * @param type   类型
     * @param id     id
     * @param params 参数内容
     * @return void
     * @Description 通过Map 异步构建索引
     * @Date 2018/11/9 5:00 PM
     */
    public void indexAsync(String index, String type, String id, Map<String, Object> params) {
        indexCommon(index, type, id, params, SyncEnum.ASYNC);
    }

    /**
     * indexAsync
     *
     * @param index      索引
     * @param type       类型
     * @param id         id
     * @param jsonParams 参数内容
     * @return void
     * @Description 通过json String 异步构建索引
     * @Date 2018/11/9 5:00 PM
     */
    public void indexAsync(String index, String type, String id, String jsonParams) {
        indexCommon(index, type, id, jsonParams, SyncEnum.ASYNC);
    }

    @Override
    public UpdateResponse updateSync(String index, String type, String id, Map<String, Object> params) {
        return updateCommon(index, type, id, params, SyncEnum.SYNC);
    }

    @Override
    public UpdateResponse updateSync(String index, String type, String id, String jsonParams) {
        return updateCommon(index, type, id, jsonParams, SyncEnum.SYNC);
    }

    @Override
    public void updateAsync(String index, String type, String id, Map<String, Object> params) {
        updateCommon(index, type, id, params, SyncEnum.ASYNC);
    }

    @Override
    public void updateAsync(String index, String type, String id, String jsonParams) {
        updateCommon(index, type, id, jsonParams, SyncEnum.ASYNC);
    }

    @Override
    public DeleteResponse deleteSync(String index, String type, String id) {
        return deleteCommon(index, type, id, SyncEnum.SYNC);
    }

    @Override
    public void deleteAsync(String index, String type, String id) {
        deleteCommon(index, type, id, SyncEnum.ASYNC);
    }

    private IndexResponse indexCommon(String index, String type, String id, Object params, SyncEnum syncEnum) {
        IndexRequest indexRequest = requestFactory.obtainRequest(IndexRequest.class);

        // set index
        indexRequest.index(index);
        // set type
        indexRequest.type(StringUtils.isEmpty(type) ? index : type);
        // set id
        indexRequest.id(id);

        if (params instanceof Map) {
            indexRequest.source((Map) params);
        } else if (params instanceof String) {
            indexRequest.source((String) params);
        }

        if (syncEnum == SyncEnum.SYNC) {
            IndexResponse indexResponse = null;
            try {
                indexResponse = index(indexRequest, RequestOptions.DEFAULT);

                elasticsearchClientResponseHandle.asyncHandleResponse(indexResponse);
            } catch (IOException e) {
                LOGGER.error(" index data exception", e);
            }

            return indexResponse;
        } else {
            indexAsync(indexRequest, RequestOptions.DEFAULT, responseActionListener);

            return null;
        }
    }

    private DeleteResponse deleteCommon(String index, String type, String id, SyncEnum syncEnum) {
        DeleteRequest deleteRequest = requestFactory.obtainRequest(DeleteRequest.class);

        // set index
        deleteRequest.index(index);
        // set type
        deleteRequest.type(StringUtils.isEmpty(type) ? index : type);
        // set id
        deleteRequest.id(id);

        if (syncEnum == SyncEnum.SYNC) {
            DeleteResponse deleteResponse = null;
            try {
                deleteResponse = delete(deleteRequest, RequestOptions.DEFAULT);

                elasticsearchClientResponseHandle.asyncHandleResponse(deleteResponse);
            } catch (IOException e) {
                LOGGER.error(" delete data exception", e);
            }

            return deleteResponse;
        } else {
            deleteAsync(deleteRequest, RequestOptions.DEFAULT, responseActionListener);

            return null;
        }
    }

    private UpdateResponse updateCommon(String index, String type, String id, Object params, SyncEnum syncEnum) {
        UpdateRequest updateRequest = requestFactory.obtainRequest(UpdateRequest.class);

        // set index
        updateRequest.index(index);
        // set type
        updateRequest.type(StringUtils.isEmpty(type) ? index : type);
        // set id
        updateRequest.id(id);

        if (params instanceof Map) {
            updateRequest.doc((Map) params);
        } else if (params instanceof String) {
            updateRequest.doc((String) params);
        }

        if (syncEnum == SyncEnum.SYNC) {
            UpdateResponse updateResponse = null;
            try {
                updateResponse = update(updateRequest, RequestOptions.DEFAULT);

                elasticsearchClientResponseHandle.asyncHandleResponse(updateResponse);
            } catch (IOException e) {
                LOGGER.error(" update data exception", e);
            }

            return updateResponse;
        } else {
            updateAsync(updateRequest, RequestOptions.DEFAULT, responseActionListener);

            return null;
        }
    }
}
