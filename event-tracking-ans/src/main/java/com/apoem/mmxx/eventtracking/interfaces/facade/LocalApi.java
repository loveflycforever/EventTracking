package com.apoem.mmxx.eventtracking.interfaces.facade;

import java.lang.annotation.*;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: LocalTest </p>
 * <p>Description: 分表注解 </p>
 * <p>Date: 2020/8/17 10:02 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LocalApi {
}
