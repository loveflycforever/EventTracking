package com.apoem.mmxx.eventtracking.infrastructure.po.dw;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: CrmDaliyBlockEntity </p>
 * <p>Description: 客户意向物业类型 </p>
 * <p>Date: 2020/9/18 14:36 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author somebody
 * @version v1.0
 */
@Data
@ToString(callSuper = true)
@Document(collection="et_dw_crm_180_block")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CrmDaliyBlockEntity {

    private String city;

    private String uniqueId;

    private String houseType;

    private Date date;

    private Integer day;

    private String blockId;

    private String blockName;

    private Integer viewCount;

}
