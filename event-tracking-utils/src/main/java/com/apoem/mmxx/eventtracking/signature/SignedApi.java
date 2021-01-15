package com.apoem.mmxx.eventtracking.signature;

import java.lang.annotation.*;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: SignedApi </p>
 * <p>Description: 标记需要验签的接口注解 </p>
 * <p>Date: 2020/7/14 16:02 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SignedApi {

    /**
     * 验签策略
     * 默认 HmacSha256
     * @return 验签策略
     */
    Strategies strategy() default Strategies.HMAC_SHA256;

    /**
     * 待校验参数下标
     * 默认第一个参数
     * @return 待校验参数下标
     */
    int parameterIndex() default 0;

    /**
     * 接口限制时间
     * > 0 访问限制
     * = 0 无限制
     * < 0 拒绝访问
     * @return 接口限制时间
     */
    long limitMillisecond() default 0;
}
