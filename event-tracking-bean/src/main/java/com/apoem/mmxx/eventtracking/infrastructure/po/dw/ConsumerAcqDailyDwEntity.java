package com.apoem.mmxx.eventtracking.infrastructure.po.dw;

import com.apoem.mmxx.eventtracking.infrastructure.po.ro.support.UvField;
import com.apoem.mmxx.eventtracking.infrastructure.po.support.BasicEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: ConsumerAcquisitionDailyDwEntity </p>
 * <p>Description: 每日数据 </p>
 * <p>Date: 2020/8/17 13:33 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@CompoundIndexes({
        @CompoundIndex(name = "idx_e", def = "{'event_type' : 1}"),
})
@Document(collection = "et_dw_daily_consumer_acquisition")
public class ConsumerAcqDailyDwEntity extends BasicEntity {

    private Date opTime;
    private String city;
    private String content;
    private String pageName;
    private String methodName;
    private String eventType;
    @UvField(trigger = 1, desc = "H5统计Uv用")
    private String openId;
    @UvField(trigger = 0, desc = "小程序统计Uv用")
    private String uniqueId;
    private String avenue;
    private Date startTime;
    private Date endTime;

    private Long duration;
    private String moduleName;
    private Long moduleId;
    private Long trackPointId;
    private String actionType;
    private String[] originActionDefinition;
    private String[] actionDefinition;

    /**
     * 统计uv用的上报数据
     */
    @UvField(trigger = 2, desc = "报表统计独立房源用")
    private String houseId;
    private String houseName;
    private String houseType;

    // 以下字段值取100倍，统计结果需除以100
    // houseArea、houseAreaLower、houseAreaUpper、houseTotalPrice、houseAveragePrice

    private Long houseArea;
    private Long houseAreaLower;
    private Long houseAreaUpper;
    private Long houseTotalPrice;
    private Long houseAveragePrice;

    private Integer houseLivingRoom;
    private Integer houseBedroom;
    private Integer houseBathroom;
    private String agentId;
    private String communityId;
    private String communityName;
    private String plateId;
    private String plateName;
    @UvField(trigger = 3, desc = "报表统计独立资讯用")
    private String informationId;
    private String storeId;
    private String storeName;
    private String companyId;
    private String companyName;
    private String blockId;
    private String blockName;
    private String inputType;
    private String gameId;
    private String occurred;
    private String localStatus;
    private String test;

    /**
     * 范围数
     */
    private String avgPriceRange;
    private String totalPriceRange;
    private Integer totalPriceRangeOrder;
    private String areaRange;
    private String areaRangeOrder;
    private String layoutRange;

//    H5
    private String infoTemplateId;
    private String posterCode;
    private String posterId;

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
