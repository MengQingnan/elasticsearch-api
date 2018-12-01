package com.izaodao.projects.springboot.elasticsearch.domain;

import java.util.List;

/**
 * @Auther: Mengqingnan
 * @Description: 复合操作基础属性类
 * @Date: 2018/11/30 4:19 PM
 * Copyright (c) 2018, zaodao All Rights Reserved.
 */
public class EsMultiBulkBase extends EsBase {
    /**
     * 序列化
     */
    private static final long serialVersionUID = 6934403209864632679L;

    /**
     * 文档id
     */
    private String id;

    /**
     * 复合字段集合
     */
    private List<String> includeFields;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getIncludeFields() {
        return includeFields;
    }

    public void setIncludeFields(List<String> includeFields) {
        this.includeFields = includeFields;
    }
}
