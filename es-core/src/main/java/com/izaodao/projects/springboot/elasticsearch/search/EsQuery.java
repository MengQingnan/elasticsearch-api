package com.izaodao.projects.springboot.elasticsearch.search;

import java.io.Serializable;

/**
 * @Auther: Mengqingnan
 * @Description: 查询主体共有属性
 * @Date: 2019-02-13 20:29
 * Copyright (c) 2019, zaodao All Rights Reserved.
 */
public class EsQuery implements Serializable {
    private static final long serialVersionUID = 4130333019929335716L;

    private float boost;

    private EsPaging paging;

    private EsSort sort;

    private EsAggregations aggregations;

    private EsSuggester suggester;

    public EsQuery(float boost) {
        this.boost = boost;
    }

    public float getBoost() {
        return boost;
    }

    public void setBoost(float boost) {
        this.boost = boost;
    }

    public EsPaging getPaging() {
        return paging;
    }

    public void setPaging(EsPaging paging) {
        this.paging = paging;
    }

    public EsSort getSort() {
        return sort;
    }

    public void setSort(EsSort sort) {
        this.sort = sort;
    }

    public EsAggregations getAggregations() {
        return aggregations;
    }

    public void setAggregations(EsAggregations aggregations) {
        this.aggregations = aggregations;
    }

    public EsSuggester getSuggester() {
        return suggester;
    }

    public void setSuggester(EsSuggester suggester) {
        this.suggester = suggester;
    }
}
