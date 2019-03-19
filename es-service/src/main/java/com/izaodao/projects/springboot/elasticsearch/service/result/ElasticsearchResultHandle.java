package com.izaodao.projects.springboot.elasticsearch.service.result;

import com.alibaba.fastjson.JSON;
import com.izaodao.projects.springboot.elasticsearch.directory.OperTypeEnum;
import com.izaodao.projects.springboot.elasticsearch.domain.EsMultiBulkOperResult;
import com.izaodao.projects.springboot.elasticsearch.domain.EsOperResult;
import com.izaodao.projects.springboot.elasticsearch.domain.EsSearchResult;
import com.izaodao.projects.springboot.elasticsearch.search.EsAggregations;
import com.izaodao.projects.springboot.elasticsearch.search.EsQuery;
import org.elasticsearch.action.ActionResponse;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetItemResponse;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.ParsedSingleBucketAggregation;
import org.elasticsearch.search.aggregations.bucket.filter.ParsedFilter;
import org.elasticsearch.search.aggregations.bucket.range.ParsedRange;
import org.elasticsearch.search.aggregations.bucket.range.Range;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.metrics.avg.ParsedAvg;
import org.elasticsearch.search.aggregations.metrics.min.ParsedMin;
import org.elasticsearch.search.aggregations.metrics.sum.ParsedSum;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Auther: Mengqingnan
 * @Description: es 返回结果处理
 * @Date: 2018/11/8 8:01 PM
 * Copyright (c) 2018, zaodao All Rights Reserved.
 */
public class ElasticsearchResultHandle {

    protected EsOperResult handleSyncResult(ActionResponse actionResponse) {
        EsOperResult esOperResult = new EsOperResult();

        if (actionResponse == null) {
            esOperResult.setOperFlag(Boolean.FALSE);

            return esOperResult;
        }

        if (actionResponse instanceof IndexResponse) {
            handleIndexResponse((IndexResponse) actionResponse, esOperResult);
        } else if (actionResponse instanceof UpdateResponse) {
            handleUpdateResponse((UpdateResponse) actionResponse, esOperResult);
        } else if (actionResponse instanceof DeleteResponse) {
            handleDeleteResponse((DeleteResponse) actionResponse, esOperResult);
        } else if (actionResponse instanceof GetResponse) {
            handleGetResponse((GetResponse) actionResponse, esOperResult);
        }

        return esOperResult;
    }

    protected EsMultiBulkOperResult handleMultBulkSyncResult(ActionResponse actionResponse) {
        EsMultiBulkOperResult esMultiBulkOperResult = new EsMultiBulkOperResult();

        if (actionResponse == null) {
            esMultiBulkOperResult.setOperFlag(Boolean.FALSE);

            return esMultiBulkOperResult;
        }

        if (actionResponse instanceof MultiGetResponse) {
            handleMultiGetResponse((MultiGetResponse) actionResponse, esMultiBulkOperResult);
        } else if (actionResponse instanceof BulkResponse) {
            handleBulkResponse((BulkResponse) actionResponse, esMultiBulkOperResult);
        }

        return esMultiBulkOperResult;
    }


    protected EsSearchResult handleSearchResult(SearchResponse searchResponse, EsQuery esQuery) {
        EsSearchResult esSearchResult = new EsSearchResult();

        esSearchResult.setOperTypeEnum(OperTypeEnum.SEARCH);
        // 先处理查询出来的数据
        handleSearchHits(searchResponse, esSearchResult);
        // 在处理聚合数据
        handleSearchAggs(esQuery, searchResponse, esSearchResult);

        return esSearchResult;
    }

    private EsOperResult handleIndexResponse(IndexResponse indexResponse, EsOperResult esOperResult) {
        esOperResult.setIndex(indexResponse.getIndex());
        esOperResult.setType(indexResponse.getType());
        esOperResult.setOperTypeEnum(OperTypeEnum.INDEX);
        esOperResult.setResult(indexResponse.getResult().getLowercase());

        return esOperResult;
    }

    private EsOperResult handleUpdateResponse(UpdateResponse updateResponse, EsOperResult esOperResult) {
        esOperResult.setIndex(updateResponse.getIndex());
        esOperResult.setType(updateResponse.getType());
        esOperResult.setOperTypeEnum(OperTypeEnum.UPDATE);
        esOperResult.setResult(updateResponse.getResult().getLowercase());

        return esOperResult;
    }

    private EsOperResult handleDeleteResponse(DeleteResponse deleteResponse, EsOperResult esOperResult) {
        esOperResult.setIndex(deleteResponse.getIndex());
        esOperResult.setType(deleteResponse.getType());
        esOperResult.setOperTypeEnum(OperTypeEnum.DELETE);
        esOperResult.setResult(deleteResponse.getResult().getLowercase());

        return esOperResult;
    }

