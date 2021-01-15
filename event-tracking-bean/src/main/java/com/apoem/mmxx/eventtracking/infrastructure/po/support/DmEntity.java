package com.apoem.mmxx.eventtracking.infrastructure.po.support;

import lombok.*;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: DmEntity </p>
 * <p>Description: 统计基本字段 </p>
 * <p>Date: 2020/12/2 10:39 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class DmEntity extends BasicEntity {

    /**
     * 日期，yyyyMMdd
     * 数据类型，昨日、七日，三十日
     */
    private Integer dateDay;
    private String periodType;

    // 基本统计数据

    private Long pageView;
    private Long uniqueVisitor;
}
