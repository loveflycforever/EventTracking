package com.apoem.mmxx.eventtracking.infrastructure.po.dw;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: CrmDailyCommunityEntity </p>
 * <p>Description: 客户意向板块 </p>
 * <p>Date: 2020/9/10 15:50 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author somebody
 * @version v1.0
 */
@Data
@ToString(callSuper = true)
@Document(collection="et_dw_crm_180_plate_community")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CrmDailyPlateCommunityEntity {

    private String city;

    private String uniqueId;

    private String houseType;

    private Date date;

    private Integer day;

    private String plateId;

    private String plateName;

    private String communityId;

    private String communityName;

    private Integer viewCount;

}
