package com.apoem.mmxx.eventtracking.infrastructure.po.dm;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: CrmPlateEntity </p>
 * <p>Description: 客户板块访问统计结果 </p>
 * <p>Date: 2020/9/11 14:35 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author somebody
 * @version v1.0
 */
@Data
@ToString(callSuper = true)
@Document(collection="et_dm_crm_plate")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CrmPlateEntity {

    private Date updateDate;

    private String city;

    private String uniqueId;

    private String houseType;

    private String plateId;

    private String plateName;

    private Integer viewCount;

}
