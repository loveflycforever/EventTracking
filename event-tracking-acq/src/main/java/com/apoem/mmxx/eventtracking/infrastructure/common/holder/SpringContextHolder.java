package com.apoem.mmxx.eventtracking.infrastructure.common.holder;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: SpringContextHolder </p>
 * <p>Description: Spring上下文 </p>
 * <p>Date: 2020/7/15 17:18 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Component
@Lazy(false)
public class SpringContextHolder implements ApplicationContextAware {
 
    private static volatile ApplicationContext applicationContext = null;
 
    @Override
    @SuppressWarnings("NullableProblems")
    public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
        initApplicationContext(applicationContext);
    }

    private synchronized static void initApplicationContext(final ApplicationContext applicationContext) {
        if (Objects.isNull(SpringContextHolder.applicationContext)) {
            SpringContextHolder.applicationContext = applicationContext;
        }
    }
 
    public static ApplicationContext getApplicationContext() {
        assertApplicationContext();
        return applicationContext;
    }
 
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String beanName) {
        assertApplicationContext();
        return (T) applicationContext.getBean(beanName);
    }
 
    public static <T> T getBean(Class<T> requiredType) {
        assertApplicationContext();
        return applicationContext.getBean(requiredType);
    }
 
    private static void assertApplicationContext() {
        if (Objects.isNull(SpringContextHolder.applicationContext)) {
            throw new RuntimeException("ApplicationContext属性为null，请检查是否注入了SpringContextHolder!");
        }
    }
}