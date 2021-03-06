package com.izaodao.projects.springboot.elasticsearch.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.izaodao.projects.springboot.elasticsearch.client.IZaodaoRestHighLevelClient;
import com.izaodao.projects.springboot.elasticsearch.domain.EsBulkOperParamters;
import com.izaodao.projects.springboot.elasticsearch.domain.EsMultiBulkOperResult;
import com.izaodao.projects.springboot.elasticsearch.domain.EsMultiOperParamters;
import com.izaodao.projects.springboot.elasticsearch.domain.EsOperResult;
import com.izaodao.projects.springboot.elasticsearch.domain.EsSearchResult;
import com.izaodao.projects.springboot.elasticsearch.interfaces.IElasticsearchService;
import com.izaodao.projects.springboot.elasticsearch.search.bool.BoolQuery;
import com.izaodao.projects.springboot.elasticsearch.search.match.MatchPhrasePrefixQuery;
import com.izaodao.projects.springboot.elasticsearch.search.match.MatchPhraseQuery;
import com.izaodao.projects.springboot.elasticsearch.search.match.MatchQuery;
import com.izaodao.projects.springboot.elasticsearch.search.match.MultiMatchQuery;
import com.izaodao.projects.springboot.elasticsearch.search.term.RangeQuery;
import com.izaodao.projects.springboot.elasticsearch.search.term.TermQuery;
import com.izaodao.projects.springboot.elasticsearch.search.term.TermsQuery;
import com.izaodao.projects.springboot.elasticsearch.service.result.ElasticsearchResultHandle;
import org.elasticsearch.action.search.SearchResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @Auther: Mengqingnan
 * @Description: ElasticsearchService
 * @Date: 2018/9/29 下午4:06
 * Copyright (c) 2018, zaodao All Rights Reserved.
 */
@Component
@Service(interfaceClass = IElasticsearchService.class, version = "zaodao_mqn", retries = 1, timeout = 200000)
public class ElasticsearchService extends ElasticsearchResultHandle implements IElasticsearchService {

    @Autowired
    private IZaodaoRestHighLevelClient zaodaoRestHighLevelClient;


    @Override
    public EsOperResult queryById(String index, String type, String id) {
        return handleSyncResult(zaodaoRestHighLevelClient.queryById(index, type, id));
    }

    @Override
    public EsOperResult queryById(String index, String type, String id, String[] includeFields) {
        return handleSyncResult(zaodaoRestHighLevelClient.queryById(index, type, id, includeFields));
    }

    @Override
    public EsMultiBulkOperResult multiQuery(List<EsMultiOperParamters> esMultiOperParamters) {
        return handleMultBulkSyncResult(zaodaoRestHighLevelClient.multiQuery(esMultiOperParamters));
    }

    @Override
    public EsMultiBulkOperResult bulkSync(List<EsBulkOperParamters> bulkOperParamters) {
        return handleMultBulkSyncResult(zaodaoRestHighLevelClient.bulkSync(bulkOperParamters));
    }

    @Override
    public void bulkAsync(List<EsBulkOperParamters> bulkOperParamters) {
        zaodaoRestHighLevelClient.bulkAsync(bulkOperParamters);
    }

    @Override
    public void indexAsync(String index, String type, String id, String paramJson) {
        Map<String, Object> params = JSON.parseObject(paramJson, Map.class);
        zaodaoRestHighLevelClient.indexAsync(index, type, id, params);
    }

    @Override
    public EsOperResult indexSync(String index, String type, String id, String paramJson) {
        Map<String, Object> params = JSON.parseObject(paramJson, Map.class);

        return handleSyncResult(zaodaoRestHighLevelClient.indexSync(index, type, id, params));
    }

    @Override
    public EsOperResult updateSync(String index, String type, String id, String paramJson) {
        Map<String, Object> params = JSON.parseObject(paramJson, Map.class);

        return handleSyncResult(zaodaoRestHighLevelClient.updateSync(index, type, id, params));
    }

    @Override
    public void updateAsync(String index, String type, String id, String paramJson) {
        Map<String, Object> params = JSON.parseObject(paramJson, Map.class);

        zaodaoRestHighLevelClient.updateAsync(index, type, id, params);
    }

    @Override
    public EsOperResult deleteSync(String index, String type, String id) {
        return handleSyncResult(zaodaoRestHighLevelClient.deleteSync(index, type, id));
    }

    @Override
    public void deleteAsync(String index, String type, String id) {
        zaodaoRestHighLevelClient.deleteAsync(index, type, id);
    }

    @Override
    public EsSearchResult matchQuery(String index, String type, MatchQuery matchQuery) {
        SearchResponse searchResponse = zaodaoRestHighLevelClient.searchQuery(index, type, matchQuery);
        return handleSearchResult(searchResponse, matchQuery);
    }

    @Override
    public EsSearchResult matchPhraseQuery(String index, String type, MatchPhraseQuery matchPhraseQuery) {
        SearchResponse searchResponse = zaodaoRestHighLevelClient.searchQuery(index, type, matchPhraseQuery);
        return handleSearchResult(searchResponse, matchPhraseQuery);
    }

    @Override
    public EsSearchResult matchPhrasePrefixQuery(String index, String type, MatchPhrasePrefixQuery matchPhrasePrefixQuery) {
        SearchResponse searchResponse = zaodaoRestHighLevelClient.searchQuery(index, type, matchPhrasePrefixQuery);
        return handleSearchResult(searchResponse, matchPhrasePrefixQuery);
    }

    @Override
    public EsSearchResult matchMultiQuery(String index, String type, MultiMatchQuery multiMatchQuery) {
        SearchResponse searchResponse = zaodaoRestHighLevelClient.searchQuery(index, type, multiMatchQuery);
        return handleSearchResult(searchResponse, multiMatchQuery);
    }

    @Override
    public EsSearchResult termQuery(String index, String type, TermQuery termQuery) {
        SearchResponse searchResponse = zaodaoRestHighLevelClient.searchQuery(index, type, termQuery);
        return handleSearchResult(searchResponse, termQuery);
    }

    @Override
    public EsSearchResult termsQuery(String index, String type, TermsQuery termsQuery) {
        SearchResponse searchResponse = zaodaoRestHighLevelClient.searchQuery(index, type, termsQuery);
        return handleSearchResult(searchResponse, termsQuery);
    }

    @Override
    public EsSearchResult rangeQuery(String index, String type, RangeQuery rangeQuery) {
        SearchResponse searchResponse = zaodaoRestHighLevelClient.searchQuery(index, type, rangeQuery);
        return handleSearchResult(searchResponse, rangeQuery);
    }

    @Override
    public EsSearchResult boolQuery(String index, String type, BoolQuery boolQuery) {
        SearchResponse searchResponse = zaodaoRestHighLevelClient.searchQuery(index, type, boolQuery);
        return handleSearchResult(searchResponse, boolQuery);
    }

}
