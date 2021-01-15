package com.apoem.mmxx.eventtracking.interfaces.dto.agent;

import com.apoem.mmxx.eventtracking.BaseConstants;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: HouseRankingRequestDto </p>
 * <p>Description: 经纪人房源排名DTO </p>
 * <p>Date: 2020/8/10 8:54 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Data
public class HouseRankingRequestDto {

    @ApiModelProperty(value = "经纪人", required = true, example = "909441")
    @NotEmpty(message = "经纪人")
    private String agentId;

    @ApiModelProperty(value = "小区", required = true, example = "123")
    @NotEmpty(message = "小区")
    private String communityId;

    /**
     * 请求的时间（yyyy-MM-dd HH:mm:ss.SSS）
     */
    @ApiModelProperty(value = "请求的时间（yyyy-MM-dd HH:mm:ss.SSS）", required = true, example = "2020-09-14 12:13:14.000")
    @NotEmpty(message = "请求的时间（yyyy-MM-dd HH:mm:ss.SSS）")
    @Pattern(regexp = BaseConstants.DATE_REGEX, message = "请求的时间（yyyy-MM-dd HH:mm:ss.SSS）")
    private String acquireDate;

    /**
     * 范围
     */
    @ApiModelProperty(value = "范围（全部-WHOLE、新房-NWHS、二手房-SHHS、租房-RTHS）", required = true, example = "SHHS")
    @NotEmpty(message = "范围（全部-WHOLE、新房-NWHS、二手房-SHHS、租房-RTHS）")
    private String scope;

    /**
     * 周期
     */
    @ApiModelProperty(value = "周期（昨日-DAY1、七日-DAY7、三十日-DAY30）", required = true, example = "DAY1")
    @NotEmpty(message = "周期（昨日-DAY1、七日-DAY7、三十日-DAY30）")
    private String period;

    /**
     * 域
     */
    @ApiModelProperty(value = "域（访问量-PVA、访问数-UVA、收藏-COLA、转发-RPSA）", required = true, example = "UVA")
    @NotEmpty(message = "域（访问量-PVA、访问数-UVA、收藏-COLA、转发-RPSA）")
    private String field;

    /**
     * 城市
     */
    @ApiModelProperty(value = "城市", required = true, example = "fz")
    @NotEmpty(message = "城市")
    private String city;
}
