package com.apoem.mmxx.eventtracking.infrastructure.po.dm;

import com.apoem.mmxx.eventtracking.infrastructure.po.support.DmEntity;
import lombok.*;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: AgentH5StatsEntity </p>
 * <p>Description: 运营后台-经纪人-H5 </p>
 * <p>Date: 2020/8/26 13:32 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
@Document(collection = "et_dm_agent_h5_stats")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@CompoundIndexes({
        @CompoundIndex(name = "idx", def = "{'city' : 1, 'agent_id': 1, 'house_type' : 1, 'date_day' : 1}")
})
public class AgentH5StatsEntity extends DmEntity {

    // 维度定义

    private String city;
    private String agentId;
    private String houseType;
}
