package com.apoem.mmxx.eventtracking.infrastructure.po.dm;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: CrmClientIntentionDailyEntity </p>
 * <p>Description: 用户意向 </p>
 * <p>Date: 2020/9/8 14:35 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author somebody
 * @version v1.0
 */
@Data
@ToString(callSuper = true)
@Document(collection="et_dm_crm_client_intention")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CrmClientIntentionEntity {

    private Integer updateDay;

    private Date updateDate;

    private String city;

    private String uniqueId;

    private String houseType;

    private Integer houseArea;

    private Integer houseTotalPrice;

    private Integer houseAveragePrice;

    private Integer houseBedroom;

}
