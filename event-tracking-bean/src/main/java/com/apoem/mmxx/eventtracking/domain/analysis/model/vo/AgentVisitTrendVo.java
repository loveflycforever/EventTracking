package com.apoem.mmxx.eventtracking.domain.analysis.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: VisitTrendVo </p>
 * <p>Description: 访问经纪人趋势VO </p>
 * <p>Date: 2020/8/10 7:51 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Data
public class AgentVisitTrendVo implements Serializable {

    /**
     * 日期（yyyy-MM-dd HH:mm:ss.SSS）
     */
    @ApiModelProperty(value = "日期（yyyy-MM-dd HH:mm:ss.SSS）", example = "2020-09-14 12:13:14.000")
    private String date;

    /**
     * 访问量
     */
    @ApiModelProperty(value = "访问量", example = "100")
    private Long pvAmount;

    /**
     * 访问量
     */
    @ApiModelProperty(value = "访问量", example = "100")
    private Long uvAmount;

    /**
     * 上升率
     */
    @ApiModelProperty(value = "上升率（正数上升，负数下降）", example = "10")
    private BigDecimal pvRiseRate;

    /**
     * 上升率
     */
    @ApiModelProperty(value = "上升率（正数上升，负数下降）", example = "10")
    private BigDecimal uvRiseRate;

    /**
     * 访问累计（总量）
     */
    @ApiModelProperty(value = "访问累计（总量）", example = "10000")
    private Long pvTotal;

    /**
     * 访问累计（总量）
     */
    @ApiModelProperty(value = "访问累计（总量）", example = "10000")
    private Long uvTotal;
}
