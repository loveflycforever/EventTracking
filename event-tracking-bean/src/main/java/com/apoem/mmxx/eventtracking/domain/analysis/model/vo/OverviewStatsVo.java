package com.apoem.mmxx.eventtracking.domain.analysis.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: OverviewStatsVo </p>
 * <p>Description: 房源总表统计数据行 </p>
 * <p>Date: 2020/8/10 18:56 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Data
@ToString(callSuper = true)
public class OverviewStatsVo {

    @ApiModelProperty(value = "经纪人访问量", example = "2")
    private Long agentPvAmount;
    @ApiModelProperty(value = "经纪人访客数", example = "2")
    private Long agentUvAmount;
    @ApiModelProperty(value = "客户访问量", example = "2")
    private Long customerPvAmount;
    @ApiModelProperty(value = "客户访问数", example = "2")
    private Long customerUvAmount;
    @ApiModelProperty(value = "房源收藏量", example = "2")
    private Long houseColAmount;
    /**
     * 房源收藏客户数
     */
    @ApiModelProperty(value = "房源收藏客户数", example = "2")
    private Long houseUniqueColAmount;

    /**
     * 访问次数
     */
    @ApiModelProperty(value = "访问次数", example = "12")
    private Long h5PvAmount;
    /**
     * 访问客户量
     */
    @ApiModelProperty(value = "访问客户量", example = "11")
    private Long h5UvAmount;
}
