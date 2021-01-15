package com.apoem.mmxx.eventtracking.infrastructure.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Optional;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: RangeGlanceTimeLegendEnum </p>
 * <p>Description:  </p>
 * <p>Date: 2020/9/29 17:24 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Getter
public enum RangeGlanceTimeLegendEnum {
    // ......
    W7(7, 11, "07", 0),
    W11(11, 15, "11", 1),
    W15(15, 19, "15", 2),
    W19(19, 23, "19", 3),
    W23(23, 7, "23", 4);

    private final Integer upper;
    private final Integer lower;
    private final String label;
    private final Integer orderNumber;

    RangeGlanceTimeLegendEnum(Integer lower, Integer upper, String label, Integer orderNumber) {
        this.upper = upper;
        this.lower = lower;
        this.label = label;
        this.orderNumber = orderNumber;
    }

    public static RangeGlanceTimeLegendEnum findRange(Integer value) {
        Optional<RangeGlanceTimeLegendEnum> max = Arrays.stream(RangeGlanceTimeLegendEnum.values())
                .filter(o -> value >= o.getLower())
                .max(Comparator.comparingInt(RangeGlanceTimeLegendEnum::getLower));
        return max.orElse(W23);
    }
}
