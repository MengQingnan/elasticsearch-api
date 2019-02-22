package com.izaodao.projects.springboot.elasticsearch.search.match;

import java.io.Serializable;

/**
 * @Auther: Mengqingnan
 * @Description: MatchPhrasePrefixQuery 查询
 * @Date: 2019-02-17 16:40
 * Copyright (c) 2019, zaodao All Rights Reserved.
 */
public class MatchPhrasePrefixQuery extends MatchPhraseQuery implements Serializable {
    private static final long serialVersionUID = -4104976905016180407L;
    /**
     * 最大扫描长度
     */
    private String maxExpansions;

    public MatchPhrasePrefixQuery(String field, String value){
        this(field, value, 1.0d);
    }

    public MatchPhrasePrefixQuery(String field, String value, double boost) {
        super(field, value, boost);
    }

    public String getMaxExpansions() {
        return maxExpansions;
    }

    public void setMaxExpansions(String maxExpansions) {
        this.maxExpansions = maxExpansions;
    }
}
