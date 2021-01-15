package com.apoem.mmxx.eventtracking.infrastructure.enums;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: FieldEnum </p>
 * <p>Description:  </p>
 * <p>Date: 2020/9/29 17:35 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Getter
public enum FieldEnum {
    // ......
    PVA("PVA", "pageView", "访问量"),
    UVA("UVA", "uniqueVisitor", "访客数"),
    COLA("COLA", "collected", "收藏数"),
    RPSA("RPSA", "reposted", "转发数");

    private final String name;
    private final String field;
    private final String desc;

    FieldEnum(String name, String field, String desc) {
        this.name = name;
        this.field = field;
        this.desc = desc;
    }

    public static FieldEnum find(String field) {
        return Arrays.stream(FieldEnum.values())
                .filter(o -> StringUtils.equalsIgnoreCase(o.getName(), field))
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }
}
