package com.apoem.mmxx.eventtracking.infrastructure.po.dm;

import com.apoem.mmxx.eventtracking.infrastructure.po.support.DmEntity;
import lombok.*;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: AgentStatsEntity </p>
 * <p>Description: 运营后台-经纪人 </p>
 * <p>Date: 2020/8/26 13:32 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
@Document(collection = "et_dm_customer_house_action_stats")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@CompoundIndexes({
        @CompoundIndex(name = "idx1", def = "{'city' : 1, 'date_day' : 1, 'period_type' : 1, 'customer_id' : 1}")
})
public class CustomerHouseActionStatsEntity extends DmEntity {

    // 维度定义

    private String city;
    private String customerId;
    private String houseType;

    private String actionType;

    public Long getPageViewHouse() {
        return super.getPageView();
    }

    public Long getUniqueVisitorHouse() {
        return super.getUniqueVisitor();
    }

    public Long getCollectedUniqueHouse() {
        return super.getUniqueVisitor();
    }

    public Long getRepostedHouse() {
        return super.getPageView();
    }

    public CustomerHouseActionStatsEntity init() {
        super.setPageView(0L);
        super.setUniqueVisitor(0L);
        return this;
    }
}
