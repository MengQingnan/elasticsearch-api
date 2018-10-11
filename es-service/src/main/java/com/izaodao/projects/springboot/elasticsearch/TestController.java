package com.izaodao.projects.springboot.elasticsearch;


import com.izaodao.projects.springboot.elasticsearch.domain.EsOperParamters;
import com.izaodao.projects.springboot.elasticsearch.interfaces.IElasticsearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: Mengqingnan
 * @Description:
 * @Date: 2018/9/29 下午4:38
 * Copyright (c) 2018, zaodao All Rights Reserved.
 */
@RestController
public class TestController {
    @Autowired
    private IElasticsearchService elasticsearchService;

    @GetMapping("test")
    public String test() {
        EsOperParamters esOperParamters = new EsOperParamters();

        esOperParamters.setIndex("japanesedoyensboot111");
        esOperParamters.setType("japanese_doyen_sboot111");

        elasticsearchService.getInfoById("taA42mMBWxdGga3plUKX", esOperParamters);

        return "1";
    }
}
