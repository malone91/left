package com.melo.gatewaysdk.application;

import com.melo.gatewaysdk.annotation.ApiProducerClazz;
import com.melo.gatewaysdk.annotation.ApiProducerMethod;
import com.melo.gatewaysdk.config.GatewaySdkServerProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class GatewaySdkApplication implements BeanPostProcessor {

    private Logger logger = LoggerFactory.getLogger(GatewaySdkApplication.class);

    //统计耗时
    Map<String, Long> costMap = new HashMap<>();

    private GatewaySdkServerProperties properties;

    public GatewaySdkApplication(GatewaySdkServerProperties properties) {
        this.properties = properties;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        costMap.put(beanName, System.currentTimeMillis());
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        loggerCost(beanName);
        ApiProducerClazz clazz = bean.getClass().getAnnotation(ApiProducerClazz.class);
        if (clazz == null) {
            return bean;
        }
        logger.info("system id {} name {} interface name {} version {} ", properties.getSystemId(), properties.getSystemName(),
                clazz.interfaceName(), clazz.interfaceVersion());
        Method[] methods = bean.getClass().getMethods();
        for (Method method : methods) {
            ApiProducerMethod methodAnnotation = method.getAnnotation(ApiProducerMethod.class);
            if (methodAnnotation == null) {
                continue;
            }
            Class<?>[] parameterTypes = method.getParameterTypes();
            StringBuilder sb = new StringBuilder();
            for (Class<?> cl : parameterTypes) {
                sb.append(cl.getName()).append(",");
            }
            String s = sb.toString();
            s = s.substring(0, s.lastIndexOf(","));
            logger.info("system id {} name {} interface name {} version {} method name {} type {} uri {} commandType {} auth {}", properties.getSystemId(), properties.getSystemName(),
                    clazz.interfaceName(), clazz.interfaceVersion(), methodAnnotation.methodName(), s, methodAnnotation.uri(), methodAnnotation.httpCommandType(), methodAnnotation.auth());
        }
        return bean;
    }

    private void loggerCost(String beanName) {
        Long start = costMap.getOrDefault(beanName, null);
        if (start == null) {
            logger.error("执行顺序有问题 {}", beanName);
            return;
        }

        long cost = System.currentTimeMillis() - start;
        logger.info("bean {} cost {} millis", beanName, cost);
    }
}
