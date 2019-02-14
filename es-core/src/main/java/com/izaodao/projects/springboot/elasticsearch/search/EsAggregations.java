package com.izaodao.projects.springboot.elasticsearch.search;

import java.io.Serializable;
import java.util.List;

/**
 * @Auther: Mengqingnan
 * @Description: 聚合
 * @Date: 2018-12-25 11:28
 * Copyright (c) 2018, zaodao All Rights Reserved.
 */
public class EsAggregations implements Serializable {
    private static final long serialVersionUID = -1177787264549713853L;

    /**
     * 聚合名称
     */
    private String name;
    /**
     * 聚合的域
     */
    private String field;
    /**
     * 聚合类型
     */
    private AggType aggType;
    /**
     * 过滤集合(支持多filter)
     */
    private List<Filter> filters;

    public EsAggregations(String name, String field, AggType aggType) {
        this(name, field, aggType, null);
    }

    public EsAggregations(String name, String field, AggType aggType, List<Filter> filters) {
        this.name = name;
        this.field = field;
        this.aggType = aggType;
        this.filters = filters;
    }

    public static enum AggType {
        TERMS, DATE_RANGE, RANGE, AVG, SUM, MIN, MAX
    }

    public static enum FilterType {
        TERM, MATCH, PREFIX
    }

    public static class Filter {
        private String name;
        private FilterType filterType;
        private String field;
        private String value;

        public Filter(String name, FilterType filterType, String field, String value) {
            this.name = name;
            this.filterType = filterType;
            this.field = field;
            this.value = value;
        }

        @Override
        public String toString() {
            return "Filter{" +
                "name='" + name + '\'' +
                ", filterType=" + filterType +
                ", field='" + field + '\'' +
                ", value='" + value + '\'' +
                '}';
        }
    }

    public static Filter obtainFilter(String name, FilterType filterType, String field, String value) {
        return new Filter(name, filterType, field, value);
    }

    @Override
    public String toString() {
        return "EsAggregations{" +
            "name='" + name + '\'' +
            ", field='" + field + '\'' +
            ", aggType=" + aggType +
            ", filters=" + filters +
            '}';
    }

    public String getName() {
        return name;
    }

    public String getField() {
        return field;
    }

    public AggType getAggType() {
        return aggType;
    }

    public List<Filter> getFilters() {
        return filters;
    }
}
