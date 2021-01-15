package com.apoem.mmxx.eventtracking.infrastructure.po.dm;

import com.apoem.mmxx.eventtracking.infrastructure.po.support.DmEntity;
import lombok.*;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: StoreStatsEntity </p>
 * <p>Description: 门店后台 </p>
 * <p>Date: 2020/8/6 15:03 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
@Document(collection="et_dm_store_stats")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@CompoundIndexes({
        @CompoundIndex(name = "idx1", def = "{'city' : 1, 'date_day' : 1, 'store_id' : 1, 'house_type' : 1}")
})
public class StoreStatsEntity extends DmEntity {

    // 维度定义

    /**
     * 门店、城市
     */
    private String storeId;
    private String houseType;
    private String city;
}
