package com.apoem.mmxx.eventtracking;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.apache.commons.lang3.tuple.Pair;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalQueries;
import java.util.*;
import java.util.stream.Stream;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: DateUtils </p>
 * <p>Description: 日期格式化工具类 </p>
 * <p>Date: 2020/8/24 9:20 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
public final class DateUtils {

    public static final FastDateFormat YYYY_MM_DD_HH_MM_SS_SSS_FAST_FORMAT = FastDateFormat.getInstance(BaseConstants.YYYY_MM_DD_HH_MM_SS_SSS);
    public static final FastDateFormat YYYYMMDD_FAST_FORMAT = FastDateFormat.getInstance(BaseConstants.YYYYMMDD);
    public static final FastDateFormat YYMMDDHHMMSSSSS_FAST_FORMAT = FastDateFormat.getInstance(BaseConstants.YYMMDDHHMMSSSSS);
    public static final DateTimeFormatter YYYY_MM_DD_HH_MM_SS_SSS_FORMATTER = DateTimeFormatter.ofPattern(BaseConstants.YYYY_MM_DD_HH_MM_SS_SSS);
    public static final DateTimeFormatter YYYYMMDD_FORMATTER = DateTimeFormatter.ofPattern(BaseConstants.YYYYMMDD);
    public static final DateTimeFormatter YYMMDDHHMMSSSSS_FORMATTER = DateTimeFormatter.ofPattern(BaseConstants.YYMMDDHHMMSSSSS);
    public static final DateTimeFormatter YYYYMM_FORMATTER = DateTimeFormatter.ofPattern(BaseConstants.YYYYMM);
    public static final DateTimeFormatter YYYY_FORMATTER = DateTimeFormatter.ofPattern(BaseConstants.YYYY);


    private static final Random RANDOM = new Random(47);

    public static Integer numericalYyyyMmDd(LocalDateTime localDateTime) {
        return Integer.parseInt(localDateTime.format(YYYYMMDD_FORMATTER));
    }

    public static Integer numericalYyyyMmDd(Date date) {
        return Integer.parseInt(YYYYMMDD_FAST_FORMAT.format(date));
    }

    public static Long numericalYyMmDdHhMmSsSssToLong(LocalDateTime localDateTime) {
        return Long.parseLong(StringUtils.rightPad(localDateTime.format(YYMMDDHHMMSSSSS_FORMATTER), BaseConstants.MAX_LONG_DIGIT, BaseConstants.ZERO_STRING));
    }

    public static Long numericalYyMmDdHhMmSsSssToLongWithRandom(Date date) {
        return Long.parseLong(StringUtils.rightPad(YYMMDDHHMMSSSSS_FAST_FORMAT.format(date),
                BaseConstants.MAX_LONG_DIGIT,
                BaseConstants.ZERO_STRING)) + RANDOM.nextInt(9999);
    }

    public static Long numericalYyMmDdHhMmSsSssToLong(Date date) {
        return Long.parseLong(StringUtils.rightPad(YYMMDDHHMMSSSSS_FAST_FORMAT.format(date), BaseConstants.MAX_LONG_DIGIT, BaseConstants.ZERO_STRING));
    }

    public static String literalYyyyMmDd(LocalDateTime localDateTime) {
        return localDateTime.format(YYYYMMDD_FORMATTER);
    }

    public static String literalYyyyMmDdHhMmSsSss(LocalDateTime localDateTime) {
        return localDateTime.format(YYYY_MM_DD_HH_MM_SS_SSS_FORMATTER);
    }

    public static String localDateTimeString(Integer dateDay) {
        LocalDateTime localDateTime = YYYYMMDD_FORMATTER.parse(dateDay.toString(), TemporalQueries.localDate()).atStartOfDay();
        return YYYY_MM_DD_HH_MM_SS_SSS_FORMATTER.format(localDateTime);
    }

    public static LocalDateTime localDateTime(Integer dateDay) {
        return YYYYMMDD_FORMATTER.parse(dateDay.toString(), TemporalQueries.localDate()).atStartOfDay();
    }

