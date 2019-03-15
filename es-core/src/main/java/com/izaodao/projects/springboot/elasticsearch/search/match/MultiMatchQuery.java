package com.izaodao.projects.springboot.elasticsearch.search.match;

import com.izaodao.projects.springboot.elasticsearch.search.EsQuery;

import java.io.Serializable;
import java.util.List;

/**
 * @Auther: Mengqingnan
 * @Description: MultiMatchQuery 查询
 * @Date: 2019-02-20 18:47
 * Copyright (c) 2019, zaodao All Rights Reserved.
 */
public class MultiMatchQuery extends EsQuery implements Serializable {
    private static final long serialVersionUID = -545760579107064184L;

    /**
     * 目标域
     */
    private List<String> fields;
    /**
     * 传入的参数
     */
    private String value;
    /**
     * 0.0(默认选项，不起作用) 设置该属性后，先获取获取评分最大的field 的文档，再将另一个field的评分*tie_breaker 加上 获取评分最大的field的评分，得到最后的得分 是最终得分
     */
    private float tieBreaker;
    /**
     * and/or
     */
    private String operator;
    /**
     * best_fields、most_fields、cross_fields、
     */
    private String type;

    public MultiMatchQuery(List<String> fields, String value) {
        this(fields, value, 1.0f);
    }

    public MultiMatchQuery(List<String> fields, String value, float boost) {
        super(boost);
        this.fields = fields;
        this.value = value;
    }

    public List<String> getFields() {
        return fields;
    }

    public void setFields(List<String> fields) {
        this.fields = fields;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public float getTieBreaker() {
        return tieBreaker;
    }

    public void setTieBreaker(float tieBreaker) {
        this.tieBreaker = tieBreaker;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
