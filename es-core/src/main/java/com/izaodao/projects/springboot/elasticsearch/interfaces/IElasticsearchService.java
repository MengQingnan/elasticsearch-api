package com.izaodao.projects.springboot.elasticsearch.interfaces;

import com.izaodao.projects.springboot.elasticsearch.domain.EsBulkOperParamters;
import com.izaodao.projects.springboot.elasticsearch.domain.EsMultiBulkBase;
import com.izaodao.projects.springboot.elasticsearch.domain.EsMultiBulkOperResult;
import com.izaodao.projects.springboot.elasticsearch.domain.EsOperResult;

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
     * @param EsMultiBulkBase 复合查询参数
     * @return com.izaodao.projects.springboot.elasticsearch.domain.EsOperResult
     * @Description 复合查询，参数类需要指定索引、类型、id，includefields 属性为可选， 内容为要查询的列
     * @Date 2018/11/30 3:00 PM
     */
    EsMultiBulkOperResult multiQuery(List<EsMultiBulkBase> EsMultiBulkBase);

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
}