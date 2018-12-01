package com.izaodao.projects.springboot.elasticsearch.service.result;

import com.izaodao.projects.springboot.elasticsearch.directory.OperTypeEnum;
import com.izaodao.projects.springboot.elasticsearch.domain.EsMultiBulkOperResult;
import com.izaodao.projects.springboot.elasticsearch.domain.EsOperResult;
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

import java.util.ArrayList;
import java.util.List;

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
        } else if (actionResponse instanceof BulkResponse) {

        } else if (actionResponse instanceof SearchResponse) {

        } else if (actionResponse instanceof MultiGetResponse) {

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

    protected EsOperResult handleAsyncResult() {
        return new EsOperResult();
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

                if(bulkItemResponse.getOpType() == DocWriteRequest.OpType.UPDATE){
                    esOperResult.setOperTypeEnum(OperTypeEnum.UPDATE);
                } else if(bulkItemResponse.getOpType() == DocWriteRequest.OpType.DELETE){
                    esOperResult.setOperTypeEnum(OperTypeEnum.DELETE);
                } else if(bulkItemResponse.getOpType() == DocWriteRequest.OpType.INDEX){
                    esOperResult.setOperTypeEnum(OperTypeEnum.INDEX);
                }

                esOperResult.setOperFlag(!bulkItemResponse.isFailed());
                if (!bulkItemResponse.isFailed()) {
                    esOperResult.setResult(bulkItemResponse.getResponse().getResult().getLowercase());
                }

                esOperResultList.add(esOperResult);
            }
        }
        return esMultiBulkOperResult;
    }
}
