package com.apoem.mmxx.eventtracking.infrastructure.po.ro.support;

import java.lang.annotation.*;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: MrStoreStatsRo </p>
 * <p>Description: 门店MR临时结果 </p>
 * <p>Date: 2020/8/21 9:44 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MrSignedKey {

    FieldNamingStrategy fieldNamingStrategy() default FieldNamingStrategy.SNAKE;
}
