package com.apoem.mmxx.eventtracking.interfaces.assembler;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@ToString
public class AgentVisitTrendCmd {
    private final String city;
    private final String agentId;
    private final String houseType;
    private final LocalDateTime localDateTime;

    public AgentVisitTrendCmd(String city, String agentId, String houseType, LocalDateTime localDateTime) {
        this.city = city;
        this.agentId = agentId;
        this.houseType = houseType;
        this.localDateTime = localDateTime;
    }
}
