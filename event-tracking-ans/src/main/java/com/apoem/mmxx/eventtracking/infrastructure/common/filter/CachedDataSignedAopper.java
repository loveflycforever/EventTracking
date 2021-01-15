package com.apoem.mmxx.eventtracking.infrastructure.common.filter;


import com.apoem.mmxx.eventtracking.interfaces.facade.CachedData;
import com.apoem.mmxx.eventtracking.signature.MillisecondClock;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

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
public class CachedDataSignedAopper implements InitializingBean {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Value("#{${cached_data_signed_aopper.enable}}")
    private boolean enable;

    @Value("${spring.profiles.active}")
    private String profile;

    @Pointcut(value = "@annotation(com.apoem.mmxx.eventtracking.interfaces.facade.CachedData)")
    public void cachedDataPointCut() {
    }

    @Around("cachedDataPointCut()")
    public Object doAround(ProceedingJoinPoint point) throws Throwable {

        if (enable) {
            MethodSignature signature = (MethodSignature) point.getSignature();
            CachedData cachedData = signature.getMethod().getAnnotation(CachedData.class);
            int duration = cachedData.duration();
            TimeUnit timeUnit = cachedData.unit();
            Object preVerifyArg = point.getArgs()[getParameterIndex(point)];

            String key = timeUnit.name() + "_" + duration + "_" +preVerifyArg.toString();

            Object o = null;
            try {
                o = redisTemplate.opsForValue().get(key);
            } catch (Exception e) {
                log.error("redis search unavailable");
            }

            if(Objects.nonNull(o)) {
                log.info("result from cache");
                return o;
            }

            Object proceed = point.proceed();

            try {
                redisTemplate.opsForValue().set(key, proceed, duration, timeUnit);
            } catch (Exception e) {
                e.printStackTrace();
                log.error("redis save unavailable");
            }
            return proceed;
        }

        return point.proceed();
    }

    /**
     * 获取参数下标
     *
     * @param point 切点
     * @return 参数下标
     */
    private int getParameterIndex(JoinPoint point) {
        MethodSignature signature = (MethodSignature) point.getSignature();
        CachedData signedApi = signature.getMethod().getAnnotation(CachedData.class);
        int parameterIndex = signedApi.parameterIndex();
        int argsLength = point.getArgs().length;

        if (parameterIndex >= 0 && parameterIndex < argsLength) {
            return parameterIndex;
        }

        throw new RuntimeException("参数下标异常");
    }

    @Override
    public void afterPropertiesSet() {
        if (enable) {
            log.info(this.getClass().getCanonicalName() + "已启用" + MillisecondClock.strik());
        }
    }
}
