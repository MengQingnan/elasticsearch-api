package com.izaodao.projects.springboot.elasticsearch.client.request;

import com.izaodao.projects.springboot.elasticsearch.config.properties.ZaodaoElasticsearchIndexProperties;
import com.izaodao.projects.springboot.elasticsearch.domain.EsBulkOperParamters;
import com.izaodao.projects.springboot.elasticsearch.domain.EsMultiBulkBase;
import org.elasticsearch.action.ActionRequest;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.MultiGetRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.support.ActiveShardCount;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.common.Strings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @Auther: Mengqingnan
 * @Description: 请求对象基本参数配置
 * @Date: 2018/11/21 8:34 PM
 * Copyright (c) 2018, zaodao All Rights Reserved.
 */
public class ElasticsearchRequestConfig {
    /**
     * 创建索引配置
     */
    private Settings.Builder settingBuilder;

    public ElasticsearchRequestConfig(ZaodaoElasticsearchIndexProperties elasticsearchIndexProperties) {
        settingBuilder = Settings.builder();

        settingBuilder.put("index.refresh_interval", elasticsearchIndexProperties.getRefreshInterval());
        settingBuilder.put("index.number_of_shards", elasticsearchIndexProperties.getNumberOfReplicas());
        settingBuilder.put("index.number_of_replicas", elasticsearchIndexProperties.getNumberOfReplicas());
        settingBuilder.put("index.store.type", elasticsearchIndexProperties.getStoreType());
    }

    protected void configActionRequest(ActionRequest actionRequest) {
        configActionRequest(actionRequest, null);
    }

    protected void configActionRequest(ActionRequest actionRequest, List<? extends EsMultiBulkBase> multiBulkBases) {
        if (actionRequest == null) {
            return;
        }

        if (actionRequest instanceof GetIndexRequest) {
            configRequest((GetIndexRequest) actionRequest);
        } else if (actionRequest instanceof CreateIndexRequest) {
            configRequest((CreateIndexRequest) actionRequest);
        } else if (actionRequest instanceof DeleteIndexRequest) {
            configRequest((DeleteIndexRequest) actionRequest);
        } else if (actionRequest instanceof IndexRequest) {
            configRequest((IndexRequest) actionRequest);
        } else if (actionRequest instanceof DeleteRequest) {
            configRequest((DeleteRequest) actionRequest);
        } else if (actionRequest instanceof UpdateRequest) {
            configRequest((UpdateRequest) actionRequest);
        } else if (actionRequest instanceof GetRequest) {
            configRequest((GetRequest) actionRequest);
        } else if (actionRequest instanceof BulkRequest) {
            configRequest((BulkRequest) actionRequest, (List<EsBulkOperParamters>) multiBulkBases);
        } else if (actionRequest instanceof SearchRequest) {
            configRequest((SearchRequest) actionRequest);
        } else if (actionRequest instanceof MultiGetRequest) {
            configRequest((MultiGetRequest) actionRequest, (List<EsMultiBulkBase>) multiBulkBases);
        }
    }

    private void configRequest(GetIndexRequest getIndexRequest) {
        getIndexRequest.local(Boolean.FALSE);
        getIndexRequest.humanReadable(Boolean.TRUE);
        getIndexRequest.includeDefaults(Boolean.FALSE);
    }

    private void configRequest(CreateIndexRequest createIndexRequest) {
        // Timeout to wait all the nodes to  acknowledge the index creation as a timevalue
        createIndexRequest.timeout(TimeValue.timeValueMinutes(2));
        // Timeout to connect to the master node as a timevalue
        createIndexRequest.masterNodeTimeout(TimeValue.timeValueMinutes(1));
        // The number of active shards copies to wait for before the request returned a response as a ActiveShardCount
        createIndexRequest.waitForActiveShards(ActiveShardCount.DEFAULT);
        // Setting to config the settingBuilder of index
        createIndexRequest.settings(settingBuilder);
    }

