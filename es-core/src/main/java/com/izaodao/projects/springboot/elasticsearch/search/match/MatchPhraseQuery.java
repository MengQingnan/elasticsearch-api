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
    private int slop;
    /**
     * 例如  ik 、english、pinyin  等等
     */
    private String analyzer;

    public MatchPhraseQuery(String field, String value){
        this(field, value,  1.0f);
    }

    public MatchPhraseQuery(String field, String value, float boost) {
        super(boost);
        this.field = field;
        this.value = value;
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

    public int getSlop() {
        return slop;
    }

    public void setSlop(int slop) {
        this.slop = slop;
    }

    public String getAnalyzer() {
        return analyzer;
    }

    public void setAnalyzer(String analyzer) {
        this.analyzer = analyzer;
    }
}
