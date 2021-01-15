package com.apoem.mmxx.eventtracking.interfaces.assembler;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@ToString
public class RankingHouseCmd {
    private final String city;
    private final String agentId;
    private final String communityId;
    private final LocalDateTime date;
    private final String scope;
    private final String period;
    private final String field;

    public RankingHouseCmd(String city, String agentId, String communityId, LocalDateTime date, String scope, String period, String field) {
        this.city = city;
        this.agentId = agentId;
        this.communityId = communityId;
        this.date = date;
        this.scope = scope;
        this.period = period;
        this.field = field;
    }
}
