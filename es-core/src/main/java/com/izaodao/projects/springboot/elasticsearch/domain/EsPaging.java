package com.izaodao.projects.springboot.elasticsearch.domain;

import org.springframework.util.StringUtils;

import java.io.Serializable;

/**
 * @Auther: Mengqingnan
 * @Description: 分页信息实体类
 * @Date: 2018-12-24 13:24
 * Copyright (c) 2018, zaodao All Rights Reserved.
 */
public class EsPaging extends EsBase implements Serializable {
    private static final long serialVersionUID = 905243438513815987L;

    private static final int DEFAULT_PAGE_NO = 1;

    private static final int DEFAULT_PAGE_SIZE = 10;

    private static final String DEFAULT_SORT = "_score";

    private int pageNo;

    private int pageSize;

    private String sort;

    private SortType sortType;

    public static enum SortType{
        ASC(1,"asc"),DESC(2, "desc");

        private int code;
        private String label;

        SortType(int code, String label) {
            this.code = code;
            this.label = label;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }
    }

    public EsPaging() {
        this.pageNo = DEFAULT_PAGE_NO;
        this.pageSize = DEFAULT_PAGE_SIZE;
        this.sort = DEFAULT_SORT;
        this.sortType = SortType.DESC;
    }

    public EsPaging(int pageNo) {
        this(pageNo, DEFAULT_PAGE_SIZE);
    }

    public EsPaging(int pageNo, int pageSize) {
        this(pageNo, pageSize, DEFAULT_SORT);
    }

    public EsPaging(int pageNo, int pageSize, String sort) {
        this(pageNo, pageSize, sort, SortType.DESC);
    }

    public EsPaging(int pageNo, int pageSize, String sort, SortType sortType) {
        this.pageNo = (pageNo == 0 ? DEFAULT_PAGE_NO : pageNo);
        this.pageSize = (pageSize == 0 ? DEFAULT_PAGE_SIZE : pageSize);
        this.sort = (StringUtils.isEmpty(sort) ? DEFAULT_SORT : sort);
        this.sortType = sortType;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public SortType getSortType() {
        return sortType;
    }

    public void setSortType(SortType sortType) {
        this.sortType = sortType;
    }
}
