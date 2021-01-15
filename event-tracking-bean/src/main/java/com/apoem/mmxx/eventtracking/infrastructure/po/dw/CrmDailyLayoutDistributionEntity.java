package com.apoem.mmxx.eventtracking.infrastructure.po.dw;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: CrmDailyLayoutDistributionEntity </p>
 * <p>Description: 客户每日户型浏览统计 </p>
 * <p>Date: 2020/9/14 15:17 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author somebody
 * @version v1.0
 */
@Data
@ToString(callSuper = true)
@Document(collection="et_dw_crm_180_layout_distribution")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CrmDailyLayoutDistributionEntity {

    private String city;

    private String uniqueId;

    private String houseType;

    private String layoutRange;

    private Date date;

    private Integer day;

    private Integer viewCount;

    private Integer mark;

}
