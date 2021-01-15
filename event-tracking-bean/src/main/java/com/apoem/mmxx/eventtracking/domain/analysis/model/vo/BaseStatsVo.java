package com.apoem.mmxx.eventtracking.domain.analysis.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

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
@Data
public class BaseStatsVo implements Serializable {
    /**
     * 唯一编号
     */
    @ApiModelProperty(value = "唯一编号", example = "191")
    private String id;

    /**
     * 访问次数
     */
    @ApiModelProperty(value = "访问次数", example = "12")
    private Long pvAmount;
    /**
     * 访问客户量
     */
    @ApiModelProperty(value = "访问客户量", example = "11")
    private Long uvAmount;

    /**
     * 收藏次数
     */
    @ApiModelProperty(value = "收藏次数", example = "22")
    private Long colAmount;

    /**
     * 转发次数
     */
    @ApiModelProperty(value = "转发次数", example = "3")
    private Long rpsAmount;
}
