package com.izaodao.projects.springboot.elasticsearch.domain;

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


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
