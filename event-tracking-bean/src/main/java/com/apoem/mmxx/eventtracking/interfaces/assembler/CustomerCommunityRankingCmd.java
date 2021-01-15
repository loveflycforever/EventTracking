package com.apoem.mmxx.eventtracking.interfaces.assembler;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@ToString
public class CustomerCommunityRankingCmd {
    private final String city;
    private final String customerId;
    private final String agentId;
    private final LocalDateTime date;
    private final String scope;
    private final String nextKey;

    public CustomerCommunityRankingCmd(String city, String customerId, String agentId, LocalDateTime date, String scope, String nextKey) {
        this.city = city;
        this.customerId = customerId;
        this.agentId = agentId;
        this.date = date;
        this.scope = scope;
        this.nextKey = nextKey;
    }
}
