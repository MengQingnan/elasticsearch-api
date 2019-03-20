package com.izaodao.projects.springboot.elasticsearch.search;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
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

    public void paging(EsPaging paging) {
        this.paging = paging;
    }

    public List<EsSort> getSorts() {
        return sorts;
    }

    public void sorts(List<EsSort> sorts) {
        this.sorts = sorts;
    }

    public List<EsAggregations> getAggregations() {
        return aggregations;
    }

    public void aggregations(List<EsAggregations> aggregations) {
        this.aggregations = aggregations;
    }

    public EsSuggester getSuggester() {
        return suggester;
    }

    public void suggester(EsSuggester suggester) {
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

    public EsQuery page(int pageNo, int pageSize) {
        if (this.paging == null) {
            this.paging = new EsPaging(pageNo, pageSize);
        } else {
            this.paging.setPageSize(pageSize);
            this.paging.setPageNo(pageNo);
        }
        return this;
    }

    public EsQuery sorts(String sort, EsSort.OrderType orderType) {
        if (CollectionUtils.isEmpty(this.sorts)) {
            this.sorts = new ArrayList<>();
        }
        this.sorts.add(new EsSort(sort, orderType));
        return this;
    }

    public EsQuery hightlight(boolean requireFieldMatch, String... names) {
        return hightlight("", "", requireFieldMatch, names);
    }

    public EsQuery hightlight(String preTag, String postTag, boolean requireFieldMatch, String... names) {
        if (this.esHighlighter == null) {
            this.esHighlighter = new EsHighlighter();
        }

        if (!StringUtils.isEmpty(preTag)) {
            this.esHighlighter.setPreTags(preTag);
        }

        if (!StringUtils.isEmpty(postTag)) {
            this.esHighlighter.setPostTags(postTag);
        }

        this.esHighlighter.setRequireFieldMatch(requireFieldMatch);

        for (String name : names) {
            esHighlighter.addField(name, EsHighlighter.HighLighterType.Unified);
        }
        return this;
    }
}
