package com.izaodao.projects.springboot.elasticsearch.rest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.izaodao.projects.springboot.elasticsearch.client.IZaodaoRestHighLevelClient;
import com.izaodao.projects.springboot.elasticsearch.config.dictionary.JTypeMatchEsTypeEnum;
import com.izaodao.projects.springboot.elasticsearch.domain.EsIndexParamters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: Mengqingnan
 * @Description:
 * @Date: 2018/10/19 1:41 PM
 * Copyright (c) 2018, zaodao All Rights Reserved.
 */
@RestController
@RequestMapping(value = "rest/index/")
public class IndexRestController {
    @Autowired
    private IZaodaoRestHighLevelClient zaodaoRestHighLevelClient;

    @GetMapping(value = "creat", produces = "application/json")
    public void creatIndex(@RequestParam(value = "indexSetting") String indexSetting) throws IOException {

        indexSetting = "[".concat(indexSetting).concat("]");

        List<Map<String, Object>> list = JSON.parseObject(indexSetting,
            new TypeReference<List<Map<String, Object>>>() {
            });

        for (Map<String, Object> map : list) {
            String indexTypeSetting = (String) map.get("indexSettingJson");
            String indexMapping = (String) map.get("indexMappingJson");

            EsIndexParamters esIndexParamters = arrangeEsIndexParams(indexTypeSetting, indexMapping);

            zaodaoRestHighLevelClient.creatIndexAsync(esIndexParamters.getIndex(), esIndexParamters.getType(),
                esIndexParamters.getMappings());
        }
    }

    private EsIndexParamters arrangeEsIndexParams(String indexTypeSetting, String indexMapping) {
        if (StringUtils.isEmpty(indexTypeSetting) || StringUtils.isEmpty(indexMapping)) {
            throw new NullPointerException("INDEX OR MAPPING IS EMPTY");
        }

        EsIndexParamters esIndexParamters = JSON.parseObject(indexTypeSetting, EsIndexParamters.class);

        Map<String, Object> mappings = JSON.parseObject(indexMapping, HashMap.class);

        Map<String, Object> jsonMap = new HashMap<>();
        Map<String, Object> mapping = new HashMap<>();
        Map<String, Object> properties = new HashMap<>();


        for (Map.Entry<String, Object> entry : mappings.entrySet()) {
            Map<String, Object> objectMap = new HashMap<>();

            JTypeMatchEsTypeEnum jTypeMatchEsTypeEnum = JTypeMatchEsTypeEnum.parse((String) entry.getValue());

            if (jTypeMatchEsTypeEnum != null) {
                if (!StringUtils.isEmpty(jTypeMatchEsTypeEnum.getEsTypeSecond())) {
                    Map<String, Object> fieldsMap = new HashMap<>();
                    Map<String, Object> keywordMap = new HashMap<>();

                    keywordMap.put("type", jTypeMatchEsTypeEnum.getEsTypeSecond());
                    keywordMap.put("ignore_above", 256);

                    fieldsMap.put("keyword", keywordMap);
                    objectMap.put("fields", fieldsMap);
                }

                objectMap.put("type", jTypeMatchEsTypeEnum.getEsTypeFirst());

                properties.put(entry.getKey(), objectMap);
            }
        }

        mapping.put("properties", properties);
        jsonMap.put(esIndexParamters.getType(), mapping);

        esIndexParamters.setMappings(jsonMap);
        return esIndexParamters;
    }
}
