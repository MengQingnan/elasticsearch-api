package com.izaodao.projects.springboot.elasticsearch.search;

import java.io.Serializable;

/**
 * @Auther: Mengqingnan
 * @Description: 查询主体共有属性
 * @Date: 2019-02-13 20:29
 * Copyright (c) 2019, zaodao All Rights Reserved.
 */
public class EsQuery implements Serializable {
    private static final long serialVersionUID = 4130333019929335716L;

    private double boost;

    public EsQuery(double boost) {
        this.boost = boost;
    }

    public double getBoost() {
        return boost;
    }

    public void setBoost(double boost) {
        this.boost = boost;
    }
}
