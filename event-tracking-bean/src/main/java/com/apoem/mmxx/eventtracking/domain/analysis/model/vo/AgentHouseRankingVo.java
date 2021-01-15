package com.apoem.mmxx.eventtracking.domain.analysis.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: HouseRankingVo </p>
 * <p>Description: 经纪人房源排名VO </p>
 * <p>Date: 2020/8/10 8:54 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Data
public class AgentHouseRankingVo implements Serializable {
    /**
     * ID
     */
    @ApiModelProperty(value = "ID", example = "1")
    private String id;

    /**
     * 类型
     */
    @ApiModelProperty(value = "类型", example = "SHHS")
    private String type;

    /**
     * 名称
     */
    @ApiModelProperty(value = "名称", example = "汤臣一品4栋101")
    private String name;

    /**
     * 访问量
     */
    @ApiModelProperty(value = "访问量", example = "99")
    private Long pvAmount;


    /**
     * 访客数
     */
    @ApiModelProperty(value = "访客数", example = "99")
    private Long uvAmount;


    /**
     * 收藏量
     */
    @ApiModelProperty(value = "收藏量", example = "99")
    private Long colAmount;

    /**
     * 转发量
     */
    @ApiModelProperty(value = "转发量", example = "99")
    private Long rpsAmount;
}
