package com.apoem.mmxx.eventtracking.infrastructure.enums;

import lombok.Getter;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: RangeTypeEnum </p>
 * <p>Description:  </p>
 * <p>Date: 2020/9/29 17:20 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Getter
public enum RangeTypeEnum {
    // ......
    SELL_PRICE("SELL_PRICE", "二手房总价"),
    RENT_PRICE("RENT_PRICE", "租房总价"),
    NEW_PRICE("NEW_PRICE", "新房单价"),
    SELL_AREA("SELL_AREA", "二手房面积"),
    RENT_AREA("RENT_AREA", "租房面积"),
    SELL_LAYOUT("SELL_LAYOUT", "二手房户型"),
    RENT_LAYOUT("RENT_LAYOUT", "租房户型");

    private final String name;
    private final String desc;

    RangeTypeEnum(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    public static RangeTypeEnum getTotalPriceRange(String scope) {
        if (HouseTypeEnum.SHHS.maybe(scope) || HouseTypeEnum.NWHS.maybe(scope)) {
            return RangeTypeEnum.SELL_PRICE;
        } else {
            return RangeTypeEnum.RENT_PRICE;
        }
    }

    public static RangeTypeEnum getLayoutRange(String scope) {
        if (HouseTypeEnum.SHHS.maybe(scope) || HouseTypeEnum.NWHS.maybe(scope)) {
            return RangeTypeEnum.SELL_LAYOUT;
        } else {
            return RangeTypeEnum.RENT_LAYOUT;
        }
    }

    public static RangeTypeEnum getAvgPriceRange(String scope) {
        if (HouseTypeEnum.SHHS.maybe(scope) || HouseTypeEnum.NWHS.maybe(scope)) {
            return RangeTypeEnum.NEW_PRICE;
        } else {
            return RangeTypeEnum.RENT_PRICE;
        }
    }

    public static RangeTypeEnum getAreaRange(String scope) {

        if (HouseTypeEnum.SHHS.maybe(scope) || HouseTypeEnum.NWHS.maybe(scope)) {
            return RangeTypeEnum.SELL_AREA;
        } else {
            return RangeTypeEnum.RENT_AREA;
        }
    }
}
