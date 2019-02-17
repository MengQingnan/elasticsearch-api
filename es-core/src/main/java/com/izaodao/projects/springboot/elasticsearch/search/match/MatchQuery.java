package com.izaodao.projects.springboot.elasticsearch.search.match;

import com.izaodao.projects.springboot.elasticsearch.search.EsQuery;

import java.io.Serializable;

/**
 * @Auther: Mengqingnan
 * @Description: match 查询
 * @Date: 2019-02-17 16:36
 * Copyright (c) 2019, zaodao All Rights Reserved.
 */
public class MatchQuery extends EsQuery implements Serializable {
    private static final long serialVersionUID = 8527879673474870202L;
    /**
     * 目标域
     */
    private String field;

    /**
     * 传入的参数
     */
    private String value;

    /**
     * 例如  ik 、english、pinyin  等等
     */
    private String analyzer;
    /**
     * and/or
     */
    private String operator;
    /**
     *  value -> 不确定标题(5个词)，minimumShouldMatch 设置为 5，代表 目标域 必须同时包含 不确定标题 这5个词
     */
    private String minimumShouldMatch;
    /**
     * 模糊查询的偏移量
     */
    private String fuzziness;
    /**
     * 忽略前缀的长度
     */
    private String prefixLength;
    /**
     * 最大扫描长度
     */
    private String maxExpansions;

    public MatchQuery(String value, String field) {
        this(value, field, "");
    }

    public MatchQuery(String value, String field, String boost) {
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

    public String getAnalyzer() {
        return analyzer;
    }

    public void setAnalyzer(String analyzer) {
        this.analyzer = analyzer;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getMinimumShouldMatch() {
        return minimumShouldMatch;
    }

    public void setMinimumShouldMatch(String minimumShouldMatch) {
        this.minimumShouldMatch = minimumShouldMatch;
    }

    public String getFuzziness() {
        return fuzziness;
    }

    public void setFuzziness(String fuzziness) {
        this.fuzziness = fuzziness;
    }

    public String getPrefixLength() {
        return prefixLength;
    }

    public void setPrefixLength(String prefixLength) {
        this.prefixLength = prefixLength;
    }

    public String getMaxExpansions() {
        return maxExpansions;
    }

    public void setMaxExpansions(String maxExpansions) {
        this.maxExpansions = maxExpansions;
    }
}
