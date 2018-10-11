package com.izaodao.projects.springboot.elasticsearch.config;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.izaodao.projects.springboot.elasticsearch.config.properties.ZaodaoDubboProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Auther: Mengqingnan
 * @Description: dubbo 初始化配置
 * @Date: 2018/9/28 下午4:33
 * Copyright (c) 2018, zaodao All Rights Reserved.
 */
@Configuration
@ConditionalOnClass( {ApplicationConfig.class, RegistryConfig.class, ProtocolConfig.class})
@ConditionalOnProperty(prefix = "spring.dubbo.config", name = "registry-address", matchIfMissing = false)
@EnableConfigurationProperties(ZaodaoDubboProperties.class)
public class ZaodaoDubboConfiguration {

    private final ZaodaoDubboProperties properties;

    public ZaodaoDubboConfiguration(ZaodaoDubboProperties properties){
        this.properties = properties;
    }

    @Bean
    @ConditionalOnMissingBean
    public ApplicationConfig applicationConfig() {
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName(properties.getApplicationName());
        return applicationConfig;
    }

    @Bean
    @ConditionalOnMissingBean
    public RegistryConfig registryConfig() {
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress(properties.getRegistryAddress());
        registryConfig.setProtocol(properties.getRegistryProtocol());
        registryConfig.setId(properties.getRegistryId());
        //registryConfig.setClient("curator");
        return registryConfig;
    }

    @Bean
    @ConditionalOnMissingBean
    public ProtocolConfig protocolConfig() {
        ProtocolConfig protocolConfig = new ProtocolConfig();
        protocolConfig.setName(properties.getProtocolName());
        protocolConfig.setPort(properties.getProtocolPort());
        protocolConfig.setId(properties.getRegistryId());
        return protocolConfig;
    }
}
