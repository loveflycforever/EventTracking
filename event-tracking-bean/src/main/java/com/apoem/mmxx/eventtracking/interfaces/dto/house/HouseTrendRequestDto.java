package com.apoem.mmxx.eventtracking.interfaces.dto.house;

import com.apoem.mmxx.eventtracking.BaseConstants;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: ImplicateCustomerRequestDto </p>
 * <p>Description: 房源关联客源DTO </p>
 * <p>Date: 2020/8/10 18:20 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Data
public class HouseTrendRequestDto {

    /**
     * 房源
     */
    @ApiModelProperty(value = "房源", required = true, example = "9001")
    @NotEmpty(message = "房源")
    private String houseId;

    /**
     * 房源
     */
    @ApiModelProperty(value = "房源类型（二手房=SHHS、新房=NWHS、租房=RTHS）", required = true, example = "NWHS")
    @NotEmpty(message = "房源类型（二手房=SHHS、新房=NWHS、租房=RTHS）")
    private String houseType;

    /**
     * 周期
     */
    @ApiModelProperty(value = "周期（全部-WHOLE、昨日-DAY1、七日-DAY7）", required = true, example = "DAY1")
    @NotEmpty(message = "周期（全部-WHOLE、昨日-DAY1、七日-DAY7）")
    private String period;

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
