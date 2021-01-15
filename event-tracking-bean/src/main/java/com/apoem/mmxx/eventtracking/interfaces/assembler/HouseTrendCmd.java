package com.apoem.mmxx.eventtracking.interfaces.assembler;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@ToString
public class HouseTrendCmd {
    private final String city;
    private final String houseId;
    private final String houseType;
    private final String period;
    private final LocalDateTime localDateTime;

    public HouseTrendCmd(String city, String houseId, String houseType, String period, LocalDateTime localDateTime) {
        this.city = city;
        this.houseId = houseId;
        this.houseType = houseType;
        this.period = period;
        this.localDateTime = localDateTime;
    }

    public String getCity() {
        return city;
    }

    public String getHouseId() {
        return houseId;
    }

    public String getHouseType() {
        return houseType;
    }

    public String getPeriod() {
        return period;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }
}
