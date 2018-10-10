package com.izaodao.projects.springboot.elasticsearch.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Auther: Mengqingnan
 * @Description: 初始化配置文件信息
 * @Date: 2018/7/26 上午11:24
 * Copyright (c) 2018, zaodao All Rights Reserved.
 */
@ConfigurationProperties(prefix = "spring.dubbo.config")
public class ZaodaoDubboProperties {
    /**
     * es 提供者名称
     */
    private String applicationName = "es-dubbo-provider";

    /**
     * zookeeper注册地址
     */
    private String registryAddress = "127.0.0.1:2181";

    /**
     *  注册协议
     */
    private String registryProtocol = "zookeeper";
    /**
     *  注册Id
     */
    private String registryId = "zookeeper";
    /**
     *  协议名称
     */
    private String protocolName = "dubbo";
    /**
     *  协议端口
     */
    private Integer protocolPort = 40011;

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getRegistryAddress() {
        return registryAddress;
    }

    public void setRegistryAddress(String registryAddress) {
        this.registryAddress = registryAddress;
    }

    public String getRegistryProtocol() {
        return registryProtocol;
    }

    public void setRegistryProtocol(String registryProtocol) {
        this.registryProtocol = registryProtocol;
    }

    public String getRegistryId() {
        return registryId;
    }

    public void setRegistryId(String registryId) {
        this.registryId = registryId;
    }

    public String getProtocolName() {
        return protocolName;
    }

    public void setProtocolName(String protocolName) {
        this.protocolName = protocolName;
    }

    public Integer getProtocolPort() {
        return protocolPort;
    }

    public void setProtocolPort(Integer protocolPort) {
        this.protocolPort = protocolPort;
    }
}
