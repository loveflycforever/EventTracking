package com.apoem.mmxx.eventtracking.domain.analysis.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: BaseStatsRow </p>
 * <p>Description: 基础统计数据行 </p>
 * <p>Date: 2020/8/10 18:56 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BaseH5StatsVo extends BaseStatsVo {
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
