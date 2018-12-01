package com.izaodao.projects.springboot.elasticsearch.client;

import com.izaodao.projects.springboot.elasticsearch.domain.EsBulkOperParamters;
import com.izaodao.projects.springboot.elasticsearch.domain.EsMultiBulkBase;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateResponse;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @Auther: Mengqingnan
 * @Description: es 服务接口
 * @Date: 2018/11/9 3:18 PM
 * Copyright (c) 2018, zaodao All Rights Reserved.
 */
public interface IZaodaoRestHighLevelClient {
    /**
     * indexExist
     *
     * @param index 索引
     * @return boolean
     * @Description 验证索引是否存在
     * @Date 2018/11/30 2:51 PM
     */
    boolean indexExist(String index) throws IOException;

    /**
     * creatIndex
     *
     * @param index   索引
     * @param type    类型
     * @param mapping 索引结构
     * @Description 同步创建索引
     * @Date 2018/11/30 2:50 PM
     */
    boolean creatIndex(String index, String type, Map<String, Object> mapping) throws IOException;

    /**
     * creatIndexAsync
     *
     * @param index   索引
     * @param type    类型
     * @param mapping 索引结构
     * @Description 异步创建索引
     * @Date 2018/11/30 2:50 PM
     */
    void creatIndexAsync(String index, String type, Map<String, Object> mapping) throws IOException;

    /**
     * queryById
     *
     * @param index 索引
     * @param type  类型
     * @param id    id
     * @return org.elasticsearch.action.get.GetResponse
     * @Description 根据ID 查询信息
     * @Date 2018/11/30 2:46 PM
     */
    GetResponse queryById(String index, String type, String id);

    /**
     * queryById
     *
     * @param index         索引
     * @param type          类型
     * @param id            id
     * @param includeFields 查询的属性集合
     * @return org.elasticsearch.action.get.GetResponse
     * @Description 根据ID 查询信息，需要指定查询的属性
     * @Date 2018/11/30 2:46 PM
     */
    GetResponse queryById(String index, String type, String id, String[] includeFields);

    /**
     * multiQuery
     *
     * @param multiBulkBases 参数集合
     * @return org.elasticsearch.action.get.MultiGetResponse
     * @Description 复合查询，参数类需要指定索引、类型、id，includefields 属性为可选， 内容为要查询的列
     * @Date 2018/11/30 2:45 PM
     */
    MultiGetResponse multiQuery(List<EsMultiBulkBase> multiBulkBases);

    /**
     * bulkSync
     *
     * @param bulkOperParamters 参数集合
     * @return org.elasticsearch.action.get.MultiGetResponse
     * @Description 同步复合操作，同时执行 新增、修改、删除操作
     * @Date 2018/11/30 2:45 PM
     */
    BulkResponse bulkSync(List<EsBulkOperParamters> bulkOperParamters);

    /**
     * bulkAsync
     *
     * @param bulkOperParamters 参数集合
     * @return org.elasticsearch.action.get.MultiGetResponse
     * @Description 异步复合操作，同时执行 新增、修改、删除操作
     * @Date 2018/11/30 2:45 PM
     */
    void bulkAsync(List<EsBulkOperParamters> bulkOperParamters);

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
    IndexResponse indexSync(String index, String type, String id, Map<String, Object> params);

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
    IndexResponse indexSync(String index, String type, String id, String jsonParams);

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
    void indexAsync(String index, String type, String id, Map<String, Object> params);

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
    void indexAsync(String index, String type, String id, String jsonParams);

    /**
     * updateSync
     *
     * @param index  索引
     * @param type   类型
     * @param id     id
     * @param params 参数
     * @return org.elasticsearch.action.update.UpdateResponse
     * @Description 根据Map, 同步修改
     * @Date 2018/11/30 2:30 PM
     */
    UpdateResponse updateSync(String index, String type, String id, Map<String, Object> params);

    /**
     * updateSync
     *
     * @param index      索引
     * @param type       类型
     * @param id         id
     * @param jsonParams 参数
     * @return org.elasticsearch.action.update.UpdateResponse
     * @Description 根据json串，同步修改
     * @Date 2018/11/30 2:30 PM
     */
    UpdateResponse updateSync(String index, String type, String id, String jsonParams);

    /**
     * updateAsync
     *
     * @param index  索引
     * @param type   类型
     * @param id     id
     * @param params 参数
     * @return org.elasticsearch.action.update.UpdateResponse
     * @Description 根据Map, 异步修改
     * @Date 2018/11/30 2:30 PM
     */
    void updateAsync(String index, String type, String id, Map<String, Object> params);

    /**
     * updateAsync
     *
     * @param index      索引
     * @param type       类型
     * @param id         id
     * @param jsonParams 参数
     * @return org.elasticsearch.action.update.UpdateResponse
     * @Description 根据json串，异步修改
     * @Date 2018/11/30 2:30 PM
     */
    void updateAsync(String index, String type, String id, String jsonParams);

    /**
     * deleteSync
     *
     * @param index 索引
     * @param type  类型
     * @param id    id
     * @return org.elasticsearch.action.update.UpdateResponse
     * @Description 同步删除
     * @Date 2018/11/30 2:30 PM
     */
    DeleteResponse deleteSync(String index, String type, String id);

    /**
     * deleteAsync
     *
     * @param index 索引
     * @param type  类型
     * @param id    id
     * @return org.elasticsearch.action.update.UpdateResponse
     * @Description 异步删除
     * @Date 2018/11/30 2:30 PM
     */
    void deleteAsync(String index, String type, String id);


}
