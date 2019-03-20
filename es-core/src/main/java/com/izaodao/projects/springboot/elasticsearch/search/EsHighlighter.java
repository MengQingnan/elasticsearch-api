package com.izaodao.projects.springboot.elasticsearch.search;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: Mengqingnan
 * @Description: 高亮显示
 * @Date: 2018-12-25 11:28
 * Copyright (c) 2018, zaodao All Rights Reserved.
 */
public class EsHighlighter implements Serializable {
    private static final long serialVersionUID = -1177787264549713853L;

    private boolean requireFieldMatch;

    private String preTags;

    private String postTags;

    private List<Field> fieldList;

    public EsHighlighter() {
        this(Boolean.TRUE);
    }

    public EsHighlighter(boolean requireFieldMatch) {
        this.requireFieldMatch = requireFieldMatch;
        this.preTags = "<em class='hightlight'>";
        this.postTags = "</em>";
        fieldList = new ArrayList<>();
    }

    public void addField(String name) {
        addField(name, HighLighterType.Unified);
    }

    public void addField(String name, HighLighterType highLighterType) {
        addField(name, 5, 100, highLighterType);
    }

    public void addField(String name, int numberOfFragments, int fragmentSize, HighLighterType highLighterType) {
        addField(name, Fragmenter.Span, numberOfFragments, fragmentSize, highLighterType);
    }

    public void addField(String name, Fragmenter fragmenter, int numberOfFragments, int fragmentSize,
                         HighLighterType highLighterType) {
        if (CollectionUtils.isEmpty(fieldList)) {
            fieldList = new ArrayList<>();
        }

        if (StringUtils.isEmpty(name)) {
            throw new NullPointerException("EsHighlighter field name is empty!");
        } else if (fieldList.size() > 0 && this.requireFieldMatch == Boolean.TRUE) {
            throw new IllegalStateException("RequireFieldMatch is true that just highlight one!");
        }

        Field field = new Field();

        field.setName(name).setNumberOfFragments(numberOfFragments < 0 ? 0 : numberOfFragments)
            .setFragmentSize(fragmentSize < 0 ? 0 : fragmentSize)
            .setFragmenter(fragmenter == null ? Fragmenter.Span : fragmenter)
            .setHighLighterType(highLighterType == null ? HighLighterType.Unified : highLighterType);

        fieldList.add(field);
    }

    public class Field implements Serializable {
        private static final long serialVersionUID = -5619207734346716648L;

        private String name;

        private Fragmenter fragmenter;

        private int numberOfFragments;

        private int fragmentSize;

        private HighLighterType highLighterType;


        public String getName() {
            return name;
        }

        public Field setName(String name) {
            this.name = name;
            return this;
        }

        public Fragmenter getFragmenter() {
            return fragmenter;
        }

        public Field setFragmenter(Fragmenter fragmenter) {
            this.fragmenter = fragmenter;
            return this;
        }

        public int getNumberOfFragments() {
            return numberOfFragments;
        }

        public Field setNumberOfFragments(int numberOfFragments) {
            this.numberOfFragments = numberOfFragments;
            return this;
        }

        public int getFragmentSize() {
            return fragmentSize;
        }

        public Field setFragmentSize(int fragmentSize) {
            this.fragmentSize = fragmentSize;
            return this;
        }

        public HighLighterType getHighLighterType() {
            return highLighterType;
        }

        public Field setHighLighterType(HighLighterType highLighterType) {
            this.highLighterType = highLighterType;
            return this;
        }
    }

    public static enum HighLighterType {
        Unified, Plain, Fvh;
    }

    public static enum Fragmenter {
        Simple, Span;
    }

    public boolean isRequireFieldMatch() {
        return requireFieldMatch;
    }

    public void setRequireFieldMatch(boolean requireFieldMatch) {
        this.requireFieldMatch = requireFieldMatch;
    }

    public String getPreTags() {
        return preTags;
    }

    public void setPreTags(String preTags) {
        this.preTags = preTags;
    }

    public String getPostTags() {
        return postTags;
    }

    public void setPostTags(String postTags) {
        this.postTags = postTags;
    }

    public List<Field> getFieldList() {
        return fieldList;
    }
}
