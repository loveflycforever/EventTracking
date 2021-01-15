package com.apoem.mmxx.eventtracking.domain.analysis.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: VisitTrailVo </p>
 * <p>Description: 客源访问轨迹VO </p>
 * <p>Date: 2020/8/10 11:25 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Data
public class CustomerTrailVo implements Serializable {

    /**
     * 时间
     */
    @ApiModelProperty(value = "时间（yyyy-MM-dd HH:mm:ss.SSS）", example = "2020-09-14 12:13:14.000")
    private String date;

    /**
     * 浏览、分享、收藏
     */
    @ApiModelProperty(value = "范围（浏览-VIS、收藏-COL、分享-RPS）", example = "COL")
    private String scope;

    /**
     * 定义
     */
    @ApiModelProperty(value = "定义", example = "VIS_HUS")
    private String def;

    /**
     * 浏览次数
     * 第xx次浏览
     */
    @ApiModelProperty(value = "浏览次数", example = "3")
    private String number;

    /**
     * 提交时的原文
     */
    @ApiModelProperty(value = "提交时的原文")
    private String content;

    /**
     * 浏览时长
     */
    @ApiModelProperty(value = "浏览时长", example = "53")
    private String duration;

    /**
     * nextKey
     */
    @ApiModelProperty(value = "nextKey", example = "2012")
    private String nextKey;

}
