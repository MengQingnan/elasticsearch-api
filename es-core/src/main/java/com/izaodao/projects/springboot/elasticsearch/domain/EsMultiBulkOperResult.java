package com.izaodao.projects.springboot.elasticsearch.domain;

import java.io.Serializable;
import java.util.List;

/**
 * @Auther: Mengqingnan
 * @Description: es 复合操作结果类
 * @Date: 2018/9/29 下午3:33
 * Copyright (c) 2018, zaodao All Rights Reserved.
 */
public final class EsMultiBulkOperResult extends EsOperResult implements Serializable {
    /**
     * 序列化
     */
    private static final long serialVersionUID = -3248926830052174733L;

    /**
     * 复合操作会产生多个结果
     */
    private List<EsOperResult> esOperResults;

    public List<EsOperResult> getEsOperResults() {
        return esOperResults;
    }

    public void setEsOperResults(List<EsOperResult> esOperResults) {
        this.esOperResults = esOperResults;
    }
}
