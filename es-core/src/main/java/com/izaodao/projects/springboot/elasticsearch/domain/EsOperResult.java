package com.izaodao.projects.springboot.elasticsearch.domain;

import com.izaodao.projects.springboot.elasticsearch.directory.OperTypeEnum;

import java.io.Serializable;

/**
 * @Auther: Mengqingnan
 * @Description: es 基本操作结果类
 * @Date: 2018/9/29 下午3:33
 * Copyright (c) 2018, zaodao All Rights Reserved.
 */
public class EsOperResult extends EsBase implements Serializable {
    /**
     * 序列化
     */
    private static final long serialVersionUID = -4273808679712391783L;
    /**
     * 操作状态
     */
    private Boolean operFlag = Boolean.TRUE;
    /**
     * 操作类型
     */
    private OperTypeEnum operTypeEnum;
    /**
     * 操作结果
     */
    private String result;

    public Boolean getOperFlag() {
        return operFlag;
    }

    public void setOperFlag(Boolean operFlag) {
        this.operFlag = operFlag;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public OperTypeEnum getOperTypeEnum() {
        return operTypeEnum;
    }

    public void setOperTypeEnum(OperTypeEnum operTypeEnum) {
        this.operTypeEnum = operTypeEnum;
    }
}
