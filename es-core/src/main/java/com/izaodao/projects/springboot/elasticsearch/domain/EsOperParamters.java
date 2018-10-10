package com.izaodao.projects.springboot.elasticsearch.domain;

import java.io.Serializable;

/**
 * @Auther: Mengqingnan
 * @Description: es 基本操作参数类
 * @Date: 2018/9/29 下午3:33
 * Copyright (c) 2018, zaodao All Rights Reserved.
 */
public class EsOperParamters implements Serializable {
    /**
     * 序列化
     */
    private static final long serialVersionUID = -4273808679712391783L;
    /**
     *  索引
     */
    private String index;
    /**
     *  类型(在未来的版本中， 将要取消type 属性)
     */
    private String type;
    /**
     *  请求参数
     */
    private String paramtersJson;


    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getParamtersJson() {
        return paramtersJson;
    }

    public void setParamtersJson(String paramtersJson) {
        this.paramtersJson = paramtersJson;
    }
}
