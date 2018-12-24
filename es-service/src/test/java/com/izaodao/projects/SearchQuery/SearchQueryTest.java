package com.izaodao.projects.SearchQuery;

import com.izaodao.projects.springboot.elasticsearch.SpringbootApplication;
import com.izaodao.projects.springboot.elasticsearch.client.ZaodaoRestHighLevelClient;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
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
//            .fuzziness(Fuzziness.AUTO)
//            .prefixLength(3)
            //.maxExpansions(10));
        searchSourceBuilder.size(10);
        searchSourceBuilder.from(0);
        searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        searchSourceBuilder.sort("id.keyword", SortOrder.DESC);

        HighlightBuilder highlightBuilder = new HighlightBuilder();

        HighlightBuilder.Field highlightTitle = new HighlightBuilder.Field("title");
        highlightTitle.highlighterType("unified");
        highlightBuilder.field(highlightTitle);

        searchSourceBuilder.highlighter(highlightBuilder);

        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = zaodaoRestHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        SearchHits searchHit = searchResponse.getHits();

        SearchHit[] searchHits = searchHit.getHits();

        for (SearchHit searchHit1 : searchHits) {
            System.out.println(searchHit1.getSourceAsString());
            Map<String, HighlightField> highlightMap = searchHit1.getHighlightFields();

            Set<Map.Entry<String, HighlightField>> set = highlightMap.entrySet();

            for (Map.Entry<String, HighlightField> entry : set) {
                System.out.println(entry.getKey()+"===="+entry.getValue());
            }
        }
    }
}