    public static LocalDateTime localDateTime(String date) {
        return YYYY_MM_DD_HH_MM_SS_SSS_FORMATTER.parse(date, TemporalQueries.localDate()).atStartOfDay();
    }


    public static LocalDateTime localDateTime2(String dayDate) {
        return YYYYMMDD_FORMATTER.parse(dayDate, TemporalQueries.localDate()).atStartOfDay();
    }


    public static Date date(String date) {
        if (StringUtils.isBlank(date)) {
            return null;
        }
        try {
            return FastDateFormat.getInstance(BaseConstants.YYYY_MM_DD_HH_MM_SS_SSS).parse(date);
        } catch (ParseException e) {
            throw new RuntimeException();
        }
    }

    public static String dateString(Date date) {
        if (Objects.isNull(date)) {
            return BaseConstants.BLANK;
        }
        return YYYY_MM_DD_HH_MM_SS_SSS_FAST_FORMAT.format(date);
    }

    public static Pair<LocalDateTime, LocalDateTime> getDateDatePair111(LocalDateTime localDateTime, int distance) {
        LocalDateTime closeTime = LocalDateTime.of(localDateTime.plusDays(1).toLocalDate(), LocalTime.MIDNIGHT);
        LocalDateTime openTime = LocalDateTime.of(localDateTime.minusDays(distance).toLocalDate(), LocalTime.MIDNIGHT);
        return Pair.of(openTime, closeTime);
    }

    /**
     * @param localDateTime 2020-09-08T13:51:17.593
     * @param distance      7
     * @return (2020 - 09 - 02T00 : 00, 2020 - 09 - 08T00 : 00)
     */
    public static Pair<LocalDateTime, LocalDateTime> getDateDatePairClosed(LocalDateTime localDateTime, int distance) {
        LocalDateTime closeTime = LocalDateTime.of(localDateTime.toLocalDate(), LocalTime.MIDNIGHT);
        LocalDateTime openTime = LocalDateTime.of(localDateTime.minusDays(distance - 1).toLocalDate(), LocalTime.MIDNIGHT);
        return Pair.of(openTime, closeTime);
    }
//
//    public static List<LocalDateTime> sd(LocalDateTime start, LocalDateTime end) {
//        LocalDateTime closeTime = LocalDateTime.of(localDateTime.toLocalDate(), LocalTime.MIDNIGHT);
//        LocalDateTime openTime = LocalDateTime.of(localDateTime.minusDays(distance - 1).toLocalDate(), LocalTime.MIDNIGHT);
//        return Pair.of(openTime, closeTime);
//    }

    public static Pair<Date, Date> getDateDatePair(LocalDateTime localDateTime, int distance) {
        LocalDateTime closeTime = LocalDateTime.of(localDateTime.plusDays(1).toLocalDate(), LocalTime.MIDNIGHT);
        LocalDateTime openTime = LocalDateTime.of(localDateTime.minusDays(distance).toLocalDate(), LocalTime.MIDNIGHT);

        Date closeDate = Date.from(closeTime.atZone(ZoneId.systemDefault()).toInstant());
        Date openDate = Date.from(openTime.atZone(ZoneId.systemDefault()).toInstant());

        return Pair.of(openDate, closeDate);
    }

    public static long timeDis(Date endTime, Date startTime) {
        if (endTime == null || startTime == null) {
            return 0;
        }

        return endTime.getTime() - startTime.getTime();
    }

    public static Date trimToZero(Date endTime) {
        if (endTime == null) {
            return new Date(0L);
        }
        return endTime;
    }

    public static List<LocalDateTime> getBetweenDate(LocalDateTime start, LocalDateTime end) {
        List<LocalDateTime> list = new ArrayList<>();
        long distance = ChronoUnit.DAYS.between(start, end);
        if (distance < 0) {
            return list;
        }
        Stream.iterate(start, d -> d.plusDays(1)).limit(distance + 1).forEach(list::add);
        return list;
    }

}
