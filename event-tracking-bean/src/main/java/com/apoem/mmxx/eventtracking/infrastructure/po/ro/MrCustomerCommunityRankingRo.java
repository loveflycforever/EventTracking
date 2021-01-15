package com.apoem.mmxx.eventtracking.infrastructure.po.ro;

import com.apoem.mmxx.eventtracking.infrastructure.dao.support.Affix;
import com.apoem.mmxx.eventtracking.infrastructure.dao.support.Sharding;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: MrCustomerCommunityRankingRo </p>
 * <p>Description: 客源社区 </p>
 * <p>Date: 2020/8/31 16:40 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Data
@ToString
@Sharding(fix = Affix.FORMAT)
@Document("et_{}_customer_community_ranking_result")
public class MrCustomerCommunityRankingRo {

    private Key id;
    private Value value;

    @Data
    public static class Key {
        private String customerId;
        private String communityId;
        private String houseType;
        private String agentId;
        private String city;
    }

    @Data
    public static class Value {
        private String communityName;
        private String plateId;
        private String plateName;

        private Long pageView;
        private Long houseTotalPriceTotal;
        private Long houseTotalPriceAmount;
        private Long houseAvgPriceTotal;
        private Long houseAvgPriceAmount;
        private Long houseLivingRoomTotal;
        private Long houseLivingRoomAmount;
        private Long houseBathroomTotal;
        private Long houseBathroomAmount;
        private Long houseBedroomTotal;
        private Long houseBedroomAmount;

        private Long uniqueHouse;
    }
}