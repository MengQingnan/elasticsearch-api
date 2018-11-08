package com.izaodao.projects.springboot.elasticsearch.domain;

import java.io.Serializable;
import java.util.Map;

/**
 * @Auther: Mengqingnan
 * @Description: es 索引初始化操作参数类
 * @Date: 2018/9/29 下午3:33
 * Copyright (c) 2018, zaodao All Rights Reserved.
 */
public class EsIndexParamters extends EsBase implements Serializable {
    /**
     * 序列化
     */
    private static final long serialVersionUID = -4273808679712391783L;

    /**
     *  字段mapping
     */
    private Map<String, Object> mappings;

    public Map<String, Object> getMappings() {
        return mappings;
    }

    public void setMappings(Map<String, Object> mappings) {
        this.mappings = mappings;
    }
}
