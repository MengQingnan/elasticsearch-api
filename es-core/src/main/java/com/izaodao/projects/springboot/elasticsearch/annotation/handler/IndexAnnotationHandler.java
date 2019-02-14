package com.izaodao.projects.springboot.elasticsearch.annotation.handler;

import com.alibaba.fastjson.JSON;
import com.izaodao.projects.springboot.elasticsearch.annotation.IndexSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @Auther: Mengqingnan
 * @Description: index 注解处理
 * @Date: 2018/10/17 4:49 PM
 * Copyright (c) 2018, zaodao All Rights Reserved.
 */
public class IndexAnnotationHandler {
    private final ResourcePatternResolver rpr = new PathMatchingResourcePatternResolver();
    private final RestTemplate restTemplate = new RestTemplate();
    private static final Logger LOGGER = LoggerFactory.getLogger(IndexAnnotationHandler.class);
    private String serviceName;
    private final SpringContextHolder springContextHolder;
    private enum ProfileEnum {
        DEV, TEST, PROD
    }

    public IndexAnnotationHandler(SpringContextHolder springContextHolder) {
        this.springContextHolder = springContextHolder;

        try {
            ResourceBundle resource = ResourceBundle.getBundle("generator-config");
            serviceName = resource.getString("project.service.package");
        } catch (Exception e) {
            LOGGER.error("PROPERTIES IS NOT EXIT", e);
        }
    }

    public void doIndexAnnotationHandler() throws IOException, ClassNotFoundException {
        Resource[] res = rpr.getResources(packageBasePath());

        List<Map<String, Object>> indexSettings = new ArrayList<>();

        for (int i = 0; i < res.length; i++) {
            //获取到文件类
            Class<?> clazz = Class.forName(formatClassName(res[i].getURL().getPath()));

            IndexSettings indexSettingsAnno = clazz.getAnnotation(IndexSettings.class);

            if (indexSettingsAnno != null) {
                Map<String, Object> map = new HashMap<>();

                map.put("indexSettingJson", indexSettingJson(indexSettingsAnno, clazz));
                map.put("indexMappingJson", indexMappingJson(clazz));

                indexSettings.add(map);
            }
        }

        if (!indexSettings.isEmpty()) {
            String indexSettingsJson =
                JSON.toJSONString(indexSettings).replace("[", "").replace("]", "");

            // 调用es 提供的rest服务
            try {
                restTemplate.getForEntity(obtainCreatIndexUrl(), String.class, indexSettingsJson);
            } catch (Exception e) {
                LOGGER.error("INDEX CREATE REST EXPECTION", e);
            }
        }
    }


    private ProfileEnum checkEnvironmentProfile() {
        ApplicationContext applicationContext = springContextHolder.getApplicationContext();

        Environment environment = applicationContext.getEnvironment();

        String[] activeProfiles = environment.getActiveProfiles();

        if (activeProfiles == null || activeProfiles.length == 0) {
            return ProfileEnum.DEV;
        } else if (activeProfiles[0].equals("test")) {
            return ProfileEnum.TEST;
        } else if (activeProfiles[0].equals("dev")) {
            return ProfileEnum.DEV;
        } else {
            return ProfileEnum.PROD;
        }
    }

    private String obtainCreatIndexUrl() {
        ProfileEnum profileEnum = checkEnvironmentProfile();

        StringBuilder stringBuilder = new StringBuilder();

        if (profileEnum == ProfileEnum.DEV) {
            stringBuilder.append("http://learn.izaodao.com:8082");
        } else if (profileEnum == ProfileEnum.TEST){
            stringBuilder.append("http://test.elasticsearch.com");
        } else {
            stringBuilder.append("http://prod.elasticsearch.com");
        }

        stringBuilder.append("/rest/index/creat?indexSetting={indexSettingsJson}");

        return stringBuilder.toString();
    }

    private String packageBasePath() {
        StringBuilder locationPattern = new StringBuilder();

        locationPattern.append("classpath*:");
        locationPattern.append("com/izaodao/projects/springboot/");
        locationPattern.append(StringUtils.isEmpty(serviceName)?"**":serviceName);
        locationPattern.append("/**/elasticsearch/*.class");

        return locationPattern.toString();
    }

    private String formatClassName(String className) {
        className = className.split("(classes/)|(!/)")[1];
        return className.replace("/", ".").replace(".class", "");
    }

    private String indexSettingJson(IndexSettings indexSettingsAnno, Class<?> clazz) {
        StringBuilder indexSetting = new StringBuilder();

        indexSetting.append("{\"");
        indexSetting.append("index\":\"");
        indexSetting.append(indexSettingsAnno.index().equals("") ? clazz.getSimpleName() : indexSettingsAnno.index());
        indexSetting.append("\",");
        indexSetting.append("\"type\":\"");
        indexSetting.append(indexSettingsAnno.type().equals("") ? clazz.getSimpleName() : indexSettingsAnno.type());
        indexSetting.append("\"}");

        return indexSetting.toString();
    }

    private String indexMappingJson(Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        StringBuilder indexSetting = new StringBuilder();
        if (fields.length > 0) {
            indexSetting.append("{");

            for (Field field : fields) {
                int modifter = field.getModifiers();

                if (!Modifier.isStatic(modifter)) {
                    indexSetting.append("\"");
                    indexSetting.append(field.getName());
                    indexSetting.append("\":");
                    indexSetting.append("\"");
                    indexSetting.append(field.getGenericType().getTypeName());
                    indexSetting.append("\",");
                }
            }
            indexSetting.replace(indexSetting.lastIndexOf(","), indexSetting.lastIndexOf(",") + 1, "");
            indexSetting.append("}");
        }

        return indexSetting.toString();
    }
}
