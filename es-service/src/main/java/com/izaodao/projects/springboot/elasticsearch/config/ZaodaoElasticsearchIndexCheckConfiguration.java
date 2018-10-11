package com.izaodao.projects.springboot.elasticsearch.config;

import com.izaodao.projects.springboot.elasticsearch.client.ZaodaoRestHighLevelClient;
import com.izaodao.projects.springboot.elasticsearch.domain.EsOperParamters;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Configuration;

/**
 * @Auther: Mengqingnan
 * @Description:
 * @Date: 2018/9/29 下午4:17
 * Copyright (c) 2018, zaodao All Rights Reserved.
 */
@Aspect
@Configuration
@ConditionalOnBean(ZaodaoRestHighLevelClient.class)
public class ZaodaoElasticsearchIndexCheckConfiguration {

    private final ZaodaoRestHighLevelClient restHighLevelClient;


    public ZaodaoElasticsearchIndexCheckConfiguration(ZaodaoRestHighLevelClient restHighLevelClient) {
        this.restHighLevelClient = restHighLevelClient;
    }

    /**
     * 定义一个切入点
     */
    @Pointcut("execution(* com.izaodao.projects.springboot.elasticsearch.service..*.*(..))")
    public void excudeService() {}


    @Around("excudeService()")
    public Object twiceAsOld(ProceedingJoinPoint thisJoinPoint) throws Throwable {
        EsOperParamters esOperParamters = getFieldsName(thisJoinPoint);

        if (esOperParamters == null || esOperParamters.getIndex() == null) {
            throw new NullPointerException("EsOperParamters index is empty");
        }

        String mapping = "{\n" +
            "  \"tweet\": {\n" +
            "    \"properties\": {\n" +
            "      \"message\": {\n" +
            "        \"type\": \"text\"\n" +
            "      }\n" +
            "    }\n" +
            "  }\n" +
            "}";

        restHighLevelClient.creatIndex(esOperParamters.getIndex(), esOperParamters.getType(), mapping);

        return thisJoinPoint.proceed();

    }


    private EsOperParamters getFieldsName(JoinPoint joinPoint) throws ClassNotFoundException, NoSuchMethodException {
//        // 目标类名称
//        String classType = joinPoint.getTarget().getClass().getName();
        // 目标方法
        String methodName = joinPoint.getSignature().getName();
        // 目标方法的参数具体值
        Object[] args = joinPoint.getArgs();

        for (Object obj : args) {
            if (obj.getClass().getSimpleName().equals("EsOperParamters")) {
                return (EsOperParamters) obj;
            }
        }

        // 根据具体的参数值，定义参数class 数组
//        Class<?>[] classes = new Class[args.length];
//        // 此处我想要 esOperParamters 这个参数
//        for (int k = 0; k < args.length; k++) {
//            if (!args[k].getClass().isPrimitive()) {
//                //获取的是封装类型而不是基础类型
//                String result = args[k].getClass().getName();
//                Class s = map.get(result);
//                classes[k] = s == null ? args[k].getClass() : s;
//            }
//        }
//        ParameterNameDiscoverer pnd = new DefaultParameterNameDiscoverer();
//        //获取指定的方法，第二个参数可以不传，但是为了防止有重载的现象，还是需要传入参数的类型
//        Method method = Class.forName(classType).getMethod(methodName, classes);
//        String[] parameterNames = pnd.getParameterNames(method);
        return null;
    }


//    private HashMap<String, Class> map = new HashMap<String, Class>() {
//        {
//            put("java.lang.Integer", int.class);
//            put("java.lang.Double", double.class);
//            put("java.lang.Float", float.class);
//            put("java.lang.Long", long.class);
//            put("java.lang.Short", short.class);
//            put("java.lang.Boolean", boolean.class);
//            put("java.lang.Char", char.class);
//        }
//    };
}
