package com.apoem.mmxx.eventtracking.interfaces.assembler;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@ToString
public class VisitorCmd {
    private final String city;
    private final String houseId;
    private final String houseType;
    private final String period;
    private final LocalDateTime localDateTime;

    public VisitorCmd(String city, String houseId, String houseType, String period, LocalDateTime localDateTime) {
        this.city = city;
        this.houseId = houseId;
        this.houseType = houseType;
        this.period = period;
        this.localDateTime = localDateTime;
    }
}