    private EsOperResult handleGetResponse(GetResponse getResponse, EsOperResult esOperResult) {
        esOperResult.setIndex(getResponse.getIndex());
        esOperResult.setType(getResponse.getType());
        esOperResult.setOperTypeEnum(OperTypeEnum.GET);
        esOperResult.setResult(getResponse.getSourceAsString());

        return esOperResult;
    }

    private EsMultiBulkOperResult handleMultiGetResponse(MultiGetResponse multiGetResponse, EsMultiBulkOperResult esMultiBulkOperResult) {
        MultiGetItemResponse[] multiGetItemResponses = multiGetResponse.getResponses();

        if (multiGetItemResponses != null && multiGetItemResponses.length > 0) {
            List<EsOperResult> esOperResultList = new ArrayList<>();

            for (MultiGetItemResponse multiGetItemResponse : multiGetItemResponses) {
                EsOperResult esOperResult = new EsOperResult();

                esOperResult.setIndex(multiGetItemResponse.getIndex());
                esOperResult.setType(multiGetItemResponse.getType());
                esOperResult.setOperTypeEnum(OperTypeEnum.GET);
                esOperResult.setOperFlag(!multiGetItemResponse.isFailed());
                if (!multiGetItemResponse.isFailed()) {
                    esOperResult.setResult(multiGetItemResponse.getResponse().getSourceAsString());
                }

                esOperResultList.add(esOperResult);
            }

            esMultiBulkOperResult.setEsOperResults(esOperResultList);
        }

        return esMultiBulkOperResult;
    }

    private EsMultiBulkOperResult handleBulkResponse(BulkResponse bulkResponse, EsMultiBulkOperResult esMultiBulkOperResult) {
        BulkItemResponse[] bulkItemResponses = bulkResponse.getItems();

        if (bulkItemResponses != null && bulkItemResponses.length > 0) {
            List<EsOperResult> esOperResultList = new ArrayList<>();

            for (BulkItemResponse bulkItemResponse : bulkItemResponses) {
                EsOperResult esOperResult = new EsOperResult();

                esOperResult.setIndex(bulkItemResponse.getIndex());
                esOperResult.setType(bulkItemResponse.getType());

                if (bulkItemResponse.getOpType() == DocWriteRequest.OpType.UPDATE) {
                    esOperResult.setOperTypeEnum(OperTypeEnum.UPDATE);
                } else if (bulkItemResponse.getOpType() == DocWriteRequest.OpType.DELETE) {
                    esOperResult.setOperTypeEnum(OperTypeEnum.DELETE);
                } else if (bulkItemResponse.getOpType() == DocWriteRequest.OpType.INDEX) {
                    esOperResult.setOperTypeEnum(OperTypeEnum.INDEX);
                }

                esOperResult.setOperFlag(!bulkItemResponse.isFailed());
                if (!bulkItemResponse.isFailed()) {
                    esOperResult.setResult(bulkItemResponse.getResponse().getResult().getLowercase());
                }

                esOperResultList.add(esOperResult);
            }

            esMultiBulkOperResult.setEsOperResults(esOperResultList);
        }
        return esMultiBulkOperResult;
    }

    private void handleSearchHits(SearchResponse searchResponse, EsSearchResult esSearchResult) {
        Assert.notNull(searchResponse, "Search Response Is Null");

        SearchHits searchHits = searchResponse.getHits();
        // 设置总数
        esSearchResult.setTotal(searchHits.getTotalHits());
        // 获取查询到的数据
        SearchHit[] searchHitss = searchHits.getHits();

        List<String> queryResult = new ArrayList<>();

        for (SearchHit searchHit : searchHitss) {
            Map<String, Object> sourceMap = searchHit.getSourceAsMap();

            // 获取高亮
            Map<String, HighlightField> highlightMap = searchHit.getHighlightFields();

            if (!CollectionUtils.isEmpty(highlightMap)) {
                Set<Map.Entry<String, HighlightField>> set = highlightMap.entrySet();

                for (Map.Entry<String, HighlightField> entry : set) {
                    HighlightField field = entry.getValue();

                    StringBuilder stringBuilder = new StringBuilder();
                    for (Text text : field.getFragments()) {
                        stringBuilder.append(text.string()).append(" ");
                    }

                    sourceMap.put(entry.getKey(), stringBuilder.toString());
                }
            }

            queryResult.add(JSON.toJSONString(sourceMap));
        }

        esSearchResult.setSearchResult(queryResult.toString());
    }

