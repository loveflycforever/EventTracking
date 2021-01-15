package com.apoem.mmxx.eventtracking.infrastructure.dao.support;

import java.lang.annotation.*;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: ShardingKey </p>
 * <p>Description: 分表关键字注解 </p>
 * <p>Date: 2020/8/17 10:02 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TaskLabel {

    String name();

    String desc();

    int order();
}
