package com.apoem.mmxx.eventtracking.domain.analysis.model.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: GeneralBriefVo </p>
 * <p>Description: 概括性简要VO </p>
 * <p>Date: 2020/8/10 13:56 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Data
public class CustomerBriefVo implements Serializable {

    /**
     * 新房
     */
    @ApiModelProperty(value = "新房 NWHS")
    @JsonProperty("NWHS")
    private VisitBriefView newHouse;

    /**
     * 新房
     */
    @ApiModelProperty(value = "二手房 SHHS")
    @JsonProperty("SHHS")
    private VisitBriefView secondHandHouse;

    /**
     * 租房
     */
    @ApiModelProperty(value = "租房 RTHS")
    @JsonProperty("RTHS")
    private VisitBriefView rentedHouse;

    @Data
    public static class VisitBriefView implements Serializable {

        private String communityId;
        private String communityName;

        /**
         * 房源
         */
        @ApiModelProperty(value = "房源", example = "9902")
        private String houseId;

        /**
         * 房源
         */
        @ApiModelProperty(value = "房源名称", example = "汤臣一品")
        private String houseName;

        /**
         * 价格
         */
        @ApiModelProperty(value = "价格", example = "85000000")
        private BigDecimal price;

        /**
         * 价格范围上限
         */
        @ApiModelProperty(value = "价格范围上限", example = "90000000")
        private BigDecimal priceUpperLimit;

        /**
         * 价格范围下限
         */
        @ApiModelProperty(value = "价格范围下限", example = "80000000")
        private BigDecimal priceLowerLimit;

        /**
         * 面积
         */
        @ApiModelProperty(value = "面积", example = "434")
        private BigDecimal area;

        /**
         * 面积范围上限
         */
        @ApiModelProperty(value = "价格范围上限", example = "500")
        private BigDecimal areaUpperLimit;

        /**
         * 面积范围下限
         */
        @ApiModelProperty(value = "面积范围下限", example = "400")
        private BigDecimal areaLowerLimit;


        private Integer houseBedroom;
        private Integer houseLivingRoom;
        private Integer houseBathRoom;

        /**
         * 户型
         */
        @ApiModelProperty(value = "户型", example = "4房2室2厅")
        private String layout;
    }
}
