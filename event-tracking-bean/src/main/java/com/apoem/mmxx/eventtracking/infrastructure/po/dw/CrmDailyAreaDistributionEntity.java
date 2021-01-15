package com.apoem.mmxx.eventtracking.infrastructure.po.dw;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: CrmDailyAreaDistributionEntity </p>
 * <p>Description: 客户浏览房源面积统计结果 </p>
 * <p>Date: 2020/9/14 9:33 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author somebody
 * @version v1.0
 */
@Data
@ToString(callSuper = true)
@Document(collection="et_dw_crm_180_area_distribution")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CrmDailyAreaDistributionEntity {

    private String city;

    private String uniqueId;

    private String houseType;

    private String areaRange;

    private Date date;

    private Integer day;

    private Integer viewCount;

    private Integer mark;

}
