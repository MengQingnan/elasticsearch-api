package com.izaodao.projects.springboot.elasticsearch.client.request;

import com.izaodao.projects.springboot.elasticsearch.search.EsAggregations;
import com.izaodao.projects.springboot.elasticsearch.search.EsHighlighter;
import com.izaodao.projects.springboot.elasticsearch.search.EsPaging;
import com.izaodao.projects.springboot.elasticsearch.search.EsQuery;
import com.izaodao.projects.springboot.elasticsearch.search.EsSort;
import com.izaodao.projects.springboot.elasticsearch.search.bool.BoolQuery;
import com.izaodao.projects.springboot.elasticsearch.search.match.MatchPhrasePrefixQuery;
import com.izaodao.projects.springboot.elasticsearch.search.match.MatchPhraseQuery;
import com.izaodao.projects.springboot.elasticsearch.search.match.MatchQuery;
import com.izaodao.projects.springboot.elasticsearch.search.match.MultiMatchQuery;
import com.izaodao.projects.springboot.elasticsearch.search.term.RangeQuery;
import com.izaodao.projects.springboot.elasticsearch.search.term.TermQuery;
import com.izaodao.projects.springboot.elasticsearch.search.term.TermsQuery;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchPhrasePrefixQueryBuilder;
import org.elasticsearch.index.query.MatchPhraseQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.index.query.TermsQueryBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.filter.FiltersAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.range.DateRangeAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.range.RangeAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.avg.AvgAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.max.MaxAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.min.MinAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.sum.SumAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortMode;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

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

    /**
     * multiMatchTypes
     */
    private List<String> multiMatchTypes;

    {
        multiMatchTypes = new ArrayList<>();

        for (MultiMatchQueryBuilder.Type type : MultiMatchQueryBuilder.Type.values()) {
            multiMatchTypes.add(type.parseField().toString());
        }
    }

    public ElasticsearchQueryBuilders(TimeValue timeValue) {
        this.timeValue = timeValue;
    }

    public SearchSourceBuilder produceSearchSourceBuilder(EsQuery esQuery) {
        Assert.notNull(esQuery, "EsQuery Object Empty");

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        // 设置查询超时时间
        searchSourceBuilder.timeout(timeValue);
        // 设置查询类型
        QueryBuilder queryBuilder = setUpQuery(esQuery);
        searchSourceBuilder.query(queryBuilder);
        // 设置分页
        setUpPage(esQuery.getPaging(), searchSourceBuilder);
        // 设置排序
        setUpSort(esQuery.getSorts(), searchSourceBuilder);
        // 设置查询聚合
        setUpAggs(esQuery.getAggregations(), searchSourceBuilder);
        // 设置高亮
        setUpHighlighter(esQuery.getEsHighlighter(), searchSourceBuilder);
        // 设置查询建议
        return searchSourceBuilder;
    }

    /**
     * setUpQuery
     *
     * @param esQuery 查询参数
     * @return org.elasticsearch.index.query.QueryBuilder
     * @Description 设置查询类型
     * @Date 2019-03-11 17:08
     */
    private QueryBuilder setUpQuery(EsQuery esQuery) {
        QueryBuilder queryBuilder = null;
        // 对应获取实际的查询类型
        if (esQuery instanceof TermQuery) {
            queryBuilder = setUpQuery((TermQuery) esQuery);
        } else if (esQuery instanceof TermsQuery) {
            queryBuilder = setUpQuery((TermsQuery) esQuery);
        } else if (esQuery instanceof RangeQuery) {
            queryBuilder = setUpQuery((RangeQuery) esQuery);
        } else if (esQuery instanceof MatchQuery) {
            queryBuilder = setUpQuery((MatchQuery) esQuery);
        } else if (esQuery instanceof MatchPhrasePrefixQuery) {
            queryBuilder = setUpQuery((MatchPhrasePrefixQuery) esQuery);
        } else if (esQuery instanceof MatchPhraseQuery) {
            queryBuilder = setUpQuery((MatchPhraseQuery) esQuery);
        } else if (esQuery instanceof MultiMatchQuery) {
            queryBuilder = setUpQuery((MultiMatchQuery) esQuery);
        } else if (esQuery instanceof BoolQuery) {
            queryBuilder = setUpQuery((BoolQuery) esQuery);
        }

        return queryBuilder;
    }


    private TermsQueryBuilder setUpQuery(TermsQuery termsQuery) {
        TermsQueryBuilder termsQueryBuilder = QueryBuilders.termsQuery(termsQuery.getField(), termsQuery.getValues());

        setBoost(termsQuery, termsQueryBuilder);

        return termsQueryBuilder;
    }

    private RangeQueryBuilder setUpQuery(RangeQuery rangeQuery) {
        RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery(rangeQuery.getField());

        setBoost(rangeQuery, rangeQueryBuilder);

        if (rangeQuery.getRangeType() == RangeQuery.RangeType.Timestamp) {
            // 此处默认应用索引date 类型的format，一般存储都是时间戳格式，此处参数应为时间戳
            rangeQueryBuilder.from(rangeQuery.getFrom());
            rangeQueryBuilder.to(rangeQuery.getTo());
        } else if (rangeQuery.getRangeType() == RangeQuery.RangeType.Number) {
            rangeQueryBuilder.gte(rangeQuery.getFrom());
            rangeQueryBuilder.lte(rangeQuery.getTo());
        }

        return rangeQueryBuilder;
    }

    private TermQueryBuilder setUpQuery(TermQuery termQuery) {
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery(termQuery.getField(), termQuery.getValue());

        setBoost(termQuery, termQueryBuilder);

        return termQueryBuilder;
    }

    private MatchQueryBuilder setUpQuery(MatchQuery matchQuery) {
        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery(matchQuery.getField(), matchQuery.getValue());

        setBoost(matchQuery, matchQueryBuilder);

        if (!StringUtils.isEmpty(matchQuery.getAnalyzer())) {
            matchQueryBuilder.analyzer(matchQuery.getAnalyzer());
        }

        if (!StringUtils.isEmpty(matchQuery.getFuzziness())) {
            matchQueryBuilder.fuzziness(matchQuery.getFuzziness());
        } else {
            matchQueryBuilder.fuzziness(Fuzziness.AUTO);
        }

        if (!StringUtils.isEmpty(matchQuery.getOperator())) {
            if (matchQuery.getOperator().equals(Operator.OR.toString())) {
                matchQueryBuilder.operator(Operator.OR);
            } else {
                matchQueryBuilder.operator(Operator.AND);
            }
        } else {
            matchQueryBuilder.operator(MatchQueryBuilder.DEFAULT_OPERATOR);
        }

        if (matchQuery.getMaxExpansions() != 0) {
            matchQueryBuilder.maxExpansions(matchQuery.getMaxExpansions());
        }

        if (matchQuery.getPrefixLength() != 0) {
            matchQueryBuilder.prefixLength(matchQuery.getPrefixLength());
        }

        if (!StringUtils.isEmpty(matchQuery.getMinimumShouldMatch())) {
            matchQueryBuilder.minimumShouldMatch(matchQuery.getMinimumShouldMatch());
        }

        return matchQueryBuilder;
    }

    private MatchPhraseQueryBuilder setUpQuery(MatchPhraseQuery matchPhraseQuery) {
        MatchPhraseQueryBuilder matchPhraseQueryBuilder = QueryBuilders.
            matchPhraseQuery(matchPhraseQuery.getField(), matchPhraseQuery.getValue());

        setBoost(matchPhraseQuery, matchPhraseQueryBuilder);

        if (!StringUtils.isEmpty(matchPhraseQuery.getAnalyzer())) {
            matchPhraseQueryBuilder.analyzer(matchPhraseQuery.getAnalyzer());
        }
        if (matchPhraseQuery.getSlop() != 0) {
            matchPhraseQueryBuilder.slop(matchPhraseQuery.getSlop());
        }

        return matchPhraseQueryBuilder;
    }

    private MatchPhrasePrefixQueryBuilder setUpQuery(MatchPhrasePrefixQuery matchPhrasePrefixQuery) {
        MatchPhrasePrefixQueryBuilder matchPhrasePrefixQueryBuilder = QueryBuilders.
            matchPhrasePrefixQuery(matchPhrasePrefixQuery.getField(), matchPhrasePrefixQuery.getValue());

        setBoost(matchPhrasePrefixQuery, matchPhrasePrefixQueryBuilder);

        if (!StringUtils.isEmpty(matchPhrasePrefixQuery.getAnalyzer())) {
            matchPhrasePrefixQueryBuilder.analyzer(matchPhrasePrefixQuery.getAnalyzer());
        }
        if (matchPhrasePrefixQuery.getMaxExpansions() != 0) {
            matchPhrasePrefixQueryBuilder.maxExpansions(matchPhrasePrefixQuery.getMaxExpansions());
        }
        if (matchPhrasePrefixQuery.getSlop() != 0) {
            matchPhrasePrefixQueryBuilder.slop(matchPhrasePrefixQuery.getSlop());
        }
        return matchPhrasePrefixQueryBuilder;
    }

    private MultiMatchQueryBuilder setUpQuery(MultiMatchQuery multiMatchQuery) {
        MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery(multiMatchQuery.getValue(),
            multiMatchQuery.getFields().toArray(new String[multiMatchQuery.getFields().size()]));

        setBoost(multiMatchQuery, multiMatchQueryBuilder);

        if (!StringUtils.isEmpty(multiMatchQuery.getOperator())) {
            if (multiMatchQuery.getOperator().equals(Operator.AND.toString())) {
                multiMatchQueryBuilder.operator(Operator.AND);
            } else {
                multiMatchQueryBuilder.operator(Operator.OR);
            }
        } else {
            multiMatchQueryBuilder.operator(MultiMatchQueryBuilder.DEFAULT_OPERATOR);
        }

        if (multiMatchQuery.getTieBreaker() != 0.0d) {
            multiMatchQueryBuilder.tieBreaker(multiMatchQuery.getTieBreaker());
        }

        if (multiMatchTypes.contains(multiMatchQuery.getType())) {
            multiMatchQueryBuilder.type(multiMatchQuery.getType());
        } else {
            multiMatchQueryBuilder.type(MultiMatchQueryBuilder.Type.BEST_FIELDS);
        }

        return multiMatchQueryBuilder;
    }

    private BoolQueryBuilder setUpQuery(BoolQuery boolQuery) {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        setBoost(boolQuery, boolQueryBuilder);

        if (boolQuery.getMinimumShouldMatch() != 0) {
            boolQueryBuilder.minimumShouldMatch(boolQuery.getMinimumShouldMatch());
        }

        if (!CollectionUtils.isEmpty(boolQuery.getShoulds())) {
            for (EsQuery shouldQuery : boolQuery.getShoulds()) {
                boolQueryBuilder.should(setUpQuery(shouldQuery));
            }
        }

        if (!CollectionUtils.isEmpty(boolQuery.getMusts())) {
            for (EsQuery mustQuery : boolQuery.getMusts()) {
                boolQueryBuilder.must(setUpQuery(mustQuery));
            }
        }
        if (!CollectionUtils.isEmpty(boolQuery.getMustNots())) {
            for (EsQuery mustNotQuery : boolQuery.getMustNots()) {
                boolQueryBuilder.mustNot(setUpQuery(mustNotQuery));
            }
        }
        if (!CollectionUtils.isEmpty(boolQuery.getFilters())) {
            for (EsQuery filterQuery : boolQuery.getFilters()) {
                boolQueryBuilder.filter(setUpQuery(filterQuery));
            }
        }
        return boolQueryBuilder;
    }

    /**
     * setBoost
     *
     * @param esQuery      查询参数
     * @param queryBuilder 查询builder
     * @Description 统一设置权重系数
     * @Date 2019-03-11 20:27
     */
    private void setBoost(EsQuery esQuery, QueryBuilder queryBuilder) {
        if (esQuery.getBoost() != 0.0d) {
            queryBuilder.boost(esQuery.getBoost());
        }
    }

    /**
     * setUpPage
     *
     * @param page                分页信息
     * @param searchSourceBuilder searchSourceBuilder
     * @Description 设置分页
     * @Date 2019-03-11 20:28
     */
    private void setUpPage(EsPaging page, SearchSourceBuilder searchSourceBuilder) {
        if (page != null) {
            searchSourceBuilder.from(page.getPageNo());
            searchSourceBuilder.size(page.getPageSize());
        }
    }

    /**
     * setUpSort
     *
     * @param sorts               排序参数
     * @param searchSourceBuilder searchSourceBuilder
     * @Description 设置查询排序
     * @Date 2019-03-11 20:28
     */
    private void setUpSort(List<EsSort> sorts, SearchSourceBuilder searchSourceBuilder) {
        if (!CollectionUtils.isEmpty(sorts)) {
            for (EsSort sort : sorts) {
                FieldSortBuilder fieldSortBuilder = SortBuilders.fieldSort(sort.getSort());
                // 排序
                if (sort.getOrderType() == EsSort.OrderType.ASC) {
                    fieldSortBuilder.order(SortOrder.ASC);
                } else {
                    fieldSortBuilder.order(SortOrder.DESC);
                }
                // 字段为空的策略
                if (sort.getMissType() == EsSort.MissType.FIRST) {
                    fieldSortBuilder.missing("_first");
                } else if (sort.getMissType() == EsSort.MissType.LAST) {
                    fieldSortBuilder.missing("_last");
                } else if (sort.getMissType() == EsSort.MissType.CUSTOM) {
                    fieldSortBuilder.missing(EsSort.MissType.CUSTOM.toString());
                }
                // 排序模式（排序模式仅限于用在数字类型的数组区间。例如 [20,30]）
                if (sort.getSortMode() != EsSort.SortMode.NULL) {
                    fieldSortBuilder.sortMode(SortMode.fromString(EsSort.SortMode.MAX.toString()));
                }

                searchSourceBuilder.sort(fieldSortBuilder);
            }
        }
    }

    /**
     * setUpAggs
     *
     * @param aggregations        聚合参数
     * @param searchSourceBuilder searchSourceBuilder
     * @Description 设置查询聚合
     * @Date 2019-03-11 20:30
     */
    private void setUpAggs(List<EsAggregations> aggregations, SearchSourceBuilder searchSourceBuilder) {
        if (!CollectionUtils.isEmpty(aggregations)) {
            for (EsAggregations aggregation : aggregations) {
                searchSourceBuilder.aggregation(recursionHandleAgg(aggregation));
            }
        }
    }

    /**
     * recursionHandleAgg
     *
     * @param aggregation 聚合参数
     * @return org.elasticsearch.search.aggregations.AggregationBuilder
     * @Description 递归处理子聚合
     * @Date 2019-03-12 14:27
     */
    private AggregationBuilder recursionHandleAgg(EsAggregations aggregation) {
        AggregationBuilder aggregationBuilder = null;
        if (aggregation.getAggType() == EsAggregations.AggType.TERMS) {
            aggregationBuilder = handleTermAgg(aggregation);
        } else if (aggregation.getAggType() == EsAggregations.AggType.DATE_RANGE) {
            aggregationBuilder = handleDataRangeAgg(aggregation);
        } else if (aggregation.getAggType() == EsAggregations.AggType.RANGE) {
            aggregationBuilder = handleRangeAgg(aggregation);
        } else if (aggregation.getAggType() == EsAggregations.AggType.MAX) {
            aggregationBuilder = handleMaxAgg(aggregation);
        } else if (aggregation.getAggType() == EsAggregations.AggType.MIN) {
            aggregationBuilder = handleMinAgg(aggregation);
        } else if (aggregation.getAggType() == EsAggregations.AggType.AVG) {
            aggregationBuilder = handleAvgAgg(aggregation);
        } else if (aggregation.getAggType() == EsAggregations.AggType.SUM) {
            aggregationBuilder = handleSumAgg(aggregation);
        } else if (aggregation.getAggType() == EsAggregations.AggType.Filter) {
            aggregationBuilder = handleFilterAgg(aggregation);
        }

        return aggregationBuilder;
    }

    private AggregationBuilder handleTermAgg(EsAggregations aggregation) {
        TermsAggregationBuilder termsAggregationBuilder = AggregationBuilders.terms(aggregation.getName()).
            field(aggregation.getField());

        handleSubAgg(aggregation, termsAggregationBuilder);

        return termsAggregationBuilder;
    }

    private AggregationBuilder handleDataRangeAgg(EsAggregations aggregation) {
        DateRangeAggregationBuilder dateRangeAggregationBuilder = AggregationBuilders.dateRange(aggregation.getName()).
            field(aggregation.getField());

        dateRangeAggregationBuilder.addRange(aggregation.getFrom(), aggregation.getTo());

        handleSubAgg(aggregation, dateRangeAggregationBuilder);

        return dateRangeAggregationBuilder;
    }

    private AggregationBuilder handleRangeAgg(EsAggregations aggregation) {
        RangeAggregationBuilder rangeAggregationBuilder = AggregationBuilders.range(aggregation.getName()).
            field(aggregation.getField());

        rangeAggregationBuilder.addRange(aggregation.getFrom(), aggregation.getTo());

        handleSubAgg(aggregation, rangeAggregationBuilder);

        return rangeAggregationBuilder;
    }

    private AggregationBuilder handleMaxAgg(EsAggregations aggregation) {
        MaxAggregationBuilder maxAggregationBuilder = AggregationBuilders.max(aggregation.getName()).
            field(aggregation.getField());
        return maxAggregationBuilder;
    }

    private AggregationBuilder handleMinAgg(EsAggregations aggregation) {
        MinAggregationBuilder minAggregationBuilder = AggregationBuilders.min(aggregation.getName()).
            field(aggregation.getField());
        return minAggregationBuilder;
    }

    private AggregationBuilder handleAvgAgg(EsAggregations aggregation) {
        AvgAggregationBuilder avgAggregationBuilder = AggregationBuilders.avg(aggregation.getName()).
            field(aggregation.getField());
        return avgAggregationBuilder;
    }

    private AggregationBuilder handleSumAgg(EsAggregations aggregation) {
        SumAggregationBuilder sumAggregationBuilder = AggregationBuilders.sum(aggregation.getName()).
            field(aggregation.getField());
        return sumAggregationBuilder;
    }

    private AggregationBuilder handleFilterAgg(EsAggregations aggregation) {
        // filter 类型： FilterType必须存在
        Assert.notNull(aggregation.getFilterType(), "FilterType Empty");
        QueryBuilder queryBuilder = null;
        if (aggregation.getFilterType() == EsAggregations.FilterType.PREFIX) {
            queryBuilder = QueryBuilders.prefixQuery(aggregation.getField(), aggregation.getFilterValue());
        } else if (aggregation.getFilterType() == EsAggregations.FilterType.MATCH) {
            queryBuilder = QueryBuilders.matchQuery(aggregation.getField(), aggregation.getFilterValue());
        } else if (aggregation.getFilterType() == EsAggregations.FilterType.TERM) {
            queryBuilder = QueryBuilders.termQuery(aggregation.getField(), aggregation.getFilterValue());
        }

        FiltersAggregationBuilder filtersAggregationBuilder = AggregationBuilders.filters(aggregation.getName(), queryBuilder);

        handleSubAgg(aggregation, filtersAggregationBuilder);

        return filtersAggregationBuilder;
    }

    private void handleSubAgg(EsAggregations aggregation, AggregationBuilder aggregationBuilder) {
        if (aggregation.getSubEsAggregations() != null) {
            aggregationBuilder.subAggregation(recursionHandleAgg(aggregation.getSubEsAggregations()));
        }
    }

    /**
     * setUpHighlighter
     *
     * @param highlighter         高亮参数
     * @param searchSourceBuilder searchSourceBuilder
     * @Description 设置查询高亮
     * @Date 2019-03-15 17:03
     */
    private void setUpHighlighter(EsHighlighter highlighter, SearchSourceBuilder searchSourceBuilder) {
        if (highlighter != null) {
            HighlightBuilder highlightBuilder = new HighlightBuilder();

            highlightBuilder.order(HighlightBuilder.Order.SCORE);
            highlightBuilder.requireFieldMatch(highlighter.isRequireFieldMatch());
            if (StringUtils.isEmpty(highlighter.getPreTags())) {
                highlightBuilder.preTags(highlighter.getPreTags());
            }
            if (StringUtils.isEmpty(highlighter.getPostTags())) {
                highlightBuilder.postTags(highlighter.getPostTags());
            }

            if (CollectionUtils.isEmpty(highlighter.getFieldList())) {
                throw new NullPointerException(" EsHighlighter has no fields ");
            }

            for (EsHighlighter.Field field : highlighter.getFieldList()) {
                HighlightBuilder.Field highlight = new HighlightBuilder.Field(field.getName());

                highlight.fragmenter(field.getFragmenter().toString().toLowerCase());
                highlight.fragmentSize(field.getFragmentSize());
                highlight.numOfFragments(field.getFragmentSize());
                highlight.highlighterType(field.getHighLighterType().toString().toLowerCase());

                highlightBuilder.field(highlight);
            }

            searchSourceBuilder.highlighter(highlightBuilder);
        }
    }
    // 设置查询建议
}
