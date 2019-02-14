package com.izaodao.projects.springboot.elasticsearch.search;

import java.io.Serializable;

/**
 * @Auther: Mengqingnan
 * @Description: 分页信息实体类
 * @Date: 2018-12-24 13:24
 * Copyright (c) 2018, zaodao All Rights Reserved.
 */
public class EsPaging  implements Serializable {
    private static final long serialVersionUID = 905243438513815987L;

    private static final int DEFAULT_PAGE_NO = 1;

    private static final int DEFAULT_PAGE_SIZE = 10;

    private int pageNo;

    private int pageSize;

    public EsPaging() {
        this.pageNo = DEFAULT_PAGE_NO;
        this.pageSize = DEFAULT_PAGE_SIZE;
    }

    public EsPaging(int pageNo) {
        this(pageNo, DEFAULT_PAGE_SIZE);
    }

    public EsPaging(int pageNo, int pageSize) {
        this.pageNo = (pageNo == 0 ? DEFAULT_PAGE_NO : pageNo);
        this.pageSize = (pageSize == 0 ? DEFAULT_PAGE_SIZE : pageSize);
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
}
