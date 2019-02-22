package com.izaodao.projects.springboot.elasticsearch.search.term;

import com.izaodao.projects.springboot.elasticsearch.search.EsQuery;

import java.io.Serializable;
import java.util.List;

/**
 * @Auther: Mengqingnan
 * @Description: Terms 查询
 * @Date: 2019-02-20 19:35
 * Copyright (c) 2019, zaodao All Rights Reserved.
 */
public class TermsQuery extends EsQuery implements Serializable {
    private static final long serialVersionUID = 5395700810977484452L;

    private String field;

    private List<String> values;

    public TermsQuery(String field, List<String> values){
        this(field, values, 1.0d);
    }

    public TermsQuery(String field, List<String> values, double boost) {
        super(boost);
        this.field = field;
        this.values = values;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }
}
