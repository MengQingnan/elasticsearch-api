package com.izaodao.projects.springboot.elasticsearch.client;

import com.izaodao.projects.springboot.elasticsearch.config.properties.ZaodaoElasticsearchIndexProperties;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.IOException;

/**
 * @Auther: Mengqingnan
 * @Description:
 * @Date: 2018/10/11 1:44 PM
 * Copyright (c) 2018, zaodao All Rights Reserved.
 */
@Slf4j
public class ZaodaoRestHighLevelClient extends RestHighLevelClient {
    /**
     * 验证索引是否存在请求对象,ThreadLocal形式存储
     */
    private final ThreadLocal<GetIndexRequest> checkIndexRequestLocal = new ThreadLocal<>();
    /**
     * 创建索引配置
     */
    private Settings.Builder settingBuilder;

    public ZaodaoRestHighLevelClient(ZaodaoElasticsearchIndexProperties elasticsearchIndexProperties,
                                     RestClientBuilder restClientBuilder) {
        super(restClientBuilder);
        initCheckIndex();
        initIndexSettingBuilder(elasticsearchIndexProperties);
    }

    /**
     * initCheckIndex
     *
     * @Description 初始化索引检查配置
     * @Date 2018/10/11 2:45 PM
     */
    private void initCheckIndex() {
        GetIndexRequest checkIndexRequest = new GetIndexRequest();

        checkIndexRequest.local(Boolean.FALSE);
        checkIndexRequest.humanReadable(Boolean.TRUE);
        checkIndexRequest.includeDefaults(Boolean.FALSE);

        checkIndexRequestLocal.set(checkIndexRequest);
    }

    /**
     * initIndexSettingBuilder
     *
     * @param elasticsearchIndexProperties 索引配置文件
     * @Description 初始化 索引配置
     * @Date 2018/10/11 2:25 PM
     */
    private void initIndexSettingBuilder(ZaodaoElasticsearchIndexProperties elasticsearchIndexProperties) {
        settingBuilder = Settings.builder();

        settingBuilder.put("index.refresh_interval", elasticsearchIndexProperties.getRefreshInterval());
        settingBuilder.put("index.number_of_shards", elasticsearchIndexProperties.getNumberOfReplicas());
        settingBuilder.put("index.number_of_replicas", elasticsearchIndexProperties.getNumberOfReplicas());
        settingBuilder.put("index.store.type", elasticsearchIndexProperties.getStoreType());
    }

    /**
     * 同步验证索引是否存在
     *
     * @param index 索引
     * @return boolean
     * @throws IOException
     */
    public boolean indexExist(String index) throws IOException {
        return indices().exists(getCheckIndexRequest(index), RequestOptions.DEFAULT);
    }

    /**
     * 异步验证索引是否存在
     *
     * @param index    索引
     * @param listener 回调函数
     * @throws IOException
     */
    public void indexExistAsync(String index, ActionListener<Boolean> listener) {
        if (listener == null) {
            throw new NullPointerException(" Async listener must exist ");
        }

        indices().existsAsync(getCheckIndexRequest(index), RequestOptions.DEFAULT, listener);
    }

    private GetIndexRequest getCheckIndexRequest(String index) {
        GetIndexRequest getIndexRequest = checkIndexRequestLocal.get();
        getIndexRequest.indices(index);
        return getIndexRequest;
    }

    /**
     * creatIndex
     * @Description 同步创建索引
     * @param index 索引
     * @param mapping 索引mapping
     * @return boolean
     * @Date 2018/10/11 4:43 PM
     */
    public boolean creatIndex(String index, String type, String mapping) throws IOException {
        if (indexExist(index)) {
            CreateIndexRequest createIndexRequest = new CreateIndexRequest();

            createIndexRequest.index(index);
            createIndexRequest.settings(settingBuilder);
            createIndexRequest.mapping(type==null?index:type,mapping,XContentType.JSON);

            // 进行同步创建
            CreateIndexResponse createIndexResponse = indices().create(createIndexRequest, RequestOptions.DEFAULT);

            return  createIndexResponse.isAcknowledged();
        }
        return false;
    }

    public void creatIndexAsync(String index, String type,ActionListener<CreateIndexResponse> listener) {

    }
}
