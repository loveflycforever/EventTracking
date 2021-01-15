package com.apoem.mmxx.eventtracking.domain.analysis.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: FrequencyStatsVo </p>
 * <p>Description: 点击频次统计VO </p>
 * <p>Date: 2020/8/10 14:36 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Data
public class CustomerRangeGlanceVo implements Serializable {

    /**
     * 总数
     */
    private Long totalAmounts;

    /**
     * 数据（总数 = 标签数量n*图例数量m）
     */
    @ApiModelProperty(value = "数据")
    private List<VisitStatsNode> rows;

    @Data
    public static class VisitStatsNode implements Serializable {

        @ApiModelProperty(value = "名称", example = "Monday")
        private String name;

        @ApiModelProperty(value = "每个范围的数量", example = "10")
        private Long amount;

        @ApiModelProperty(value = "顺序", example = "0")
        private Integer order;
    }
}
