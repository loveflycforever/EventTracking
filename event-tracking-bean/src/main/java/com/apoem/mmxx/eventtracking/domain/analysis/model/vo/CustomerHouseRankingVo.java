package com.apoem.mmxx.eventtracking.domain.analysis.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: CommunityRankingVo </p>
 * <p>Description: 经纪人小区排名VO </p>
 * <p>Date: 2020/8/10 8:54 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Data
public class CustomerHouseRankingVo implements Serializable {
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
}

