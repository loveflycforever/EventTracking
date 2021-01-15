package com.apoem.mmxx.eventtracking.interfaces.dto.customer;

import com.apoem.mmxx.eventtracking.BaseConstants;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: GeneralBriefRequestDto </p>
 * <p>Description: 概括性简要DTO </p>
 * <p>Date: 2020/8/10 13:56 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Data
public class BriefRequestDto {

    /**
     * 客户
     */
    @ApiModelProperty(value = "客源", required = true, example = "81931")
    @NotEmpty(message = "客源")
    private String customerId;

    /**
     * 经纪人
     */
    @ApiModelProperty(value = "经纪人", required = true, example = "81931")
    @NotEmpty(message = "经纪人")
    private String agentId;

    /**
     * 请求的时间（yyyy-MM-dd HH:mm:ss.SSS）
     */
    @ApiModelProperty(value = "请求的时间（yyyy-MM-dd HH:mm:ss.SSS）", required = true, example = "2020-09-14 12:13:14.000")
    @NotEmpty(message = "请求的时间（yyyy-MM-dd HH:mm:ss.SSS）")
    @Pattern(regexp = BaseConstants.DATE_REGEX, message = "请求的时间（yyyy-MM-dd HH:mm:ss.SSS）")
    private String acquireDate;

    /**
     * 城市
     */
    @ApiModelProperty(value = "城市", required = true, example = "fz")
    @NotEmpty(message = "城市")
    private String city;
}
