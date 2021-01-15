package com.apoem.mmxx.eventtracking.infrastructure.dao.support;

import java.lang.annotation.*;
import java.time.temporal.ChronoUnit;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: Sharding </p>
 * <p>Description: 分表注解 </p>
 * <p>Date: 2020/8/17 10:02 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Sharding {

    /**
     * 时间策略
     * @return 单元
     */
    ChronoUnit unit() default ChronoUnit.DAYS;

    Affix fix() default Affix.SUFFIX;
}
