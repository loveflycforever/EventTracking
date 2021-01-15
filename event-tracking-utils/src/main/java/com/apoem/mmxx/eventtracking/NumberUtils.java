package com.apoem.mmxx.eventtracking;

import org.apache.commons.lang3.StringUtils;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: IntegerUtils </p>
 * <p>Description:  </p>
 * <p>Date: 2020/9/11 14:36 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
public class NumberUtils {
    public static int trimToZero(Integer integer) {
        if(integer == null) {
            return 0;
        }
        return integer;
    }

    public static long trimToZero(Long aLong) {
        if(aLong == null) {
            return 0;
        }
        return aLong;
    }

    public static long add(long... hn) {
        long result = 0;
        for (long l : hn) {
            result += l;
        }
        return result;
    }

    public static String toString(Long obj) {
        if(obj == null) {
            return StringUtils.EMPTY;
        }
        return obj.toString();
    }

    public static Long toLong(String paramOpTime) {

        long result = 0L;
        try {
            result = Long.parseLong(paramOpTime);
        } catch (Exception ignore) {
        }
        return result;
    }

    public static boolean compareBiggerThanZero(Integer houseBedroom) {
        if (houseBedroom == null) {
            return false;
        }
        return houseBedroom > 0;
    }
}
