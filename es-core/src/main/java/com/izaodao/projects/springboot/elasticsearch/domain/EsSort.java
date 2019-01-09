package com.izaodao.projects.springboot.elasticsearch.domain;

import org.springframework.util.StringUtils;

import java.io.Serializable;

/**
 * @Auther: Mengqingnan
 * @Description: 排序
 * @Date: 2018-12-25 11:28
 * Copyright (c) 2018, zaodao All Rights Reserved.
 */
public class EsSort implements Serializable {
    private static final long serialVersionUID = -7156542302852703968L;

    private static final String DEFAULT_SORT = "_score";

    private String sort;

    private OrderType orderType;

    private SortMode sortMode;

    public static enum OrderType {
        ASC, DESC;
    }

    public static enum SortMode {
        NULL, MIN, MAX, SUM, AVG, MEDIAN;
    }


    public EsSort() {
        this.sort = DEFAULT_SORT;
        this.orderType = OrderType.DESC;
        this.sortMode = SortMode.NULL;
    }

    public EsSort(String sort) {
        this(sort, OrderType.DESC);
    }

    public EsSort(String sort, OrderType sortType) {
        this(sort, OrderType.DESC, SortMode.NULL);
    }

    public EsSort(String sort, OrderType sortType, SortMode sortMode) {
        this.sort = (StringUtils.isEmpty(sort) ? DEFAULT_SORT : sort);
        this.orderType = (sortType == null ? OrderType.DESC : sortType);
        this.sortMode = (sortMode == null ? SortMode.NULL : sortMode);
    }


    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }

    public SortMode getSortMode() {
        return sortMode;
    }

    public void setSortMode(SortMode sortMode) {
        this.sortMode = sortMode;
    }
}
