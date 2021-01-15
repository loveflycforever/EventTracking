package com.apoem.mmxx.eventtracking.interfaces.assembler;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CustomerTrailCmd {
    private final String city;
    private final String customerId;
    private final String agentId;
    private final String actionType;
    private final String nextKey;

    public CustomerTrailCmd(String city, String customerId, String agentId, String actionType, String nextKey) {
        this.city = city;
        this.customerId = customerId;
        this.agentId = agentId;
        this.actionType = actionType;
        this.nextKey = nextKey;
    }
}
