package com.izaodao.projects.springboot.elasticsearch.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Auther: Mengqingnan
 * @Description: 初始化配置文件信息
 * @Date: 2018/7/26 上午11:24
 * Copyright (c) 2018, zaodao All Rights Reserved.
 */
@ConfigurationProperties(prefix = "spring.rest.elasticsearch")
public class ZaodaoElasticsearchProperties {
    /**
     * es 集群名称
     */
    private String clusterName = "zaodao-elasticsearch";
    /**
     * 集群节点列表
     */
    private String clusterNodes;
    /**
     * 请求方式
     */
    private String clusterScheme;
    /**
     *  是否支持节点选取配置，默认为true，在true的时候，设置为SKIP_DEDICATED_MASTERS，每次请求跳过节点选取
     */
    private Boolean nodesSelector = Boolean.TRUE;
    /**
     *  是否配置节点失败时的监听器，失败的节点会通过该监听，做相关处理
     */
    private Boolean nodesFailureListener = Boolean.TRUE;
    /**
     *  是否支持访问请求的定制，例如 连接时间、超时时间等
     */
    private Boolean requestCustomize = Boolean.TRUE;
    /**
     *  是否支持http客户端相关信息定制，例如 proxy、ssl 等，该配置主要针对异步请求
     */
    private Boolean httpClientCustomize = Boolean.FALSE;
    /**
     * 最大重试时间，防止大量相同请求重复访问，官方默认30S，此处默认10S
     */
    private Integer maxRetryTimeOut = 10000;

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public String getClusterNodes() {
        return clusterNodes;
    }

    public void setClusterNodes(String clusterNodes) {
        this.clusterNodes = clusterNodes;
    }

    public Boolean getNodesSelector() {
        return nodesSelector;
    }

    public void setNodesSelector(Boolean nodesSelector) {
        this.nodesSelector = nodesSelector;
    }

    public Boolean getNodesFailureListener() {
        return nodesFailureListener;
    }

    public void setNodesFailureListener(Boolean nodesFailureListener) {
        this.nodesFailureListener = nodesFailureListener;
    }

    public Boolean getRequestCustomize() {
        return requestCustomize;
    }

    public void setRequestCustomize(Boolean requestCustomize) {
        this.requestCustomize = requestCustomize;
    }

    public Boolean getHttpClientCustomize() {
        return httpClientCustomize;
    }

    public void setHttpClientCustomize(Boolean httpClientCustomize) {
        this.httpClientCustomize = httpClientCustomize;
    }

    public Integer getMaxRetryTimeOut() {
        return maxRetryTimeOut;
    }

    public void setMaxRetryTimeOut(Integer maxRetryTimeOut) {
        this.maxRetryTimeOut = maxRetryTimeOut;
    }

    public String getClusterScheme() {
        return clusterScheme;
    }

    public void setClusterScheme(String clusterScheme) {
        this.clusterScheme = clusterScheme;
    }
}
