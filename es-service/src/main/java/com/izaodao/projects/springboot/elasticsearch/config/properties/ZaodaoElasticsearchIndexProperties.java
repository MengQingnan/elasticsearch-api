package com.izaodao.projects.springboot.elasticsearch.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Auther: Mengqingnan
 * @Description: es 索引 初始化配置文件信息
 * @Date: 2018/7/26 上午11:24
 * Copyright (c) 2018, zaodao All Rights Reserved.
 */
@ConfigurationProperties(prefix = "spring.elasticsearch.index.config")
public class ZaodaoElasticsearchIndexProperties {
    /**
     * 索引刷新时长
     */
    private String refreshInterval = "1s";
    /**
     * 每个索引片数
     */
    private String numberOfShards = "5";
    /**
     * 备份份数
     */
    private String numberOfReplicas = "1";
    /**
     *  索引存储类型
     */
    private String storeType = "fs";


    public String getRefreshInterval() {
        return refreshInterval;
    }

    public void setRefreshInterval(String refreshInterval) {
        this.refreshInterval = refreshInterval;
    }

    public String getNumberOfShards() {
        return numberOfShards;
    }

    public void setNumberOfShards(String numberOfShards) {
        this.numberOfShards = numberOfShards;
    }

    public String getNumberOfReplicas() {
        return numberOfReplicas;
    }

    public void setNumberOfReplicas(String numberOfReplicas) {
        this.numberOfReplicas = numberOfReplicas;
    }

    public String getStoreType() {
        return storeType;
    }

    public void setStoreType(String storeType) {
        this.storeType = storeType;
    }
}