    private void handleSearchAggs(EsQuery esQuery, SearchResponse searchResponse, EsSearchResult esSearchResult) {
        // 从结果中获取聚合结果集合
        Aggregations aggregations = searchResponse.getAggregations();
        // 本次统计的类型
        List<EsAggregations> esAggregations = esQuery.getAggregations();
        // 聚合Map
        Map<String, Object> aggMap = new HashMap<>();
        // 遍历本次统计类型
        for (EsAggregations esAggregation : esAggregations) {
            recursionHandleAggResult(aggMap, esAggregation, aggregations);
        }

        System.out.println(JSON.toJSONString(aggMap));
    }

    private void recursionHandleAggResult(Map<String, Object> aggMap, EsAggregations esAggregation, Aggregations aggregations) {
        if (esAggregation.getAggType() == EsAggregations.AggType.TERMS) {
            ParsedStringTerms parsedStringTerms = aggregations.get(esAggregation.getName());

            List<Terms.Bucket> buckets = (List<Terms.Bucket>) parsedStringTerms.getBuckets();

            List<Map<String, Object>> bucketList = new ArrayList<>();
            if (!CollectionUtils.isEmpty(buckets)) {
                for (Terms.Bucket bucket : buckets) {
                    Map<String, Object> subMap = new HashMap<>();

                    subMap.put("key", bucket.getKeyAsString());
                    subMap.put("docCount", bucket.getDocCount());

                    bucketList.add(subMap);

                    Aggregations subAggregations = bucket.getAggregations();

                    if (subAggregations != null && !CollectionUtils.isEmpty(subAggregations.asList())) {
                        // 递归调用
                        recursionHandleAggResult(subMap, esAggregation.getSubEsAggregations(), subAggregations);
                    }
                }
                aggMap.put(parsedStringTerms.getName(), bucketList);
            }
        } else if (esAggregation.getAggType() == EsAggregations.AggType.RANGE ||
            esAggregation.getAggType() == EsAggregations.AggType.DATE_RANGE) {
            ParsedRange parsedRange = aggregations.get(esAggregation.getName());

            List<Range.Bucket> buckets = (List<Range.Bucket>) parsedRange.getBuckets();
            if (!CollectionUtils.isEmpty(buckets)) {
                Range.Bucket bucket = buckets.get(0);

                Map<String, Object> subMap = new HashMap<>();

                subMap.put("docCount", bucket.getDocCount());

                Aggregations subAggregations = bucket.getAggregations();

                if (subAggregations != null && !CollectionUtils.isEmpty(subAggregations.asList())) {
                    // 递归调用
                    recursionHandleAggResult(subMap, esAggregation.getSubEsAggregations(), subAggregations);
                }

                aggMap.put(parsedRange.getName(), subMap);
            }
        } else if (esAggregation.getAggType() == EsAggregations.AggType.Filter) {
            ParsedFilter parsedFilter = aggregations.get(esAggregation.getName());

            if (parsedFilter != null) {
                Map<String, Object> filterMap = parsedSingleBucketAggregation(parsedFilter, esAggregation);

                aggMap.put(parsedFilter.getName(), filterMap);
            }

        } else if (esAggregation.getAggType() == EsAggregations.AggType.SUM) {
            ParsedSum parsedSum = aggregations.get(esAggregation.getName());

            aggMap.put(esAggregation.getName(), parsedSum.getValue());
        } else if (esAggregation.getAggType() == EsAggregations.AggType.AVG) {
            ParsedAvg parsedAvg = aggregations.get(esAggregation.getName());

            aggMap.put(esAggregation.getName(), parsedAvg.getValue());
        } else if (esAggregation.getAggType() == EsAggregations.AggType.SUM) {
            ParsedSum parsedSum = aggregations.get(esAggregation.getName());

            aggMap.put(esAggregation.getName(), parsedSum.getValue());
        } else if (esAggregation.getAggType() == EsAggregations.AggType.MIN) {
            ParsedMin parsedMin = aggregations.get(esAggregation.getName());

            aggMap.put(esAggregation.getName(), parsedMin.getValue());
        }
    }

    public Map<String, Object> parsedSingleBucketAggregation(ParsedSingleBucketAggregation parsedSingleBucketAggregation, EsAggregations esAggregation) {
        Map<String, Object> subMap = new HashMap<>();

        subMap.put("docCount", parsedSingleBucketAggregation.getDocCount());

        Aggregations subAggregations = parsedSingleBucketAggregation.getAggregations();

        if (subAggregations != null && !CollectionUtils.isEmpty(subAggregations.asList())) {
            // 递归调用
            recursionHandleAggResult(subMap, esAggregation.getSubEsAggregations(), subAggregations);
        }

        return subMap;
    }
}
