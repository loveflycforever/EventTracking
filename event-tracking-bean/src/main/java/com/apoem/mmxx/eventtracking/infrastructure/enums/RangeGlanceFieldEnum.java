package com.apoem.mmxx.eventtracking.infrastructure.enums;

import lombok.Getter;

import java.util.Arrays;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: RangeGlanceFieldEnum </p>
 * <p>Description:  </p>
 * <p>Date: 2020/9/29 17:24 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Getter
public enum RangeGlanceFieldEnum {
    // ......
    TIME("TIME", "时间"),
    PRICE("PRICE", "价格"),
    AREA("AREA", "面积");

    private final String name;
    private final String desc;

    RangeGlanceFieldEnum(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    public static boolean isExist(String name) {
        return Arrays.stream(RangeGlanceFieldEnum.values())
                .anyMatch(o -> o.getName().equals(name));
    }
}
