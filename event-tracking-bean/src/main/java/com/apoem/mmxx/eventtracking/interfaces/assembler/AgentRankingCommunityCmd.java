package com.apoem.mmxx.eventtracking.interfaces.assembler;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@ToString
public class AgentRankingCommunityCmd {
    private final String city;
    private final String agentId;
    private final LocalDateTime date;
    private final String scope;
    private final String period;
    private final String field;
    private final String nextKey;

    public AgentRankingCommunityCmd(String city, String agentId, LocalDateTime date, String scope, String period, String field, String nextKey) {
        this.city = city;
        this.agentId = agentId;
        this.date = date;
        this.scope = scope;
        this.period = period;
        this.field = field;
        this.nextKey = nextKey;
    }
}
