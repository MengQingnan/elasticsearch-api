package com.izaodao.projects.springboot.elasticsearch.config;

import com.izaodao.projects.springboot.elasticsearch.client.IZaodaoRestHighLevelClient;
import com.izaodao.projects.springboot.elasticsearch.client.ZaodaoElasticsearcRestClientFactory;
import com.izaodao.projects.springboot.elasticsearch.client.request.ElasticsearchQueryBuilders;
import com.izaodao.projects.springboot.elasticsearch.client.request.ElasticsearchRequestFactory;
import com.izaodao.projects.springboot.elasticsearch.client.response.ElasticsearchClientResponseHandle;
import com.izaodao.projects.springboot.elasticsearch.config.properties.ZaodaoElasticsearchIndexProperties;
import com.izaodao.projects.springboot.elasticsearch.config.properties.ZaodaoElasticsearchProperties;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.inject.Singleton;
import org.elasticsearch.common.unit.TimeValue;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * @Auther: Mengqingnan
 * @Description:
 * @Date: 2018/7/26 上午11:41
 * Copyright (c) 2018, zaodao All Rights Reserved.
 */
@Configuration
@ConditionalOnClass({RestHighLevelClient.class})
@ConditionalOnProperty(prefix = "spring.elasticsearch.rest.config", name = "cluster-nodes", matchIfMissing = false)
@EnableConfigurationProperties({ZaodaoElasticsearchProperties.class, ZaodaoElasticsearchIndexProperties.class})
public class ZaodaoElasticsearchAutoConfiguration {

    private final ZaodaoElasticsearchProperties properties;

    private final ZaodaoElasticsearchIndexProperties indexProperties;

    public ZaodaoElasticsearchAutoConfiguration(ZaodaoElasticsearchProperties properties,
                                                ZaodaoElasticsearchIndexProperties indexProperties) {
        this.properties = properties;
        this.indexProperties = indexProperties;
    }

    @Bean(initMethod = "init", destroyMethod = "close")
    @Singleton
    @ConditionalOnMissingBean
    public ZaodaoElasticsearcRestClientFactory elasticsearcRestClientFactory() {
        return ZaodaoElasticsearcRestClientFactory.createClientFactory(properties,
            new ElasticsearchClientResponseHandle(),
            new ElasticsearchRequestFactory(indexProperties),
            new ElasticsearchQueryBuilders(new TimeValue(60, TimeUnit.SECONDS)));
    }

    @Bean
    @Singleton
    @ConditionalOnBean(ZaodaoElasticsearcRestClientFactory.class)
    @ConditionalOnMissingBean
    public IZaodaoRestHighLevelClient getRestHighLevelClient(ZaodaoElasticsearcRestClientFactory zaodaoElasticsearcRestClientFactory){
        return zaodaoElasticsearcRestClientFactory.getRestHighLevelClient();
    }
}
