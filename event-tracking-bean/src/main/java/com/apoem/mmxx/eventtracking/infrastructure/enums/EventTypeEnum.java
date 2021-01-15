package com.apoem.mmxx.eventtracking.infrastructure.enums;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
public enum EventTypeEnum {
    // ......
    VIEW("VIEW", "page", "打开页面"),
    CLICK("CLICK", "click", "页面点击");

    private final String name;
    private final String transValue;
    private final String desc;

    EventTypeEnum(String name, String transValue, String desc) {
        this.name = name;
        this.transValue = transValue;
        this.desc = desc;
    }

    public static boolean isExist(String name) {
        return Arrays.stream(EventTypeEnum.values())
                .anyMatch(o -> o.getName().equals(name));
    }

    public static String getDesc(String name) {
        Optional<EventTypeEnum> first = Arrays.stream(EventTypeEnum.values())
                .filter(o -> o.getName().equals(name))
                .findFirst();
        return first.isPresent() ? first.get().getDesc() : "";
    }

    public static String valueX(String name) {
        String trimName = StringUtils.trimToEmpty(name);
        return Arrays.stream(EventTypeEnum.values())
                .map(EventTypeEnum::getName)
                .filter(o -> StringUtils.equalsIgnoreCase(o, trimName))
                .findFirst()
                .orElse(trimName);
    }

    public static String string() {
        return Arrays.stream(EventTypeEnum.values()).map(o -> o.getDesc() + "=" + o.getName()).collect(Collectors.joining("|"));
    }

    public boolean maybe(String name) {
        return StringUtils.equalsIgnoreCase(this.getName(), name);
    }
}