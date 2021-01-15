package com.apoem.mmxx.eventtracking.infrastructure.po.dw;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: CrmClientIntentionEntity </p>
 * <p>Description: 用户意向 </p>
 * <p>Date: 2020/9/8 14:35 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author somebody
 * @version v1.0
 */
@Data
@ToString(callSuper = true)
@Document(collection="et_dw_crm_180_client_intention")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CrmDailyClientIntentionEntity {

    private String city;

    private String uniqueId;

    private String houseType;

    private Date date;

    private Integer day;

    private Long houseArea;

    private Long houseTotalPrice;

    private Long houseAveragePrice;

    private Long houseBedroom;

    private Integer houseCount;

}
