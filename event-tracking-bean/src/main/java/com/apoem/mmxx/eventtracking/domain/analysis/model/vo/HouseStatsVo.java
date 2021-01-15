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
public class HouseStatsVo extends BaseH5StatsVo {

    /**
     * IM联系数
     */
    @ApiModelProperty(value = "IM联系数", example = "22")
    private Long imConnectedAmount;

    /**
     * 电话联系数
     */
    @ApiModelProperty(value = "电话联系数", example = "3")
    private Long phoneConnectedAmount;
}
