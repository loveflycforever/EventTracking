package com.apoem.mmxx.eventtracking.domain.analysis.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
/**
 * <p>Project: Event Tracking </p>
 * <p>Name: LiveGActivityAgentVo </p>
 * <p>Description: LiveG活动经纪人维度展示实体 </p>
 * <p>Date: 2020/11/27 10:22 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author somebody
 * @version v1.0
 */
@Data
public class LiveGActivityAgentVo implements Serializable {

    /**
     * 唯一编号
     */
    @ApiModelProperty(value = "唯一编号", example = "191")
    private String id;

    /**
     * 浏览次数
     */
    @ApiModelProperty(value = "浏览次数", example = "12")
    private Long actAgentAllPvAmount;

    /**
     * 分享次数
     */
    @ApiModelProperty(value = "分享次数", example = "12")
    private Long actAgentShareAmount;
}
