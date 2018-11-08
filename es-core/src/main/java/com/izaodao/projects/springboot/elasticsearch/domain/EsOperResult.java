package com.izaodao.projects.springboot.elasticsearch.domain;

import java.io.Serializable;

/**
 * @Auther: Mengqingnan
 * @Description: es 基本操作结果类
 * @Date: 2018/9/29 下午3:33
 * Copyright (c) 2018, zaodao All Rights Reserved.
 */
public final class EsOperResult extends EsBase implements Serializable {
    /**
     * 序列化
     */
    private static final long serialVersionUID = -4273808679712391783L;
    /**
     * 操作状态
     */
    private Boolean operFlag = Boolean.TRUE;
    /**
     * 操作结果
     */
    private String reesult;

    public Boolean getOperFlag() {
        return operFlag;
    }

    public void setOperFlag(Boolean operFlag) {
        this.operFlag = operFlag;
    }

    public String getReesult() {
        return reesult;
    }

    public void setReesult(String reesult) {
        this.reesult = reesult;
    }
}
