package com.izaodao.projects.springboot.elasticsearch.domain;

import java.io.Serializable;

/**
 * @Auther: Mengqingnan
 * @Description: Es 基础参数类
 * @Date: 2018/10/24 7:20 PM
 * Copyright (c) 2018, zaodao All Rights Reserved.
 */
public class EsBase implements Serializable {
    /**
     * 序列化
     */
    private static final long serialVersionUID = 6044097335623567087L;
    /**
     *  索引
     */
    private String index;
    /**
     *  索引
     */
    private String type;

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index.toLowerCase();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
