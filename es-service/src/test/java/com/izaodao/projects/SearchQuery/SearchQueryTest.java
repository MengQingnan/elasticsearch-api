package com.izaodao.projects.SearchQuery;

import com.izaodao.projects.springboot.elasticsearch.SpringbootApplication;
import com.izaodao.projects.springboot.elasticsearch.client.ZaodaoRestHighLevelClient;
import com.izaodao.projects.springboot.elasticsearch.search.EsAggregations;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.MatchPhrasePrefixQueryBuilder;
import org.elasticsearch.index.query.MatchPhraseQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.index.query.TermsQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.avg.Avg;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: Mengqingnan
 * @Description: 测试searchquery
 * @Date: 2018-12-20 15:26
 * Copyright (c) 2018, zaodao All Rights Reserved.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringbootApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SearchQueryTest {
    @Autowired
    private ZaodaoRestHighLevelClient zaodaoRestHighLevelClient;

    @Test
    public void test1() throws IOException {
        SearchRequest searchRequest = new SearchRequest();

        searchRequest.indices("coupon");
        searchRequest.types("coupon");

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        //searchSourceBuilder.query(QueryBuilders.matchQuery("title", "我不确定mqn3").fuzziness(Fuzziness.AUTO)

        searchSourceBuilder.query(QueryBuilders.termQuery("title.keyword", "不确定标题"));

        String[] a = {"2", "2", "2"};

        QueryBuilders.multiMatchQuery("mqn", a);
        //BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        QueryBuilder boolQueryBuilder = QueryBuilders.matchQuery("111", "222")
            .fuzziness(Fuzziness.AUTO)
            .prefixLength(3)
            .maxExpansions(10);

        RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("111");


        searchSourceBuilder.size(10);
        searchSourceBuilder.from(0);
        searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        searchSourceBuilder.sort("id.keyword", SortOrder.DESC);

        boolQueryBuilder = QueryBuilders.boolQuery()
            .should(QueryBuilders.matchQuery("111", "222"))
            .should(QueryBuilders.matchQuery("111", "222"));


        HighlightBuilder highlightBuilder = new HighlightBuilder();

        HighlightBuilder.Field highlightTitle = new HighlightBuilder.Field("title");
        highlightTitle.highlighterType("unified");
        highlightBuilder.field(highlightTitle);

        searchSourceBuilder.highlighter(highlightBuilder);

        AggregationBuilder aggregation = AggregationBuilders.terms("titles")
            .field("title.keyword");

        searchSourceBuilder.aggregation(aggregation);

        AggregationBuilder avgAggregationBuilder = AggregationBuilders.avg("countAvg").field("count");

        searchSourceBuilder.aggregation(avgAggregationBuilder);


        AggregationBuilder filterAggregationBuilder = AggregationBuilders.filter("ids",
            QueryBuilders.prefixQuery("title.keyword", "不"));

        TermsAggregationBuilder termsAggregationBuilder = AggregationBuilders.terms("id").field("id" + ".keyword");

        filterAggregationBuilder.subAggregation(termsAggregationBuilder);

        searchSourceBuilder.aggregation(filterAggregationBuilder);


        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = zaodaoRestHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        SearchHits searchHit = searchResponse.getHits();

        SearchHit[] searchHits = searchHit.getHits();

        for (SearchHit searchHit1 : searchHits) {
            System.out.println(searchHit1.getSourceAsString());
            Map<String, HighlightField> highlightMap = searchHit1.getHighlightFields();

            Set<Map.Entry<String, HighlightField>> set = highlightMap.entrySet();

            for (Map.Entry<String, HighlightField> entry : set) {
                System.out.println(entry.getKey() + "====" + entry.getValue());
            }
        }

        Aggregations aggregations = searchResponse.getAggregations();

        Terms terms = aggregations.get("titles");

        Avg avg = aggregations.get("countAvg");

        List<Terms.Bucket> buckets = (List<Terms.Bucket>) terms.getBuckets();

        Terms.Bucket bucket1 = terms.getBucketByKey("不确定标题");

        System.out.println(bucket1.getDocCount());

        for (Terms.Bucket bucket : buckets) {
            System.out.println(bucket.getKey() + "==" + bucket.getDocCount());
        }
    }

    public static void main(String[] args) {
        EsAggregations.Filter filter = EsAggregations.obtainFilter("mqn", EsAggregations.FilterType.MATCH, "llj", "wo");

        List<EsAggregations.Filter> filters = new ArrayList<>();

        filters.add(filter);

        EsAggregations agg = new EsAggregations("name", "mqn", EsAggregations.AggType.MAX, filters);


        System.out.println(agg);
    }


    public void test() {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        boolQueryBuilder.should(QueryBuilders.matchQuery("111", "222"));
        boolQueryBuilder.should(QueryBuilders.matchQuery("111", "222"));
        boolQueryBuilder.minimumShouldMatch(2);
        boolQueryBuilder.filter()

        MatchAllQueryBuilder matchAllQueryBuilder =  QueryBuilders.matchAllQuery();

        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("test","1111");

        MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery();

        MatchPhraseQueryBuilder matchPhraseQueryBuilder = QueryBuilders.matchPhraseQuery();

        MatchPhrasePrefixQueryBuilder matchPhrasePrefixQueryBuilder = QueryBuilders.matchPhrasePrefixQuery();

        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery();

        TermsQueryBuilder termsQueryBuilder = QueryBuilders.termsQuery();

        RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery();
    }
}
