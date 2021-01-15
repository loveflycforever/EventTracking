package com.apoem.mmxx.eventtracking.interfaces.facade;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: CrmController </p>
 * <p>Description: CRM控制器 </p>
 * <p>Date: 2020/9/15 9:17 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author somebody
 * @version v1.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CachedData {
    /**
     * 时间单位
     * @return 时间单位
     */
    TimeUnit unit() default TimeUnit.SECONDS;

    /**
     * 持续时间
     * @return 持续时间
     */
    int duration() default -1;

    /**
     * 参数下标
     * @return 默认第一个参数，
     */
    int parameterIndex() default 0;
}
