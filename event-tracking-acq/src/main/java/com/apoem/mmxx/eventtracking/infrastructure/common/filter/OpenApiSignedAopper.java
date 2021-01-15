package com.apoem.mmxx.eventtracking.infrastructure.common.filter;


import com.apoem.mmxx.eventtracking.signature.MillisecondClock;
import com.apoem.mmxx.eventtracking.signature.SignedApi;
import com.apoem.mmxx.eventtracking.signature.Strategies;
import com.apoem.mmxx.eventtracking.signature.VerifierCollator;
import com.apoem.mmxx.eventtracking.infrastructure.common.holder.CurrentRequestHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.map.CaseInsensitiveMap;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

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
public class OpenApiSignedAopper implements InitializingBean {

    @Value("#{${open_api_signed_aopper.enable}}")
    private boolean enable;

    @Value("${spring.profiles.active}")
    private String profile;

    private static final Map<String, Long> SYSTEM_LIMITED = new ConcurrentHashMap<>();

    /**
     * 使用Key值不区分大小写的Map处理
     */
    @Value("#{${open_api_signed_aopper.passport_seal}}")
    private CaseInsensitiveMap<String, String> passportSeal;

    @Pointcut(value = "@annotation(com.apoem.mmxx.eventtracking.signature.SignedApi)")
    public void signedApiPointCut() {
    }

    @Before("signedApiPointCut()")
    public void doBefore(JoinPoint point) {

        if (enable) {
            assertSignature(point);
            // 断言必填请求头
            assertHeader();

            tryVerify(point);

            MethodSignature signature = (MethodSignature) point.getSignature();
            SignedApi signedApi = signature.getMethod().getAnnotation(SignedApi.class);
            Strategies strategy = signedApi.strategy();
            Object preVerifyArg = point.getArgs()[getParameterIndex(point)];
            String viaTimestamp = CurrentRequestHolder.getHeader("viaTimestamp");
            String signWrinkles = CurrentRequestHolder.getHeader("signWrinkles");
            String sourceSystem = CurrentRequestHolder.getHeader("sourceSystem");

            // 校验
            VerifierCollator.builder()
                    .preVerifyArg(preVerifyArg)
                    .sourceSystem(sourceSystem)
                    .viaTimestamp(viaTimestamp)
                    .signWrinkles(signWrinkles)
                    .passportSeal(passportSeal.get(sourceSystem))
                    .strategy(strategy)
                    .debugModeOn(StringUtils.equalsIgnoreCase("dev", profile))
                    .build().validate();

            completeVerify(point);
        }
    }

    /**
     * 尝试校验
     * @param point 切点
     */
    private void tryVerify(JoinPoint point) {
        MethodSignature signature = (MethodSignature) point.getSignature();
        SignedApi signedApi = signature.getMethod().getAnnotation(SignedApi.class);

        // 限制
        if (signedApi.limitMillisecond() < 0) {
            throw new RuntimeException("关闭访问");
        }

        // 无限制
        if (signedApi.limitMillisecond() == 0) {
            return;
        }

        // 受限
        if (signedApi.limitMillisecond() > 0) {
            String sourceSystem = CurrentRequestHolder.getHeader("sourceSystem");
            Long lastVisitMillis = SYSTEM_LIMITED.get(sourceSystem);
            if (Objects.nonNull(lastVisitMillis)) {
                long currentMillis = MillisecondClock.tik();
                if (currentMillis < (lastVisitMillis + signedApi.limitMillisecond())) {
                    throw new RuntimeException("拒绝访问");
                }
            }
        }
    }

    /**
     * 完成校验
     * @param point 切点
     */
    private void completeVerify(JoinPoint point) {
        MethodSignature signature = (MethodSignature) point.getSignature();
        SignedApi signedApi = signature.getMethod().getAnnotation(SignedApi.class);

        if (signedApi.limitMillisecond() < 0) {
            return;
        }

        if (signedApi.limitMillisecond() == 0) {
            return;
        }

        if (signedApi.limitMillisecond() > 0) {
            String sourceSystem = CurrentRequestHolder.getHeader("sourceSystem");
            SYSTEM_LIMITED.put(sourceSystem, MillisecondClock.tik());
        }
    }

    /**
     * 校验请求头
     * viaTimestamp 请求时间 必填
     * signWrinkles 签名内容 必填
     * sourceSystem 系统来源 必填
     */
    private void assertHeader() {
        String viaTimestamp = CurrentRequestHolder.getHeader("viaTimestamp");
        String signWrinkles = CurrentRequestHolder.getHeader("signWrinkles");
        String sourceSystem = CurrentRequestHolder.getHeader("sourceSystem");

        Assert.hasText(sourceSystem, "Header[sourceSystem] is required");
        Assert.hasText(viaTimestamp, "Header[viaTimestamp] is required");
        Assert.hasText(signWrinkles, "Header[signWrinkles] is required");

        // viaTimestamp 是否符合 yyyy-MM-dd HH:mm:ss.SSS
        // sourceSystem 是否有效 in passportSeal
    }

    /**
     * 获取参数下标
     *
     * @param point 切点
     * @return 参数下标
     */
    private int getParameterIndex(JoinPoint point) {
        MethodSignature signature = (MethodSignature) point.getSignature();
        SignedApi signedApi = signature.getMethod().getAnnotation(SignedApi.class);
        int parameterIndex = signedApi.parameterIndex();
        int argsLength = point.getArgs().length;

        if (parameterIndex >= 0 && parameterIndex < argsLength) {
            return parameterIndex;
        }

        // 如果定义的待校验参数下标不符合规则，尝试使用带有@RequestBody注解的参数
        Annotation[][] annotations = signature.getMethod().getParameterAnnotations();
        checkOut:
        for (Annotation[] parameterAnnotation : annotations) {
            for (Annotation annotation : parameterAnnotation) {
                if (annotation.annotationType().equals(RequestBody.class)) {
                    parameterIndex = ArrayUtils.indexOf(annotations, parameterAnnotation);
                    break checkOut;
                }
            }
        }

        if (parameterIndex >= 0 && parameterIndex < argsLength) {
            return parameterIndex;
        }

        throw new RuntimeException("参数异常");
    }

    /**
     * 检验切点
     *
     * @param point 切点
     */
    private void assertSignature(JoinPoint point) {
        if (Objects.nonNull(point) && !(point.getSignature() instanceof MethodSignature)) {
            throw new RuntimeException("切点错");
        }
    }

    @Override
    public void afterPropertiesSet() {
        if (enable) {
            log.info(this.getClass().getCanonicalName() + "已启用" + MillisecondClock.strik());
        }
    }


}
