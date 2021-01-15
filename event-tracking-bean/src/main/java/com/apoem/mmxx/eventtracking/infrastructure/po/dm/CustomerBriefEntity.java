package com.apoem.mmxx.eventtracking.infrastructure.po.dm;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: CustomerBriefEntity </p>
 * <p>Description: 180天，用户轨迹总览 </p>
 * <p>Date: 2020/8/5 15:59 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Data
public class CustomerBriefEntity {

    /**
     * 日期，yyyyMMdd
     * 数据类型，昨日、七日，三十日
     */
    private Integer dateDay;
    private String periodType;

    // 维度定义

    /**
     * 城市、客源、经纪人、房源类型
     */
    private String city;
    private String houseId;
    private String agentId;
    private String houseType;

    /**
     * 小区、板块、价格、面积、户型
     */
    private String houseName;
    private String communityId;
    private String communityName;
    private String plateId;
    private String plateName;

    // 以下字段值取100倍，统计结果需除以100
    // houseArea、houseAreaLower、houseAreaUpper、houseTotalPrice、houseAveragePrice

    private Long houseTotalPrice;
    private Integer houseAvgPrice;
    private Long houseArea;


    private Integer houseBedroom;
    private Integer houseLivingRoom;
    private Integer houseBathroom;

    public CustomerBriefEntity init() {
        this.setHouseId(StringUtils.EMPTY);
        this.setHouseName(StringUtils.EMPTY);
        this.setCommunityId(StringUtils.EMPTY);
        this.setCommunityName(StringUtils.EMPTY);
        this.setHouseTotalPrice(0L);
        this.setHouseBedroom(0);
        this.setHouseLivingRoom(0);
        this.setHouseBathroom(0);
        this.setHouseArea(0L);
        return this;
    }
}
