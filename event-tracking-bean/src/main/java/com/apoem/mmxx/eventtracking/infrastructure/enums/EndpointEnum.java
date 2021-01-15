package com.apoem.mmxx.eventtracking.infrastructure.enums;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: EndpointEnum </p>
 * <p>Description:  </p>
 * <p>Date: 2020/9/29 17:11 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Getter
public enum EndpointEnum {
    // ......
    BUSINESS("Business", "B端"),
    CONSUMER("Consumer", "C端");

    private final String name;
    private final String desc;

    EndpointEnum(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }


    public static String getDesc(String name) {
        return Arrays.stream(EndpointEnum.values())
                .map(EndpointEnum::getName)
                .filter(o -> o.equalsIgnoreCase(name))
                .findFirst().orElse(StringUtils.EMPTY);
    }


    public static boolean isConfig(String name) {
        return Arrays.stream(EndpointEnum.values())
                .anyMatch(o -> o.getName().equals(name));
    }

    public static String string() {
        return Arrays.stream(EndpointEnum.values()).map(o -> o.getDesc() + "=" + o.getName()).collect(Collectors.joining("|"));
    }


    public boolean maybe(String endpoint) {
        return StringUtils.equalsIgnoreCase(this.getName(), endpoint);
    }
}
