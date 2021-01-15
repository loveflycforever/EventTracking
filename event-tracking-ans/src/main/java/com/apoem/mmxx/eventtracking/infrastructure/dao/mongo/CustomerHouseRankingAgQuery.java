package com.apoem.mmxx.eventtracking.infrastructure.dao.mongo;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CustomerHouseRankingAgQuery {
    private final String city;
    private final String customerId;
    private final String agentId;
    private final Integer beginDateDay;
    private final Integer endDateDay;

    public CustomerHouseRankingAgQuery(String city, String customerId, String agentId, Integer beginDateDay, Integer endDateDay) {
        this.city = city;
        this.customerId = customerId;
        this.agentId = agentId;
        this.beginDateDay = beginDateDay;
        this.endDateDay = endDateDay;
    }
}
