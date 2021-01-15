package com.apoem.mmxx.eventtracking.infrastructure.enums;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.stream.Collectors;

@Getter
public enum ActionTypeEnum {
    // ......
    WHOLE("WHOLE", null, ""),
    EMPTY("EMPTY", null, ""),
    VIS("VIS", EventTypeEnum.VIEW, "访问"),
    COL("COL", EventTypeEnum.CLICK, "收藏"),
    RPS("RPS", EventTypeEnum.CLICK, "分享");

    private final String name;
    private final String desc;
    private final EventTypeEnum eventType;

    ActionTypeEnum(String name, EventTypeEnum eventType, String desc) {
        this.name = name;
        this.eventType = eventType;
        this.desc = desc;
    }

    public static String getDesc(String name) {
        return Arrays.stream(ActionTypeEnum.values())
                .filter(o -> o.getName().equals(name))
                .findFirst()
                .orElse(EMPTY).getDesc();
    }

    /**
     * 配置不能为 WHOLE
     */
    public static boolean isConfig(String name) {
        return Arrays.stream(ActionTypeEnum.values())
                .filter(o -> !o.equals(WHOLE))
                .anyMatch(o -> o.getName().equals(name));
    }

    /**
     * 轨迹不能为 WHOLE || EMPTY
     */
    public static boolean isTrail(String name) {
        return Arrays.stream(ActionTypeEnum.values())
                .filter(o -> !o.equals(WHOLE) && !o.equals(EMPTY))
                .anyMatch(o -> o.getName().equals(name));
    }

    public static String string() {
        return Arrays.stream(ActionTypeEnum.values()).map(o -> o.getDesc() + "=" + o.getName()).collect(Collectors.joining("|"));
    }

    public static ActionTypeEnum find(String name) {
        return Arrays.stream(ActionTypeEnum.values())
                .filter(o -> o.getName().equalsIgnoreCase(name)).findFirst().orElse(EMPTY);
    }

    public boolean maybe(String status) {
        return StringUtils.equalsIgnoreCase(this.getName(), status);
    }
}