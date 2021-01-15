package com.apoem.mmxx.eventtracking.domain.analysis.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

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
public class CustomerCommunityRankingVo implements Serializable {
    /**
     * 日期
     */
    @ApiModelProperty(value = "日期（yyyy-MM-dd HH:mm:ss.SSS）", example = "2020-09-14 12:13:14.000")
    private String date;

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
    @ApiModelProperty(value = "名称", example = "汤臣一品")
    private String name;

    /**
     * 访问量
     */
    @ApiModelProperty(value = "访问量", example = "99")
    private Long pvAmount;

    /**
     * 总访问量
     */
    @ApiModelProperty(value = "总访问量", example = "999")
    private Long totalPvAmount;

    /**
     * nextKey
     */
    @ApiModelProperty(value = "nextKey", example = "2012")
    private String nextKey;

    private List<CustomerHouseRankingVo> rows;
}

