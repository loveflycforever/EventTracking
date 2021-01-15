package com.apoem.mmxx.eventtracking.domain.analysis.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: PosterStatsVo </p>
 * <p>Description: 海报H5返回结果 </p>
 * <p>Date: 2020/11/17 10:48 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author somebody
 * @version v1.0
 */
@Data
public class PosterStatsVo implements Serializable {

    /**
     * 唯一编号
     */
    @ApiModelProperty(value = "唯一编号", example = "191")
    private String id;

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
