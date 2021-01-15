package com.apoem.mmxx.eventtracking.domain.acquisition.model.aggregates;

import lombok.*;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;
import java.util.function.Function;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: ExtraInfo </p>
 * <p>Description: 额外信息，记录页面详情 </p>
 * <p>Date: 2020/7/17 11:09 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ContentInfo {

    /**
     * 房源
     */
    private String houseId;
    private String houseName;
    private String houseType;
    private BigDecimal houseArea;
    private BigDecimal houseTotalPrice;
    private BigDecimal houseAveragePrice;
    private BigDecimal houseAreaLower;
    private BigDecimal houseAreaUpper;
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
    private String blockId;
    private String blockName;
    private String inputType;

    /**
     * 资讯
     */
    private String informationId;
    /**
     * 房源海报
     */
    private String posterCode;
    /**
     * 营销海报
     */
    private String posterId;
    /**
     * liveG活动
     */
    private String gameId;
    /**
     * 发生位置
     */
    private String occurred;
    /**
     * 注册页状态
     */
    private String localStatus;
    /**
     * 是否为测试账号
     */
    private String test;
}
