package com.apoem.mmxx.eventtracking.infrastructure.po.dm;

import com.apoem.mmxx.eventtracking.infrastructure.po.support.DmEntity;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: OverviewStatsEntity </p>
 * <p>Description: 运营后台-总览 </p>
 * <p>Date: 2020/8/26 13:32 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
@Document(collection="et_dm_overview_collected_house_stats")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OverviewHouseCollectedStatsEntity extends DmEntity {

    // 维度定义

    /**
     * 城市
     */
    private String city;
    private String inputType;
}
