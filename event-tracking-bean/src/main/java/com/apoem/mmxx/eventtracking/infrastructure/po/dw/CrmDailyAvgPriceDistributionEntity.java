package com.apoem.mmxx.eventtracking.infrastructure.po.dw;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: CrmDailyAvgPriceDistributionEntity </p>
 * <p>Description: 客户浏览房源均价统计结果 </p>
 * <p>Date: 2020/9/14 9:10 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author somebody
 * @version v1.0
 */
@Data
@ToString(callSuper = true)
@Document(collection="et_dw_crm_180_avg_price_distribution")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CrmDailyAvgPriceDistributionEntity {

    private String city;

    private String uniqueId;

    private String houseType;

    private String avgPriceRange;

    private Date date;

    private Integer day;

    private Integer viewCount;

    private Integer mark;

}
