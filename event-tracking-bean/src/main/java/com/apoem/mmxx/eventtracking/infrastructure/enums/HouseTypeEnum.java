package com.apoem.mmxx.eventtracking.infrastructure.enums;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: HouseTypeEnum </p>
 * <p>Description:  </p>
 * <p>Date: 2020/9/29 17:18 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Getter
public enum HouseTypeEnum {
    // ......
    EMPTY("EMPTY", ""),
    WHOLE("WHOLE", ""),
    NWHS("NWHS", "新房"),
    SHHS("SHHS", "二手房"),
    RTHS("RTHS", "租房");

    private final String name;
    private final String desc;

    HouseTypeEnum(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    public boolean maybe(String name) {
        return StringUtils.equalsIgnoreCase(this.getName(), name);
    }

    public static String valueX(String name) {
        String trimName = StringUtils.trimToEmpty(name);
        return Arrays.stream(HouseTypeEnum.values())
                .map(HouseTypeEnum::getName)
                .filter(o -> StringUtils.equalsIgnoreCase(o, trimName))
                .findFirst()
                .orElse(trimName);
    }

    public String getName() {
        return name;
    }

    public static HouseTypeEnum find(String name) {
        return Arrays.stream(HouseTypeEnum.values())
                .filter(o -> o.getName().equals(name))
                .findFirst()
                .orElse(EMPTY);
    }

    public static String[] valuesN() {
        return Arrays.stream(HouseTypeEnum.values())
                .filter(o -> !o.equals(EMPTY) && !o.equals(WHOLE))
                .map(HouseTypeEnum::getName).toArray(String[]::new);
    }
}
