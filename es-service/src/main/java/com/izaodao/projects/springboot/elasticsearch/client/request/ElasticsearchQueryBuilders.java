package com.izaodao.projects.springboot.elasticsearch.client.request;

import com.izaodao.projects.springboot.elasticsearch.search.EsQuery;
import com.izaodao.projects.springboot.elasticsearch.search.bool.BoolQuery;
import com.izaodao.projects.springboot.elasticsearch.search.match.MatchPhrasePrefixQuery;
import com.izaodao.projects.springboot.elasticsearch.search.match.MatchPhraseQuery;
import com.izaodao.projects.springboot.elasticsearch.search.match.MatchQuery;
import com.izaodao.projects.springboot.elasticsearch.search.match.MultiMatchQuery;
import com.izaodao.projects.springboot.elasticsearch.search.term.RangeQuery;
import com.izaodao.projects.springboot.elasticsearch.search.term.TermQuery;
import com.izaodao.projects.springboot.elasticsearch.search.term.TermsQuery;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.AbstractQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.index.query.TermsQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Assert;

/**
 * @Auther: Mengqingnan
 * @Description: 全文检索构建query builder
 * @Date: 2018-12-17 20:23
 * Copyright (c) 2018, zaodao All Rights Reserved.
 */
public class ElasticsearchQueryBuilders implements IElasticsearchQueryBuilders {
    /**
     * timeValue
     */
    private TimeValue timeValue;

    public ElasticsearchQueryBuilders(TimeValue timeValue) {
        this.timeValue = timeValue;
    }

    public SearchSourceBuilder produceSearchSourceBuilder(EsQuery esQuery) {
        Assert.assertNotNull(esQuery);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        // 设置查询超时时间
        searchSourceBuilder.timeout(timeValue);
        // 设置查询类型
        setUpQuery(esQuery, searchSourceBuilder);
        // 设置分页
        // 设置查询聚合
        // 设置高亮
        // 设置查询建议
        return searchSourceBuilder;
    }

    // 设置查询类型
    private void setUpQuery(EsQuery esQuery, SearchSourceBuilder searchSourceBuilder) {
        // 对应获取实际的查询类型
        if (esQuery instanceof TermQuery) {
            setUpQuery((TermQuery) esQuery, searchSourceBuilder);
        } else if (esQuery instanceof TermsQuery) {
            setUpQuery((TermsQuery) esQuery, searchSourceBuilder);
        } else if (esQuery instanceof RangeQuery) {
            setUpQuery((RangeQuery) esQuery, searchSourceBuilder);
        } else if (esQuery instanceof MatchQuery) {
            setUpQuery((MatchQuery) esQuery, searchSourceBuilder);
        } else if (esQuery instanceof MatchPhrasePrefixQuery) {
            setUpQuery((MatchPhrasePrefixQuery) esQuery, searchSourceBuilder);
        } else if (esQuery instanceof MatchPhraseQuery) {
            setUpQuery((MatchPhraseQuery) esQuery, searchSourceBuilder);
        } else if (esQuery instanceof MultiMatchQuery) {
            setUpQuery((MultiMatchQuery) esQuery, searchSourceBuilder);
        } else if (esQuery instanceof BoolQuery) {
            setUpQuery((BoolQuery) esQuery, searchSourceBuilder);
        }
    }

    /**
     * setUpQuery
     *
     * @param termsQuery          termsQuery
     * @param searchSourceBuilder searchSourceBuilder
     * @Description 设置TermsQuery 参数
     * @Date 2019-03-09 16:03
     */
    private void setUpQuery(TermsQuery termsQuery, SearchSourceBuilder searchSourceBuilder) {
        TermsQueryBuilder termsQueryBuilder = QueryBuilders.termsQuery(termsQuery.getField(), termsQuery.getValues());

        setBoost(termsQuery, termsQueryBuilder);

        searchSourceBuilder.query(termsQueryBuilder);
    }

    /**
     * setUpQuery
     *
     * @param rangeQuery          rangeQuery
     * @param searchSourceBuilder searchSourceBuilder
     * @Description 设置RangeQuery 参数
     * @Date 2019-03-09 16:41
     */
    private void setUpQuery(RangeQuery rangeQuery, SearchSourceBuilder searchSourceBuilder) {
        RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery(rangeQuery.getField());

        setBoost(rangeQuery, rangeQueryBuilder);

        if (rangeQuery.getRangeType() == RangeQuery.RangeType.Date) {

        } else {

        }
    }

    private void setUpQuery(TermQuery termQuery, SearchSourceBuilder searchSourceBuilder) {

    }

    private void setUpQuery(MatchQuery matchQuery, SearchSourceBuilder searchSourceBuilder) {

    }

    private void setUpQuery(MatchPhrasePrefixQuery matchPhrasePrefixQuery, SearchSourceBuilder searchSourceBuilder) {

    }

    private void setUpQuery(MultiMatchQuery multiMatchQuery, SearchSourceBuilder searchSourceBuilder) {

    }

    private void setUpQuery(BoolQuery boolQuery, SearchSourceBuilder searchSourceBuilder) {

    }

    private void setUpQuery(MatchPhraseQuery matchPhraseQuery, SearchSourceBuilder searchSourceBuilder) {

    }

    private void setBoost(EsQuery esQuery, AbstractQueryBuilder queryBuilder) {
        if (esQuery.getBoost() != 0.0d) {
            queryBuilder.boost(esQuery.getBoost());
        }
    }


    // 设置查询分页
    // 设置查询聚合
    // 设置查询高亮
    // 设置查询建议

}
