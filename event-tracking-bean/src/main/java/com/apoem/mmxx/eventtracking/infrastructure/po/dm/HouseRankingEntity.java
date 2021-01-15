package com.apoem.mmxx.eventtracking.infrastructure.po.dm;

import com.apoem.mmxx.eventtracking.infrastructure.po.support.BasicEntity;
import lombok.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: AgentHouseRankingEntity </p>
 * <p>Description: 效果分析-经纪人房源 </p>
 * <p>Date: 2020/8/4 15:28 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
@Document(collection = "et_dm_house_ranking")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@CompoundIndexes({
        @CompoundIndex(name = "idx1", def = "{'city' : 1, 'house_type' : 1, 'date_day' : 1, 'period_type' : 1, 'house_id' : 1}")
})
public class HouseRankingEntity extends BasicEntity {

    /**
     * 日期，yyyyMMdd
     * 数据类型，昨日、七日，三十日
     */
    private Integer dateDay;
    private String periodType;

    // 维度定义

    /**
     * 城市、房源、房源类型
     */
    private String city;
    private String houseId;
    private String houseType;

    //维度数据

    private String houseName;
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

    // 统计数据

    /**
     * 访问次数、访问用户数、收藏次数、im联系数、电话联系数
     */
    private Long pageView;
    private Long uniqueVisitor;
    private Long collected;
    private Long reposted;
    private Long imConnected;
    private Long phoneConnected;

    public HouseRankingEntity init() {
        this.pageView = 0L;
        this.uniqueVisitor = 0L;
        this.collected = 0L;
        this.reposted = 0L;
        this.imConnected = 0L;
        this.phoneConnected = 0L;

        this.dateDay = 0;
        this.city = StringUtils.EMPTY;
        this.houseId = StringUtils.EMPTY;
        this.houseName = StringUtils.EMPTY;
        this.houseType = StringUtils.EMPTY;
        this.periodType = StringUtils.EMPTY;
        this.agentId = StringUtils.EMPTY;
        this.communityId = StringUtils.EMPTY;
        this.communityName = StringUtils.EMPTY;
        return this;
    }
}
