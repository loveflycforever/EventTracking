package com.apoem.mmxx.eventtracking.infrastructure.biz;


public class Data {

    private String cityName;
    private String cityCode;
    private Range range;

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setRange(Range range) {
        this.range = range;
    }

    public Range getRange() {
        return range;
    }

}