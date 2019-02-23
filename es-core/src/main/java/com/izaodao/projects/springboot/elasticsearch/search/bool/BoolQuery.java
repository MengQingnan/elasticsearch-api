package com.izaodao.projects.springboot.elasticsearch.search.bool;

import com.izaodao.projects.springboot.elasticsearch.search.EsQuery;

import java.io.Serializable;
import java.util.List;

/**
 * @Auther: Mengqingnan
 * @Description: bool 类型查询
 * @Date: 2019-02-23 15:18
 * Copyright (c) 2019, zaodao All Rights Reserved.
 */
public class BoolQuery extends EsQuery implements Serializable {
    private static final long serialVersionUID = -7207299747760734589L;

    private List<EsQuery> shoulds;

    private List<EsQuery> musts;

    private List<EsQuery> filters;

    private List<EsQuery> mustNots;

    private int minimumShouldMatch;

    public BoolQuery (double boost){
        this(1, 1.0d);
    }

    public BoolQuery (int minimumShouldMatch, double boost){
        super(boost);
        this.minimumShouldMatch = 1;
    }

    public List<EsQuery> getShoulds() {
        return shoulds;
    }

    public void setShoulds(List<EsQuery> shoulds) {
        this.shoulds = shoulds;
    }

    public List<EsQuery> getMusts() {
        return musts;
    }

    public void setMusts(List<EsQuery> musts) {
        this.musts = musts;
    }

    public List<EsQuery> getFilters() {
        return filters;
    }

    public void setFilters(List<EsQuery> filters) {
        this.filters = filters;
    }

    public List<EsQuery> getMustNots() {
        return mustNots;
    }

    public void setMustNots(List<EsQuery> mustNots) {
        this.mustNots = mustNots;
    }

    public int getMinimumShouldMatch() {
        return minimumShouldMatch;
    }

    public void setMinimumShouldMatch(int minimumShouldMatch) {
        this.minimumShouldMatch = minimumShouldMatch;
    }
}
