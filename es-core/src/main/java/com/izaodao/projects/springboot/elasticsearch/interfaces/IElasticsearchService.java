package com.izaodao.projects.springboot.elasticsearch.interfaces;

import com.izaodao.projects.springboot.elasticsearch.domain.EsBulkOperParamters;
import com.izaodao.projects.springboot.elasticsearch.domain.EsMultiBulkOperResult;
import com.izaodao.projects.springboot.elasticsearch.domain.EsMultiOperParamters;
import com.izaodao.projects.springboot.elasticsearch.domain.EsOperResult;
import com.izaodao.projects.springboot.elasticsearch.search.bool.BoolQuery;
import com.izaodao.projects.springboot.elasticsearch.search.match.MatchPhrasePrefixQuery;
import com.izaodao.projects.springboot.elasticsearch.search.match.MatchPhraseQuery;
import com.izaodao.projects.springboot.elasticsearch.search.match.MatchQuery;
import com.izaodao.projects.springboot.elasticsearch.search.match.MultiMatchQuery;
import com.izaodao.projects.springboot.elasticsearch.search.term.RangeQuery;
import com.izaodao.projects.springboot.elasticsearch.search.term.TermQuery;
import com.izaodao.projects.springboot.elasticsearch.search.term.TermsQuery;

import java.util.List;

/**
 * @Auther: Mengqingnan
 * @Description: es 操作服务接口
 * @Date: 2018/9/29 下午3:57
 * Copyright (c) 2018, zaodao All Rights Reserved.
 */
public interface IElasticsearchService {
    /**
     * queryById
     *
     * @param index 索引
     * @param type  类型
     * @param id    id
     * @return java.lang.String
     * @Description
     * @Date 2018/9/29 下午4:00
     */
    EsOperResult queryById(String index, String type, String id);

    /**
     * queryById
     *
     * @param index 索引
     * @param type  类型
     * @param id    id
     * @return java.lang.String
     * @Description
     * @Date 2018/9/29 下午4:00
     */
    EsOperResult queryById(String index, String type, String id, String[] includeFields);

    /**
     * multiQuery
     *
     * @param esMultiOperParamters 复合查询参数
     * @return com.izaodao.projects.springboot.elasticsearch.domain.EsOperResult
     * @Description 复合查询，参数类需要指定索引、类型、id，includefields 属性为可选， 内容为要查询的列
     * @Date 2018/11/30 3:00 PM
     */
    EsMultiBulkOperResult multiQuery(List<EsMultiOperParamters> esMultiOperParamters);

    /**
     * bulkSync
     *
     * @param bulkOperParamters 参数集合
     * @return org.elasticsearch.action.get.MultiGetResponse
     * @Description 同步复合操作，同时执行 新增、修改、删除操作
     * @Date 2018/11/30 2:45 PM
     */
    EsMultiBulkOperResult bulkSync(List<EsBulkOperParamters> bulkOperParamters);

    /**
     * bulkAsync
     *
     * @param bulkOperParamters 参数集合
     * @return org.elasticsearch.action.get.MultiGetResponse
     * @Description 异步复合操作，同时执行 新增、修改、删除操作
     * @Date 2018/11/30 2:45 PM
     */
    EsOperResult bulkAsync(List<EsBulkOperParamters> bulkOperParamters);


    /**
     * indexSync
     *
     * @param index     索引
     * @param type      类型
     * @param paramJson 实际参数
     * @return EsOperResult
     * @Description 同步构建索引
     * @Date 2018/11/8 6:47 PM
     */
    EsOperResult indexSync(String index, String type, String id, String paramJson);

    /**
     * indexAsync
     *
     * @param index     索引
     * @param type      类型
     * @param paramJson 实际参数
     * @return EsOperResult
     * @Description 同步构建索引
     * @Date 2018/11/8 6:47 PM
     */
    EsOperResult indexAsync(String index, String type, String id, String paramJson);

    /**
     * updateSync
     *
     * @param index     索引
     * @param type      类型
     * @param paramJson 实际参数
     * @return EsOperResult
     * @Description 同步修改索引属性，1.修改的目标对象如果不存在，则进行创建 2.修改的属性如果不存在，则默认新建属性
     * @Date 2018/11/8 6:47 PM
     */
    EsOperResult updateSync(String index, String type, String id, String paramJson);

