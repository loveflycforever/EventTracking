package com.apoem.mmxx.eventtracking.interfaces.dto.customer;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: InvolvedTraceRequestDto </p>
 * <p>Description: 客源关联的房源轨迹DTO </p>
 * <p>Date: 2020/8/10 17:38 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Data
@ToString
public class HouseTrailRequestDto implements Serializable {

    /**
     * 客户
     */
    @ApiModelProperty(value = "客源", required = true, example = "81931")
    @NotEmpty(message = "客源")
    private String customerId;

    /**
     * 房源
     */
    @ApiModelProperty(value = "房源", required = true, example = "1931")
    @NotEmpty(message = "房源")
    private String houseId;

    /**
     * 房源类型（二手房=SHHS、新房=NWHS、租房=RTHS）
     */
    @ApiModelProperty(value = "房源类型（二手房=SHHS、新房=NWHS、租房=RTHS）", required = true, example = "NWHS")
    @NotEmpty(message = "房源类型（二手房=SHHS、新房=NWHS、租房=RTHS）")
    private String houseType;

    /**
     * 经纪人
     */
    @ApiModelProperty(value = "经纪人", required = true, example = "81931")
    @NotEmpty(message = "经纪人")
    private String agentId;

    /**
     * 查看更多
     */
    @ApiModelProperty(value = "查看更多（首页-【0】，末页-【-1】）", required = true, example = "0")
    @NotEmpty(message = "查看更多（首页-【0】，末页-【-1】）")
    private String nextKey;

    /**
     * 城市
     */
    @ApiModelProperty(value = "城市", required = true, example = "fz")
    @NotEmpty(message = "城市")
    private String city;
}
