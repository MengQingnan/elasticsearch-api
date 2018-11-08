package com.izaodao.projects.springboot.elasticsearch;

import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@DubboComponentScan(basePackages = "com.izaodao.projects.springboot.elasticsearch.service")
public class SpringbootApplication {

    public static void main(String[] args){
        System.setProperty("es.set.netty.runtime.available.processors", "false");
        SpringApplication.run(SpringbootApplication.class, args);
    }
}
