package com.apoem.mmxx.eventtracking;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: BaseConstants </p>
 * <p>Description: 常量定义 </p>
 * <p>Date: 2020/7/30 18:04 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
public abstract class BaseConstants {

    public static final String DATE_REGEX = "^([1-2]\\d{3}-([0]{0,1}[1-9]|[1][0-2])-([0]{0,1}[1-9]|[1-2]{1}\\d|[3][0-1]))\\s([0-1]{0,1}\\d|[2][0-3]):[0-5]{0,1}\\d:[0-5]{0,1}\\d(\\.[0-9]{1,3}){0,1}$";

    /**
     * DateTime格式化字符串 [2020-01-01 12:21:23.222]
     */
    public static final String YYYY_MM_DD_HH_MM_SS_SSS = "yyyy-MM-dd HH:mm:ss.SSS";

    /**
     * Date格式化字符串 [2020-01-01 12:21:23.222] -> [2020-01-01]
     */
    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    /**
     * Date格式化字符串 [2020-01-01 12:21:23.222] -> [20200101]
     */
    public static final String YYYYMMDD = "yyyyMMdd";
    /**
     * Date格式化字符串 [2020-01-01 12:21:23.222] -> [200101122156111]
     */
    public static final String YYMMDDHHMMSSSSS = "yyMMddHHmmssSSS";
    /**
     * Date格式化字符串 [2020-01-01 12:21:23.222] -> [20010112]
     */
    public static final String YYMMDDHH = "yyMMddHH";
    /**
     * Date格式化字符串 [2020-01-01 12:21:23.222] -> [202001]
     */
    public static final String YYYYMM = "yyyyMM";
    /**
     * Date格式化字符串 [2020-01-01 12:21:23.222] -> [2020]
     */
    public static final String YYYY = "yyyy";
    /**
     * Time格式化字符串 [2020-01-01 12:21:23.222] -> [12:21:23.222]
     */
    public static final String HH_MM_SS_SSS = "HH:mm:ss.SSS";

    public static final String UNDERSCORE = "_";

    public static final String BLANK = "";

    public static final String DOT = ".";

//    public static final String COMMA  = ",";

    public static final String COMMA_SPACE  = ", ";

    public static final String ZERO_STRING = "0";
    
    public static final String LAST_PAGE_KEY = "-1";

    /**
     * Long.MAX_VALUE = 9223372036854775807
     */
    public static final int MAX_LONG_DIGIT = 19;

    public static final BigDecimal BIG_DECIMAL_100 = new BigDecimal("100");


}
