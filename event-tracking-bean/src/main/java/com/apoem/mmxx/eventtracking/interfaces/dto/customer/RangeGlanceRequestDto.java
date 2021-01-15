package com.apoem.mmxx.eventtracking.interfaces.dto.customer;

import com.apoem.mmxx.eventtracking.BaseConstants;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: FrequencyStatsRequestDto </p>
 * <p>Description: 点击频次统计DTO </p>
 * <p>Date: 2020/8/10 14:36 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Data
public class RangeGlanceRequestDto {

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
     * 范围
     */
    @ApiModelProperty(value = "范围（新房-NWHS、二手房-SHHS、租房-RTHS）", required = true, example = "SHHS")
    @NotEmpty(message = "范围（新房-NWHS、二手房-SHHS、租房-RTHS）")
    private String scope;

    /**
     * 种类
     */
    @ApiModelProperty(value = "种类（时间-TIME，价格-PRICE，面积-AREA）", required = true, example = "AREA")
    @NotEmpty(message = "种类（时间-TIME，价格-PRICE，面积-AREA）")
    private String field;

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
