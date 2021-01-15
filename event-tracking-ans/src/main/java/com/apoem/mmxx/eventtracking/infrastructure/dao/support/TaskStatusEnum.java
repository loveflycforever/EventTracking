package com.apoem.mmxx.eventtracking.infrastructure.dao.support;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: TaskStatusEnum </p>
 * <p>Description:  </p>
 * <p>Date: 2020/9/29 17:33 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Getter
public enum TaskStatusEnum {
    // ......
    READY("READY", "准备"),
    RUNNING("RUNNING", "运行"),
    FINISH("FINISH", "完成");

    private final String name;
    private final String desc;

    TaskStatusEnum(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    public boolean maybe(String status) {
        return StringUtils.equalsIgnoreCase(this.getName(), status);
    }
}
