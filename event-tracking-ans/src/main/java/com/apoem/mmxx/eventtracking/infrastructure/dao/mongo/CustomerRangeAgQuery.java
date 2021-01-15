package com.apoem.mmxx.eventtracking.infrastructure.dao.mongo;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CustomerRangeAgQuery {
    private final String city;
    private final String customerId;
    private final String agentId;
    private final Integer beginDateDay;
    private final Integer endDateDay;
    private final String houseType;

    public CustomerRangeAgQuery(String city, String customerId, String agentId, Integer beginDateDay, Integer endDateDay, String houseType) {
        this.city = city;
        this.customerId = customerId;
        this.agentId = agentId;
        this.beginDateDay = beginDateDay;
        this.endDateDay = endDateDay;
        this.houseType = houseType;
    }
}
