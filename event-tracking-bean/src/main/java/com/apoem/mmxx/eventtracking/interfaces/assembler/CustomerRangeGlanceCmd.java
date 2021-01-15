package com.apoem.mmxx.eventtracking.interfaces.assembler;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@ToString
public class CustomerRangeGlanceCmd {
    private final String city;
    private final String customerId;
    private final String agentId;
    private final String field;
    private final LocalDateTime localDateTime;
    private final String scope;

    public CustomerRangeGlanceCmd(String city, String customerId, String agentId, String field, LocalDateTime localDateTime, String scope) {
        this.city = city;
        this.customerId = customerId;
        this.agentId = agentId;
        this.field = field;
        this.localDateTime = localDateTime;
        this.scope = scope;
    }
}
