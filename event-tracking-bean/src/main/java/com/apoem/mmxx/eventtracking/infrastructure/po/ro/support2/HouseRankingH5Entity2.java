package com.apoem.mmxx.eventtracking.infrastructure.po.ro.support2;

import com.apoem.mmxx.eventtracking.infrastructure.po.ro.support.UvFieldTrigger;
import lombok.*;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: AgentHouseRankingEntity </p>
 * <p>Description: 效果分析-经纪人房源-H5 </p>
 * <p>Date: 2020/8/4 15:28 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
@Document(collection = "et_dm_house_ranking_h5")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@CompoundIndexes({
        @CompoundIndex(name = "idx1", def = "{'city' : 1, 'house_type' : 1, 'date_day' : 1, 'period_type' : 1, 'house_id' : 1}")
})
@UvFieldTrigger(value = 1)
public class HouseRankingH5Entity2 extends DmEntity2 {

    // 维度定义

    /**
     * 城市、房源、房源类型
     */
    @MrKey
    private String city;
    @MrKey
    private String houseId;
    @MrKey
    private String houseType;


    @MrValue
    private String houseName;
}
