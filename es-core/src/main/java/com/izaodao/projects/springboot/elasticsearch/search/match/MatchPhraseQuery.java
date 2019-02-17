package com.izaodao.projects.springboot.elasticsearch.search.match;

import com.izaodao.projects.springboot.elasticsearch.search.EsQuery;

import java.io.Serializable;

/**
 * @Auther: Mengqingnan
 * @Description: MatchPhrase 查询
 * @Date: 2019-02-17 16:40
 * Copyright (c) 2019, zaodao All Rights Reserved.
 */
public class MatchPhraseQuery extends EsQuery implements Serializable {
    private static final long serialVersionUID = -9107256055989684551L;

    /**
     * 目标域
     */
    private String field;
    /**
     * 传入的参数
     */
    private String value;
    /**
     * 前后偏移量
     */
    private Integer slop;

    public MatchPhraseQuery(String field, String value){

    }

    public MatchPhraseQuery(String field, String value, double boost){

    }

    public MatchPhraseQuery(String boost, String field, String value, Integer slop) {
        super(boost);
        this.field = field;
        this.value = value;
        this.slop = slop;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getSlop() {
        return slop;
    }

    public void setSlop(Integer slop) {
        this.slop = slop;
    }
}
