package com.apoem.mmxx.eventtracking.infrastructure.repository;

import java.time.LocalDateTime;
import java.util.List;

public class StoreStatsCmd {
    private final String city;
    private final List<String> storeIds;
    private final String houseType;
    private final LocalDateTime localDateTime;

    public StoreStatsCmd(String city, List<String> storeIds, String houseType, LocalDateTime localDateTime) {
        this.city = city;
        this.storeIds = storeIds;
        this.houseType = houseType;
        this.localDateTime = localDateTime;
    }

    public String getCity() {
        return city;
    }

    public List<String> getStoreIds() {
        return storeIds;
    }

    public String getHouseType() {
        return houseType;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }
}
