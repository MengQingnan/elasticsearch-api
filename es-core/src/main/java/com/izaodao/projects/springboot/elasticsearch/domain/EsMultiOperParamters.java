package com.izaodao.projects.springboot.elasticsearch.domain;

import java.io.Serializable;
import java.util.List;

/**
 * @Auther: Mengqingnan
 * @Description: es multi 操作 参数类
 * @Date: 2018/11/29 8:43 PM
 * Copyright (c) 2018, zaodao All Rights Reserved.
 */
public class EsMultiOperParamters extends EsMultiBulkBase implements Serializable {
    /**
     * 序列化
     */
    private static final long serialVersionUID = 7677423486189017880L;

    /**
     * 复合字段集合
     */
    private List<String> includeFields;

    public List<String> getIncludeFields() {
        return includeFields;
    }

    public void setIncludeFields(List<String> includeFields) {
        this.includeFields = includeFields;
    }
}
