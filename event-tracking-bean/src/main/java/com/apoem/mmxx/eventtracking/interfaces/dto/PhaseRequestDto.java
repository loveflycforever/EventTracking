package com.apoem.mmxx.eventtracking.interfaces.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: PhaseRequestDto </p>
 * <p>Description: 提交请求 </p>
 * <p>Date: 2020/7/23 10:42 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Data
public class PhaseRequestDto {

    /**
     * 令牌
     */
    @ApiModelProperty(value = "令牌", required = true, example = "1fcc86238b0")
    @NotBlank(message = "令牌")
    private String token;

    /**
     * 开始时间
     */
    @ApiModelProperty(value = "开始时间（yyyyMMdd）", required = true, example = "20200501")
    @NotBlank(message = "开始时间（yyyyMMdd）")
    private String startDate;

    /**
     * 结束时间
     */
    @ApiModelProperty(value = "结束时间（yyyyMMdd）", required = true, example = "20200510")
    @NotBlank(message = "结束时间（yyyyMMdd）")
    private String endDate;
}
