package com.izaodao.projects.springboot.elasticsearch.config.dictionary;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @Auther: Mengqingnan
 * @Description:
 * @Date: 2018/10/24 7:41 PM
 * Copyright (c) 2018, zaodao All Rights Reserved.
 */
public enum DataTypeMatchEnum {
    StringMatch("java.lang.String", "text", "keyword"),
    IntegerMatch("java.lang.Integer", "integer", ""),
    LongMatch("java.lang.Long", "long", ""),
    DoubleMatch("java.lang.Double", "double", ""),
    FloatMatch("java.lang.Float", "float", ""),
    DateMatch("java.util.Date", "date", ""),
    BooleanMatch("java.lang.Boolean", "boolean", "");

    private String jType;

    private String esTypeFirst;

    private String esTypeSecond;

    DataTypeMatchEnum(String jType, String esTypeFirst, String esTypeSecond) {
        this.jType = jType;
        this.esTypeFirst = esTypeFirst;
        this.esTypeSecond = esTypeSecond;
    }

    public String getjType() {
        return jType;
    }

    public void setjType(String jType) {
        this.jType = jType;
    }

    public String getEsTypeFirst() {
        return esTypeFirst;
    }

    public void setEsTypeFirst(String esTypeFirst) {
        this.esTypeFirst = esTypeFirst;
    }

    public String getEsTypeSecond() {
        return esTypeSecond;
    }

    public void setEsTypeSecond(String esTypeSecond) {
        this.esTypeSecond = esTypeSecond;
    }

    public static DataTypeMatchEnum parse(String jType) {
        return Arrays
            .stream(values())
            .collect(Collectors.toMap(DataTypeMatchEnum::getjType, element -> element)).get(jType);
    }
}
