package com.apoem.mmxx.eventtracking.infrastructure.enums;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: PeriodTypeEnum </p>
 * <p>Description:  </p>
 * <p>Date: 2020/9/29 17:35 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Getter
public enum PeriodTypeEnum {
    // ......
    DAY1("DAY1", 1, "1 Day"),
    DAY7("DAY7", 7, "7 Day"),
    DAY30("DAY30", 30, "30 Day"),
    WHOLE("WHOLE", 365, "WHOLE");

    private final String name;
    private final int number;
    private final String desc;

    PeriodTypeEnum(String name, int number, String desc) {
        this.name = name;
        this.number = number;
        this.desc = desc;
    }

    public static PeriodTypeEnum find(String value) {
        return Arrays.stream(PeriodTypeEnum.values())
                .filter(o -> StringUtils.equalsIgnoreCase(o.getName(), value))
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }
}
