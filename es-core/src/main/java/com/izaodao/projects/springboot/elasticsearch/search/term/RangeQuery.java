package com.izaodao.projects.springboot.elasticsearch.search.term;

import com.izaodao.projects.springboot.elasticsearch.search.EsQuery;

import java.io.Serializable;

/**
 * @Auther: Mengqingnan
 * @Description: Range 查询
 * @Date: 2019-02-20 19:35
 * Copyright (c) 2019, zaodao All Rights Reserved.
 */
public class RangeQuery extends EsQuery implements Serializable {
    private static final long serialVersionUID = 4490622749746691457L;

    private String field;

    private Object from;

    private Object to;

    private RangeType rangeType;

    public static enum RangeType {
        Number, Date
    }

    public RangeQuery(String field, RangeType rangeType) {
        this(field, rangeType, 1.0d);
    }

    public RangeQuery(String field, RangeType rangeType, double boost) {
        super(boost);
        this.field = field;
        this.rangeType = rangeType;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Object getFrom() {
        return from;
    }

    public void setFrom(Object from) {
        this.from = from;
    }

    public Object getTo() {
        return to;
    }

    public void setTo(Object to) {
        this.to = to;
    }
}
