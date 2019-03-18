package com.izaodao.projects.springboot.elasticsearch.domain;

import java.io.Serializable;

/**
 * @Auther: Mengqingnan
 * @Description: es 基本操作结果类
 * @Date: 2018/9/29 下午3:33
 * Copyright (c) 2018, zaodao All Rights Reserved.
 */
public class EsSearchResult extends EsOperResult implements Serializable {

    private static final long serialVersionUID = -5366297811936843436L;

    /**
     * 查询的总数
     */
    private long total;

    /**
     * 查询结果
     */
    private String searchResult;

    /**
     * 聚合结果
     */
    private String aggResult;

    public String getSearchResult() {
        return searchResult;
    }

    public void setSearchResult(String searchResult) {
        this.searchResult = searchResult;
    }

    public String getAggResult() {
        return aggResult;
    }

    public void setAggResult(String aggResult) {
        this.aggResult = aggResult;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
