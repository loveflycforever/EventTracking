package com.apoem.mmxx.eventtracking.infrastructure.po.entity;

import com.apoem.mmxx.eventtracking.infrastructure.po.support.BasicEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: VisitTimesRecord </p>
 * <p>Description:  </p>
 * <p>Date: 2020/9/11 10:17 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Document(collection="et_action_times_record")
public class ActionTimesRecordEntity extends BasicEntity {

    private Integer dayDate;
    private String customerId;
    private String houseId;
    private String houseType;
    private String agentId;
    private String actionDefinition;
    private Long times;
}
