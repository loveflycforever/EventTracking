package com.apoem.mmxx.eventtracking.interfaces.assembler;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CustomerHouseTrailCmd {
    private final String city;
    private final String customerId;
    private final String houseId;
    private final String houseType;
    private final String nextKey;

    public CustomerHouseTrailCmd(String city, String customerId, String houseId, String houseType, String nextKey) {
        this.city = city;
        this.customerId = customerId;
        this.houseId = houseId;
        this.houseType = houseType;
        this.nextKey = nextKey;
    }
}
