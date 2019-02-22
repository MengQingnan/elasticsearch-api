package com.izaodao.projects.springboot.elasticsearch.search.term;

import com.izaodao.projects.springboot.elasticsearch.search.EsQuery;

import java.io.Serializable;

/**
 * @Auther: Mengqingnan
 * @Description: Term 查询
 * @Date: 2019-02-20 19:35
 * Copyright (c) 2019, zaodao All Rights Reserved.
 */
public class TermQuery extends EsQuery implements Serializable {
    private static final long serialVersionUID = -7809428322264622869L;

    private String field;

    private String value;

    public TermQuery(String field, String value){
        this(field, value, 1.0d);
    }

    public TermQuery(String field, String value, double boost) {
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
}
