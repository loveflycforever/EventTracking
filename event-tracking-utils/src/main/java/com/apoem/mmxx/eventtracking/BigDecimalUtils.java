package com.apoem.mmxx.eventtracking;

import java.math.BigDecimal;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: BigDecimalUtils </p>
 * <p>Description:  </p>
 * <p>Date: 2020/9/15 11:03 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
public class BigDecimalUtils {

    public static BigDecimal trimToZero(BigDecimal bigDecimal) {
        if(bigDecimal == null) {
            return BigDecimal.ZERO;
        }
        return bigDecimal;
    }
}