    /**
     * updateAsync
     *
     * @param index     索引
     * @param type      类型
     * @param paramJson 实际参数
     * @return EsOperResult
     * @Description 同步修改索引属性，1.修改的目标对象如果不存在，则进行创建 2.修改的属性如果不存在，则默认新建属性
     * @Date 2018/11/8 6:47 PM
     */
    EsOperResult updateAsync(String index, String type, String id, String paramJson);

    /**
     * deleteSync
     *
     * @param index 索引
     * @param type  类型
     * @return EsOperResult
     * @Description 删除索引文档
     * @Date 2018/11/8 6:47 PM
     */
    EsOperResult deleteSync(String index, String type, String id);

    /**
     * deleteAsync
     *
     * @param index 索引
     * @param type  类型
     * @return EsOperResult
     * @Description 删除索引文档
     * @Date 2018/11/8 6:47 PM
     */
    EsOperResult deleteAsync(String index, String type, String id);
    
    /** 
     * matchQuery
     * @Description match 匹配查询
     * @param index 索引
     * @param type  类型
     * @param matchQuery 查询参数
     * @return com.izaodao.projects.springboot.elasticsearch.domain.EsOperResult
     * @Date 2019-02-23 16:28 
     */
    EsOperResult matchQuery(String index, String type, MatchQuery matchQuery);

    /**
     * matchPhraseQuery
     * @Description match 短语匹配查询
     * @param index 索引
     * @param type  类型
     * @param matchPhraseQuery 查询参数
     * @return com.izaodao.projects.springboot.elasticsearch.domain.EsOperResult
     * @Date 2019-02-23 16:29
     */
    EsOperResult matchPhraseQuery(String index, String type, MatchPhraseQuery matchPhraseQuery);
    
    /** 
     * matchPhrasePrefixQuery
     * @Description match 短语前缀匹配查询
     * @param index 索引
     * @param type  类型
     * @param matchPhrasePrefixQuery 查询参数
     * @return com.izaodao.projects.springboot.elasticsearch.domain.EsOperResult
     * @Date 2019-02-23 16:30 
     */
    EsOperResult matchPhrasePrefixQuery(String index, String type, MatchPhrasePrefixQuery matchPhrasePrefixQuery);

    /**
     * matchMultiQuery
     * @Description match 多字段复合查询
     * @param index 索引
     * @param type  类型
     * @param multiMatchQuery 查询参数
     * @return com.izaodao.projects.springboot.elasticsearch.domain.EsOperResult
     * @Date 2019-02-23 16:31
     */
    EsOperResult matchMultiQuery(String index, String type, MultiMatchQuery multiMatchQuery);
    
    /** 
     * termQuery
     * @Description term 精确查询
     * @param index 索引
     * @param type  类型
     * @param termQuery 查询参数
     * @return com.izaodao.projects.springboot.elasticsearch.domain.EsOperResult
     * @Date 2019-02-23 16:32 
     */
    EsOperResult termQuery(String index, String type, TermQuery termQuery);
    
    /** 
     * termsQuery
     * @Description term 多值查询
     * @param index 索引
     * @param type  类型
     * @param termsQuery 查询参数
     * @return com.izaodao.projects.springboot.elasticsearch.domain.EsOperResult
     * @Date 2019-02-23 16:32 
     */
    EsOperResult termsQuery(String index, String type, TermsQuery termsQuery);
    
    /** 
     * rangeQuery
     * @Description 范围查询
     * @param index 索引
     * @param type  类型
     * @param rangeQuery  查询参数
     * @return com.izaodao.projects.springboot.elasticsearch.domain.EsOperResult
     * @Date 2019-02-23 16:32 
     */
    EsOperResult rangeQuery(String index, String type, RangeQuery rangeQuery);
    
    /** 
     * boolQuery
     * @Description bool 查询
     * @param index 索引
     * @param type  类型
     * @param boolQuery 查询参数
     * @return com.izaodao.projects.springboot.elasticsearch.domain.EsOperResult
     * @Date 2019-02-23 16:32 
     */
    EsOperResult boolQuery(String index, String type, BoolQuery boolQuery);
}