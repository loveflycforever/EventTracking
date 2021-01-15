package com.apoem.mmxx.eventtracking.interfaces.assembler;

import java.time.LocalDateTime;

public class CustomerHouseRankingCmd {
    private final String city;
    private final String customerId;
    private final String communityId;
    private final String agentId;
    private final LocalDateTime localDateTime;
    private final String scope;

    public CustomerHouseRankingCmd(String city, String customerId, String communityId, String agentId, LocalDateTime localDateTime, String scope) {
        this.city = city;
        this.customerId = customerId;
        this.communityId = communityId;
        this.agentId = agentId;
        this.localDateTime = localDateTime;
        this.scope = scope;
    }

    public String getCity() {
        return city;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getCommunityId() {
        return communityId;
    }

    public String getAgentId() {
        return agentId;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public String getScope() {
        return scope;
    }
}
