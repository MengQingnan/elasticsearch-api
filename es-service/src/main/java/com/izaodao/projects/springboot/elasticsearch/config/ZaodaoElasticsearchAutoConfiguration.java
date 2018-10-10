package com.izaodao.projects.springboot.elasticsearch.config;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.inject.Singleton;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Auther: Mengqingnan
 * @Description:
 * @Date: 2018/7/26 上午11:41
 * Copyright (c) 2018, zaodao All Rights Reserved.
 */
@Configuration
@ConditionalOnClass({RestHighLevelClient.class})
@ConditionalOnProperty(prefix = "spring.rest.elasticsearch", name = "cluster-nodes", matchIfMissing = false)
@EnableConfigurationProperties(ZaodaoElasticsearchProperties.class)
@Slf4j
public class ZaodaoElasticsearchAutoConfiguration {

    private final ZaodaoElasticsearchProperties properties;

    public ZaodaoElasticsearchAutoConfiguration(ZaodaoElasticsearchProperties properties) {
        this.properties = properties;
    }

    @Bean(initMethod = "init", destroyMethod = "close")
    @ConditionalOnMissingBean
    public ZaodaoElasticsearcRestClientFactory elasticsearcRestClientFactory() {
        return ZaodaoElasticsearcRestClientFactory.createClientFactory(properties);
    }

    @Bean
    @Singleton
    @ConditionalOnBean(ZaodaoElasticsearcRestClientFactory.class)
    @ConditionalOnMissingBean
    public RestHighLevelClient getRestHighLevelClient(ZaodaoElasticsearcRestClientFactory zaodaoElasticsearcRestClientFactory){
        return zaodaoElasticsearcRestClientFactory.getRestHighLevelClient();
    }
}
