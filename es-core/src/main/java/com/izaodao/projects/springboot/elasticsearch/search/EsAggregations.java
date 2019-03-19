package com.izaodao.projects.springboot.elasticsearch.search;

import java.io.Serializable;
import java.util.Arrays;
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
     * 过滤的值
     */
    private String filterValue;
    /**
     * 过滤类型
     */
    private FilterType filterType;
    /**
     * 区间聚合必须用，区间开始
     */
    private double from;
    /**
     * 区间聚合必须用，区间截止
     */
    private double to;

    /**
     * 子聚合
     */
    private EsAggregations subEsAggregations;

    public EsAggregations(String name, String field, AggType aggType) {
        this.name = name;
        this.field = field;
        this.aggType = aggType;
    }

    public static enum AggType {
        TERMS, DATE_RANGE, RANGE, AVG, SUM, MIN, MAX, Filter;

        public static List<AggType> supportSubAggs() {
            return Arrays.asList(TERMS, DATE_RANGE, RANGE, Filter);
        }

        @Override
        public String toString() {
            return super.name().toLowerCase();
        }
    }

    public static void main(String[] args) {
        System.out.println(AggType.MAX.toString());
    }

    public static enum FilterType {
        TERM, MATCH, PREFIX
    }


    @Override
    public String toString() {
        return "EsAggregations{" +
            "name='" + name + '\'' +
            ", field='" + field + '\'' +
            ", aggType=" + aggType +
            ", filterValue=" + filterValue +
            ", FilterType=" + filterType +
            ", from=" + from +
            ", to=" + to +
            '}';
    }

    public void addFilterSubEsAggregations(String name, String field, FilterType filterType, String filterValue) {
        addCommonSubEsAggregations(name, field, AggType.Filter);

        this.subEsAggregations.setFilterType(filterType);
        this.subEsAggregations.setFilterValue(filterValue);
    }

    public void addCommonSubEsAggregations(String name, String field, AggType aggType) {
        if (!AggType.supportSubAggs().contains(this.aggType)) {
            throw new IllegalArgumentException(" Number agg don't support SubAgg ");
        } else if (subEsAggregations != null) {
            throw new IllegalArgumentException(" SubEsAggregations exist only one ");
        }
        EsAggregations esAggregations = new EsAggregations(name, field, aggType);

        this.subEsAggregations = esAggregations;
    }

    public void addDateRangeSubEsAggregations(String name, String field, double from, double to) {
        addCommonSubEsAggregations(name, field, AggType.DATE_RANGE);

        this.subEsAggregations.setFrom(from);
        this.subEsAggregations.setTo(to);
    }

    public void addRangeSubEsAggregations(String name, String field, double from, double to) {
        addCommonSubEsAggregations(name, field, AggType.RANGE);

        this.subEsAggregations.setFrom(from);
        this.subEsAggregations.setTo(to);
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

    public String getFilterValue() {
        return filterValue;
    }

    public void setFilterValue(String filterValue) {
        this.filterValue = filterValue;
    }

    public FilterType getFilterType() {
        return filterType;
    }

    public void setFilterType(FilterType filterType) {
        this.filterType = filterType;
    }

    public EsAggregations getSubEsAggregations() {
        return subEsAggregations;
    }

    public double getFrom() {
        return from;
    }

    public void setFrom(double from) {
        this.from = from;
    }

    public double getTo() {
        return to;
    }

    public void setTo(double to) {
        this.to = to;
    }
}
