package com.apoem.mmxx.eventtracking.infrastructure.enums;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: AvenueEnum </p>
 * <p>Description:  </p>
 * <p>Date: 2020/9/29 17:25 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Getter
public enum AvenueEnum {
    // ......
    IOS("iOS", "22", "iOS"),
    ANDROID("Android", "1", "Android"),
    H5("H5", "9115", "H5"),
    WMP("WMP", "996", "WMP");

    private final String name;
    private final String transValue;
    private final String desc;

    AvenueEnum(String name, String transValue, String desc) {
        this.name = name;
        this.transValue = transValue;
        this.desc = desc;
    }

    public static boolean isExist(String name) {
        return Arrays.stream(AvenueEnum.values())
                .anyMatch(o -> o.getName().equals(name));
    }

    public static String getDesc(String name) {
        Optional<AvenueEnum> first = Arrays.stream(AvenueEnum.values())
                .filter(o -> o.getName().equals(name))
                .findFirst();
        return first.isPresent() ? first.get().getDesc() : "";
    }

    public static String string() {

        return Arrays.stream(AvenueEnum.values()).map(o -> o.getDesc() + "=" + o.getName()).collect(Collectors.joining("|"));
    }

    public boolean maybe(String status) {
        return StringUtils.equalsIgnoreCase(this.getName(), status);
    }
}
