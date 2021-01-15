package com.apoem.mmxx.eventtracking.domain.analysis.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
/**
 * <p>Project: Event Tracking </p>
 * <p>Name: LiveGActivityVo </p>
 * <p>Description: LiveG展示实体 </p>
 * <p>Date: 2020/11/27 9:50 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author somebody
 * @version v1.0
 */
@Data
public class LiveGActivityVo implements Serializable {

    /**
     * 唯一编号
     */
    @ApiModelProperty(value = "唯一编号", example = "191")
    private String id;

    /**
     * 账号申请UV量
     */
    @ApiModelProperty(value = "账号申请UV量", example = "12")
    private Long registerUvAmount;

    /**
     * APP访问用户数
     */
    @ApiModelProperty(value = "APP访问用户数", example = "12")
    private Long appUvAmount;

    /**
     * APP访问次数
     */
    @ApiModelProperty(value = "APP访问次数", example = "12")
    private Long appPvAmount;

    /**
     * 非APP访问用户数
     */
    @ApiModelProperty(value = "非APP访问用户数", example = "12")
    private Long notAppUvAmount;

    /**
     * 非APP访问次数
     */
    @ApiModelProperty(value = "非APP访问次数", example = "12")
    private Long notAppPvAmount;

    /**
     * APP内分享次数
     */
    @ApiModelProperty(value = "APP内分享次数", example = "12")
    private Long appShareAmount;

    /**
     * 非APP分享次数
     */
    @ApiModelProperty(value = "非APP分享次数", example = "12")
    private Long notAppShareAmount;
}
