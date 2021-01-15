package com.apoem.mmxx.eventtracking.interfaces.assembler;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@ToString
public class CustomerBriefCmd {
    private final String city;
    private final String customerId;
    private final String agentId;
    private final LocalDateTime localDateTime;

    public CustomerBriefCmd(String city, String customerId, String agentId, LocalDateTime localDateTime) {
        this.city = city;
        this.customerId = customerId;
        this.agentId = agentId;
        this.localDateTime = localDateTime;
    }
}
