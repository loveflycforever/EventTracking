package com.apoem.mmxx.eventtracking.infrastructure.po.dw;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: CrmDailyClientVisitEntity </p>
 * <p>Description: 用户浏览收藏交互统计 </p>
 * <p>Date: 2020/9/24 16:12 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author somebody
 * @version v1.0
 */
@Data
@ToString(callSuper = true)
@Document(collection="et_dw_crm_180_client_visit")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CrmDailyClientVisitEntity {

    private String city;

    private String uniqueId;

    private String houseType;

    private Date date;

    private Integer day;

    private Long viewCount;

    private Long collectCount;

    private Long telCount;

}
