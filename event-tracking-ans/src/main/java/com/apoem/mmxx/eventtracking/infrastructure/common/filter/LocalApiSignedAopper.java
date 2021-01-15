package com.apoem.mmxx.eventtracking.infrastructure.common.filter;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: OpenApiSignedAopper </p>
 * <p>Description: 验签切面 </p>
 * <p>Date: 2020/7/22 9:27 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Aspect
@Component
@Slf4j
public class LocalApiSignedAopper {

    @Value("${spring.profiles.active}")
    private String profile;

    private static final String LOCAL_PROFILE_STRING = "dev_development_test";

    @Pointcut(value = "@annotation(com.apoem.mmxx.eventtracking.interfaces.facade.LocalApi)")
    public void LocalApiPointCut() {
    }

    @Before("LocalApiPointCut()")
    public void doBefore(JoinPoint point) {
        if (!LOCAL_PROFILE_STRING.contains(profile)) {
            throw new RuntimeException("Not a Local Api !!!");
        }
    }

}
