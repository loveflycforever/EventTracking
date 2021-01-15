package com.apoem.mmxx.eventtracking.infrastructure.po.dm;

import com.apoem.mmxx.eventtracking.infrastructure.po.support.HourView;
import com.apoem.mmxx.eventtracking.infrastructure.po.support.BasicEntity;
import lombok.*;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: CustomerHouseRankingEntity </p>
 * <p>Description: 客源分析-房源 </p>
 * <p>Date: 2020/8/4 15:28 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@Document(collection="et_dm_customer_house_ranking")
@CompoundIndexes({
        @CompoundIndex(name = "idx1", def = "{'date_day' : 1, 'city' : 1, 'house_id' : 1, 'customer_id' : 1, 'house_id' : 1, 'house_type' : 1}")
})
public class CustomerHouseRankingEntity extends BasicEntity {

    /**
     * 日期，yyyyMMdd
     * 数据类型，昨日、七日，三十日
     */
    private Integer dateDay;
    private String periodType;

    // 维度定义

    /**
     * 客源、城市、房源、房源类型
     */
    private String customerId;
    private String city;
    private String houseId;
    private String houseType;

    // 维度数据

    private String houseName;

    // 以下字段值取100倍，统计结果需除以100
    // houseArea、houseAreaLower、houseAreaUpper、houseTotalPrice、houseAveragePrice

    private Long houseArea;
    private Long houseTotalPrice;
    private Long houseAveragePrice;
    private Long houseAreaLower;
    private Long houseAreaUpper;

    private Integer houseLivingRoom;
    private Integer houseBedroom;
    private Integer houseBathroom;
    private String communityId;
    private String communityName;
    private String plateId;
    private String plateName;
    private String agentId;
    private String storeId;
    private String storeName;
    private String companyId;
    private String companyName;

    /**
     * 范围数
     */
    private String avgPriceRange;
    private Integer avgPriceRangeOrder;
    private String totalPriceRange;
    private Integer totalPriceRangeOrder;
    private String areaRange;
    private Integer areaRangeOrder;
    private String layoutRange;
    private Integer layoutRangeOrder;


    private Integer dayOfWeek;
    private HourView hourView;

    /**
     * 最后操作时间
     */
    private Date lastOpTime;

    // 统计数据

    /**
     * 访问次数、访问用户数、收藏次数、转发次数
     */
    private Long pageView;
    private Long uniqueVisitor;
    private Long collected;
    private Long reposted;

    public CustomerHouseRankingEntity init() {
        this.pageView = 0L;
        this.uniqueVisitor = 0L;
        this.collected = 0L;
        this.reposted = 0L;
        return this;
    }


    public void setHouseTotalPrice(BigDecimal houseTotalPrice) {
        this.houseTotalPrice = houseTotalPrice.multiply(new BigDecimal(100)).longValue();
    }

    public void setHouseAveragePrice(BigDecimal houseAveragePrice) {
        this.houseAveragePrice = houseAveragePrice.multiply(new BigDecimal(100)).longValue();
    }

    public void setHouseArea(BigDecimal houseArea) {
        this.houseArea = houseArea.multiply(new BigDecimal(100)).longValue();
    }

    public void setHouseAreaLower(BigDecimal houseAreaLower) {
        this.houseAreaLower = houseAreaLower.multiply(new BigDecimal(100)).longValue();
    }

    public void setHouseAreaUpper(BigDecimal houseAreaUpper) {
        this.houseAreaUpper = houseAreaUpper.multiply(new BigDecimal(100)).longValue();
    }
}
