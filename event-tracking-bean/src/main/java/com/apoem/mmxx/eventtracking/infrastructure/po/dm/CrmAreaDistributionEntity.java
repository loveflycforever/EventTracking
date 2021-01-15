package com.apoem.mmxx.eventtracking.infrastructure.po.dm;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: CrmAreaDistributionEntity </p>
 * <p>Description: 客户面积浏览统计结果 </p>
 * <p>Date: 2020/9/14 9:58 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author somebody
 * @version v1.0
 */
@Data
@ToString(callSuper = true)
@Document(collection="et_dm_crm_area_distribution")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CrmAreaDistributionEntity {

    private String city;

    private String uniqueId;

    private String houseType;

    private String areaRange;

    private Integer viewCount;

}
