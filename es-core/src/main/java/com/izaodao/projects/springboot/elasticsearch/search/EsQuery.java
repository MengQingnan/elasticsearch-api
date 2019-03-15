package com.izaodao.projects.springboot.elasticsearch.search;

import java.io.Serializable;
import java.util.List;

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

    private List<EsSort> sorts;

    private EsHighlighter esHighlighter;

    private List<EsAggregations> aggregations;

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

    public List<EsSort> getSorts() {
        return sorts;
    }

    public void setSorts(List<EsSort> sorts) {
        this.sorts = sorts;
    }

    public List<EsAggregations> getAggregations() {
        return aggregations;
    }

    public void setAggregations(List<EsAggregations> aggregations) {
        this.aggregations = aggregations;
    }

    public EsSuggester getSuggester() {
        return suggester;
    }

    public void setSuggester(EsSuggester suggester) {
        this.suggester = suggester;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public EsHighlighter getEsHighlighter() {
        return esHighlighter;
    }

    public void setEsHighlighter(EsHighlighter esHighlighter) {
        this.esHighlighter = esHighlighter;
    }
}
