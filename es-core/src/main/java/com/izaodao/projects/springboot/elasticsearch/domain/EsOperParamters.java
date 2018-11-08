package com.izaodao.projects.springboot.elasticsearch.domain;

import java.io.Serializable;

/**
 * @Auther: Mengqingnan
 * @Description: es 基本操作参数类
 * @Date: 2018/9/29 下午3:33
 * Copyright (c) 2018, zaodao All Rights Reserved.
 */
public class EsOperParamters extends EsBase implements Serializable {
    /**
     * 序列化
     */
    private static final long serialVersionUID = -4273808679712391783L;
    /**
     *  请求参数
     */
    private String paramtersJson;

    public String getParamtersJson() {
        return paramtersJson;
    }

    public void setParamtersJson(String paramtersJson) {
        this.paramtersJson = paramtersJson;
    }
}
