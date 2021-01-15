package com.apoem.mmxx.eventtracking.domain.analysis.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: ImContactedRankingVo </p>
 * <p>Description: 经纪人IM联系排名VO </p>
 * <p>Date: 2020/8/10 8:54 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Data
public class AgentImContactedRankingVo {

    /**
     * 日期
     */
    @ApiModelProperty(value = "日期（yyyy-MM-dd HH:mm:ss.SSS）", example = "2020-09-14 12:13:14.000")
    private String date;

    /**
     * ID
     */
    @ApiModelProperty(value = "ID", example = "41")
    private String id;

    /**
     * 名称
     */
    @ApiModelProperty(value = "名称", example = "汤臣一品001")
    private String name;

    /**
     * 访问次数
     */
    @ApiModelProperty(value = "访问次数", example = "41")
    private Long amount;

    /**
     * 上升率
     */
    @ApiModelProperty(value = "上升率（正数上升，负数下降）", example = "10")
    private BigDecimal riseRate;

    /**
     * nextKey
     */
    @ApiModelProperty(value = "nextKey", example = "2012")
    private String nextKey;

}
