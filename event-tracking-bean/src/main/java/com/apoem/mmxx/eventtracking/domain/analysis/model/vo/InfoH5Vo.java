package com.apoem.mmxx.eventtracking.domain.analysis.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: InfoH5Vo </p>
 * <p>Description: 咨询展示实体 </p>
 * <p>Date: 2020/11/18 14:20 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author somebody
 * @version v1.0
 */
@Data
public class InfoH5Vo implements Serializable {

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
     * 访问次数
     */
    @ApiModelProperty(value = "H5访问次数", example = "12")
    private Long h5PvAmount;
    /**
     * 访问客户量
     */
    @ApiModelProperty(value = "H5访问客户数", example = "11")
    private Long h5UvAmount;
}
