package com.apoem.mmxx.eventtracking.infrastructure.po.dm;

import com.apoem.mmxx.eventtracking.infrastructure.po.support.DmEntity;
import lombok.*;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: InformationStatsEntity </p>
 * <p>Description: 运营后台-资讯 </p>
 * <p>Date: 2020/8/26 13:32 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
@Document(collection="et_dm_information_stats")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@CompoundIndexes({
        @CompoundIndex(name = "idx", def = "{'city' : 1, 'information_id': 1, 'date_day' : 1}")
})
public class InformationStatsEntity extends DmEntity {

    // 维度定义

    /**
     * 咨询、城市
     */
    private String informationId;
    private String city;
}
