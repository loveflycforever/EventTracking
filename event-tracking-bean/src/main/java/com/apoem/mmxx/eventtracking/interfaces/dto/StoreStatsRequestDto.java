package com.apoem.mmxx.eventtracking.interfaces.dto;

import com.apoem.mmxx.eventtracking.BaseConstants;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: BaseStatsRequestDto </p>
 * <p>Description: 访问统计DTO </p>
 * <p>Date: 2020/8/10 18:20 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Data
public class StoreStatsRequestDto {

    /**
     * 唯一编号
     */
    @ApiModelProperty(value = "唯一编号", required = true, example = "[\"190\",\"191\"]")
    @NotEmpty(message = "唯一编号")
    @Size(min = 1, message = "唯一编号集")
    private String[] id;

    /**
     * 房源类型
     */
    @ApiModelProperty(value = "房源类型（二手房=SHHS、新房=NWHS、租房=RTHS）", required = true, example = "NWHS")
    @NotEmpty(message = "房源类型（二手房=SHHS、新房=NWHS、租房=RTHS）")
    private String houseType;


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
