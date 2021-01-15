package com.apoem.mmxx.eventtracking.infrastructure.biz;

import lombok.ToString;

import java.util.List;

/**
 * Please check `classpath:bizCity.json` for more details.
 */
@ToString
public class JsonCommunityInfo {

    private String city;
    private List<CommunityInfo> list;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public List<CommunityInfo> getList() {
        return list;
    }

    public void setList(List<CommunityInfo> list) {
        this.list = list;
    }
}
