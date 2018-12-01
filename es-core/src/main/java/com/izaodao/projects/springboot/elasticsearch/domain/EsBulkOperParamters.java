package com.izaodao.projects.springboot.elasticsearch.domain;

import com.izaodao.projects.springboot.elasticsearch.directory.OperTypeEnum;

import java.io.Serializable;

/**
 * @Auther: Mengqingnan
 * @Description: es bluk操作 参数类
 * @Date: 2018/11/29 8:43 PM
 * Copyright (c) 2018, zaodao All Rights Reserved.
 */
public class EsBulkOperParamters extends EsMultiBulkBase implements Serializable {
    /**
     * 序列化
     */
    private static final long serialVersionUID = -4273808679712391783L;

    /**
     * 操作类型(默认类型为get)
     */
    private OperTypeEnum operType = OperTypeEnum.GET;

    /**
     * 请求参数
     */
    private String paramJson;

    public String getParamJson() {
        return paramJson;
    }

    public void setParamJson(String paramJson) {
        this.paramJson = paramJson;
    }

    public OperTypeEnum getOperType() {
        return operType;
    }

    public void setOperType(OperTypeEnum operType) {
        this.operType = operType;
    }
}
