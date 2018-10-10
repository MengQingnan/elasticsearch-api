package com.izaodao.projects.springboot.elasticsearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
//@DubboComponentScan(basePackages = "com.izaodao.projects.springboot.elasticsearch.service")
public class SpringbootApplication {

    public static void main(String[] args) throws IOException, InterruptedException {
        System.setProperty("es.set.netty.runtime.available.processors", "false");
        SpringApplication.run(SpringbootApplication.class, args);
    }
}
