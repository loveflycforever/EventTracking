package com.apoem.mmxx.eventtracking.infrastructure.po.dm;

import com.apoem.mmxx.eventtracking.infrastructure.po.support.BasicEntity;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: CustomerRangePvGlanceEntity </p>
 * <p>Description: 客源点击统计 </p>
 * <p>Date: 2020/9/1 8:06 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection="et_dm_customer_range_glance")
public class CustomerRangeGlanceEntity extends BasicEntity {

    /**
     * 日期，yyyyMMdd
     * 数据类型，昨日、七日，三十日
     */
    private Integer dateDay;
    private String periodType;

    // 维度定义

    private String city;
    private String customerId;
    private String houseType;
    private String rangeType;
    private String rangeLabel;

    // 统计数据

    private String amount;
}