    private void configRequest(DeleteIndexRequest deleteIndexRequest) {

    }

    private void configRequest(SearchRequest SearchRequest) {

    }

    private void configRequest(BulkRequest bulkRequest, List<EsBulkOperParamters> bulkOperParamters) {

    }

    private void configRequest(IndexRequest indexRequest) {
        // Timeout is setted to two seconds that wait for primary shard become available as a TimeValue
        indexRequest.timeout(TimeValue.timeValueSeconds(2));
        // Refresh policy -> wait for moments
        indexRequest.setRefreshPolicy(WriteRequest.RefreshPolicy.WAIT_UNTIL);
        // default opType  index
        indexRequest.opType(DocWriteRequest.OpType.INDEX);
    }

    private void configRequest(DeleteRequest deleteRequest) {
        // Timeout is setted to two seconds thaFetchSourceContextt wait for primary shard become available as a TimeValue
        deleteRequest.timeout(TimeValue.timeValueSeconds(2));
        // Refresh policy -> wait for moments
        deleteRequest.setRefreshPolicy(WriteRequest.RefreshPolicy.WAIT_UNTIL);
    }

    private void configRequest(UpdateRequest updateRequest) {
        // Timeout is setted to two seconds that wait for primary shard become available as a TimeValue
        updateRequest.timeout(TimeValue.timeValueSeconds(2));
        // Refresh policy -> wait for moments
        updateRequest.setRefreshPolicy(WriteRequest.RefreshPolicy.WAIT_UNTIL);
        // counts to retry the update operation if conflicts
        updateRequest.retryOnConflict(3);
        // creating the document if it does not already exist
        updateRequest.scriptedUpsert(true);
        // this attrubite  depends on  scriptedUpsert
        updateRequest.docAsUpsert(true);
        // wait for the number of the activce shards
        updateRequest.waitForActiveShards(ActiveShardCount.DEFAULT);
        // set detect noop check
        updateRequest.detectNoop(true);
        // Indicates whether the response should contain the updated _source.
        updateRequest.fetchSource(false);
    }

    private void configRequest(GetRequest getRequest) {
        getRequest.fetchSourceContext(FetchSourceContext.FETCH_SOURCE);
        getRequest.realtime(true);
        getRequest.refresh(false);
        // getRequest.storedFields(); -> don't suggest use it
        // getRequest.preference() -> request is excuted on primary or local, by default. It's random within the nodes.
        // getRequest.routing() ->  use it on specific condition
        // getRequest.version(2); -> use it on specific condition
        // getRequest.versionType(VersionType.EXTERNAL)
    }

    private void configRequest(MultiGetRequest multiGetRequest, List<EsMultiBulkBase> multiOperParamters) {
        multiGetRequest.realtime(true);
        multiGetRequest.refresh(false);

        MultiGetRequest.Item item;

        for (EsMultiBulkBase multiOperParamter : multiOperParamters) {
            item = new MultiGetRequest.Item(multiOperParamter.getIndex(),
                multiOperParamter.getType(), multiOperParamter.getId());

            if (CollectionUtils.isEmpty(multiOperParamter.getIncludeFields())) {
                item.fetchSourceContext(FetchSourceContext.FETCH_SOURCE);
            } else {
                item.fetchSourceContext(new FetchSourceContext(true,
                    (String[])multiOperParamter.getIncludeFields().toArray(), Strings.EMPTY_ARRAY));
            }

            multiGetRequest.add(item.fetchSourceContext(FetchSourceContext.FETCH_SOURCE));
        }

        // getRequest.storedFields(); -> don't suggest use it
        // getRequest.preference() -> request is excuted on primary or local, by default. It's random within the nodes.
        // getRequest.routing() ->  use it on specific condition
        // getRequest.version(2); -> use it on specific condition
        // getRequest.versionType(VersionType.EXTERNAL)
    }
}
