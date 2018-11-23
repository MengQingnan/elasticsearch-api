package com.izaodao.projects.springboot.elasticsearch.client;

import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateResponse;

import java.io.IOException;
import java.util.Map;

/**
 * @Auther: Mengqingnan
 * @Description:
 * @Date: 2018/11/9 3:18 PM
 * Copyright (c) 2018, zaodao All Rights Reserved.
 */
public interface IZaodaoRestHighLevelClient {
    boolean indexExist(String index) throws IOException;

    boolean creatIndex(String index, String type, Map<String, Object> mapping) throws IOException;

    void creatIndexAsync(String index, String type, Map<String, Object> mapping) throws IOException;

    IndexResponse indexSync(String index, String type, String id, Map<String, Object> params);

    IndexResponse indexSync(String index, String type, String id, String jsonParams);

    void indexAsync(String index, String type, String id, Map<String, Object> params);

    void indexAsync(String index, String type, String id,  String jsonParams);

    UpdateResponse updateSync(String index, String type, String id, Map<String, Object> params);

    UpdateResponse updateSync(String index, String type, String id, String jsonParams);

    void updateAsync(String index, String type, String id, Map<String, Object> params);

    void updateAsync(String index, String type, String id,  String jsonParams);

    DeleteResponse deleteSync(String index, String type, String id);

    void deleteAsync(String index, String type, String id);


}
