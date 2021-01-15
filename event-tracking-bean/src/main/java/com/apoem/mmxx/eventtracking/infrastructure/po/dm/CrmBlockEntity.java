package com.apoem.mmxx.eventtracking.infrastructure.po.dm;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: CrmBlockEntity </p>
 * <p>Description: 客户物业类型访问统计结果 </p>
 * <p>Date: 2020/9/18 15:24 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author somebody
 * @version v1.0
 */
@Data
@ToString(callSuper = true)
@Document(collection="et_dm_crm_block")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CrmBlockEntity {

    private Date updateDate;

    private String city;

    private String uniqueId;

    private String houseType;

    private String blockId;

    private String blockName;

    private Integer viewCount;

}
