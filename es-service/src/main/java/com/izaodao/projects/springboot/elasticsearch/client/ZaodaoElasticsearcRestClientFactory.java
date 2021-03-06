package com.izaodao.projects.springboot.elasticsearch.client;

import com.izaodao.projects.springboot.elasticsearch.client.request.IElasticsearchQueryBuilders;
import com.izaodao.projects.springboot.elasticsearch.client.request.IElasticsearchRequestFactory;
import com.izaodao.projects.springboot.elasticsearch.client.response.IElasticsearchClientResponseHandle;
import com.izaodao.projects.springboot.elasticsearch.config.properties.ZaodaoElasticsearchProperties;
import org.apache.http.HttpHost;
import org.elasticsearch.client.Node;
import org.elasticsearch.client.NodeSelector;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.IOException;

/**
 * @Auther: Mengqingnan
 * @Description:
 * @Date: 2018/10/8 3:50 PM
 * Copyright (c) 2018, zaodao All Rights Reserved.
 */
public class ZaodaoElasticsearcRestClientFactory {
    /**
     * logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ZaodaoElasticsearcRestClientFactory.class);
    /**
     * 初始化es config 配置文件
     */
    private ZaodaoElasticsearchProperties properties;
    /**
     * restHighLevelClient
     */
    private static ZaodaoRestHighLevelClient restHighLevelClient;
    /**
     * restClientBuilder
     */
    private RestClientBuilder restClientBuilder;
    /**
     * elasticsearchClientResponseHandle
     */
    private IElasticsearchClientResponseHandle elasticsearchClientResponseHandle;
    /**
     * requestFactory
     */
    private IElasticsearchRequestFactory requestFactory;
    /**
     * queryBuilders
     */
    private IElasticsearchQueryBuilders queryBuilders;


    /**
     * 单例
     */
    private static ZaodaoElasticsearcRestClientFactory restClientFactory;

    private ZaodaoElasticsearcRestClientFactory(ZaodaoElasticsearchProperties properties,
                                                IElasticsearchClientResponseHandle elasticsearchClientResponseHandle,
                                                IElasticsearchRequestFactory requestFactory,
                                                IElasticsearchQueryBuilders queryBuilders) {
        this.properties = properties;
        this.elasticsearchClientResponseHandle = elasticsearchClientResponseHandle;
        this.requestFactory = requestFactory;
        this.queryBuilders = queryBuilders;
    }

    /**
     * 初始化 RestClientBuilder
     */
    private void init() {
        restClientBuilder = RestClient.builder(createHttpHosts());

        restClientBuilder.setMaxRetryTimeoutMillis(this.properties.getMaxRetryTimeOut());

        if (this.properties.getNodesSelector()) {
            restClientBuilder.setNodeSelector(NodeSelector.SKIP_DEDICATED_MASTERS);
        }

        if (this.properties.getNodesFailureListener()) {
            restClientBuilder.setFailureListener(new RestClient.FailureListener() {
                @Override
                public void onFailure(Node node) {
                    System.out.println(node.getHost().getAddress() + "is down");
                }
            });
        }

        if (this.properties.getHttpClientCustomize()) {
            restClientBuilder.setHttpClientConfigCallback(httpClientBuilder -> {
                httpClientBuilder.setMaxConnTotal(100);
                httpClientBuilder.setMaxConnPerRoute(100);

                return httpClientBuilder;
            });
        }

        if (this.properties.getRequestCustomize()) {
            restClientBuilder.setRequestConfigCallback(requestConfigCallback -> {
                requestConfigCallback.setConnectTimeout(5000);
                requestConfigCallback.setSocketTimeout(10000);
                requestConfigCallback.setConnectionRequestTimeout(8000);

                return requestConfigCallback;
            });
        }

        restHighLevelClient = new ZaodaoRestHighLevelClient(this.restClientBuilder,
            this.elasticsearchClientResponseHandle, this.requestFactory, this.queryBuilders);
    }

    /**
     * 服务关闭 销毁对应的builder client
     */
    private void close() {
        if (restHighLevelClient != null) {
            try {
                restHighLevelClient.close();
            } catch (IOException e) {
                LOGGER.error("close restHighLevelClient exception", e);
            }
        }
    }

    /**
     * 整理 elastic host 相关信息
     */
    private HttpHost[] createHttpHosts() {
        String clusterNodes = properties.getClusterNodes();

        if (StringUtils.isEmpty(clusterNodes)) {
            throw new NullPointerException(" cluster nodes is empty ");
        }

        String[] nodes = clusterNodes.split(",");

        HttpHost[] httpHosts = new HttpHost[nodes.length];

        for (int i = 0; i < httpHosts.length; i++) {
            String[] node = nodes[i].split(":");
            httpHosts[i] = new HttpHost(node[0], Integer.valueOf(node[1]), properties.getClusterScheme());
        }

        return httpHosts;
    }

    public ZaodaoRestHighLevelClient getRestHighLevelClient() {
        return restHighLevelClient;
    }

    public static ZaodaoElasticsearcRestClientFactory createClientFactory(ZaodaoElasticsearchProperties properties,
                                                                          IElasticsearchClientResponseHandle handle,
                                                                          IElasticsearchRequestFactory requestFactory,
                                                                          IElasticsearchQueryBuilders queryBuilders) {
        if (restClientFactory == null) {
            restClientFactory = new ZaodaoElasticsearcRestClientFactory(properties, handle, requestFactory,queryBuilders);

            return restClientFactory;
        }

        return restClientFactory;
    }
}
