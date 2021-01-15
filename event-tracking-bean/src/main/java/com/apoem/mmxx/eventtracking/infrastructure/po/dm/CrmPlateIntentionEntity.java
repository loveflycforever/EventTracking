package com.apoem.mmxx.eventtracking.infrastructure.po.dm;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: CrmPlateIntentionEntity </p>
 * <p>Description: 客户板块意向结果 </p>
 * <p>Date: 2020/9/17 14:30 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author somebody
 * @version v1.0
 */
@Data
@ToString(callSuper = true)
@Document(collection="et_dm_crm_plate_intention")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CrmPlateIntentionEntity {

    private Integer updateDay;

    private Date updateDate;

    private String city;

    private String uniqueId;

    private String houseType;

    private String plateName;

    private Integer viewCount;

}
