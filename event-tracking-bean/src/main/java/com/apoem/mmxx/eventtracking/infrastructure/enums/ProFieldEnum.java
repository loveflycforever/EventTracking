package com.apoem.mmxx.eventtracking.infrastructure.enums;

import lombok.Getter;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: ProFieldEnum </p>
 * <p>Description:  </p>
 * <p>Date: 2020/9/29 17:35 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Getter
public enum ProFieldEnum {
    // ......
    DATE_DAY("date_day", ""),
    PERIOD_TYPE("period_type", ""),
    HOUSE_TYPE("house_type", ""),
    ACTION_TYPE("action_type", ""),
    INPUT_TYPE("input_type", ""),
    ACTION_DEFINITION("action_definition", ""),
    MODULE_NAME("module_name", ""),
    EVENT_TYPE("event_type", "");

    private final String name;
    private final String desc;

    ProFieldEnum(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }
}
