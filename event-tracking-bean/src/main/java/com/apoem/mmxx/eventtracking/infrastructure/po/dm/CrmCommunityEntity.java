package com.apoem.mmxx.eventtracking.infrastructure.po.dm;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: CrmCommunityEntity </p>
 * <p>Description: 客户小区访问统计结果 </p>
 * <p>Date: 2020/9/11 14:38 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author somebody
 * @version v1.0
 */
@Data
@ToString(callSuper = true)
@Document(collection="et_dm_crm_community")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CrmCommunityEntity {

    private Date updateDate;

    private String city;

    private String uniqueId;

    private String houseType;

    private String communityId;

    private String communityName;

    private Integer viewCount;

}
