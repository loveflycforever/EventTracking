package com.apoem.mmxx.eventtracking.infrastructure.po.dm;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: CrmTotalPriceDistributionEntity </p>
 * <p>Description: 客户总价浏览统计结果 </p>
 * <p>Date: 2020/9/14 8:31 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author somebody
 * @version v1.0
 */
@Data
@ToString(callSuper = true)
@Document(collection="et_dm_crm_total_price_distribution")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CrmTotalPriceDistributionEntity {

    private String city;

    private String uniqueId;

    private String houseType;

    private String totalPriceRange;

    private Integer viewCount;

}
