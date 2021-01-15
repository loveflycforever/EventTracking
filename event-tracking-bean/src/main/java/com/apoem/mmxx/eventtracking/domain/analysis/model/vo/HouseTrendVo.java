package com.apoem.mmxx.eventtracking.domain.analysis.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: ImplicateCustomerVo </p>
 * <p>Description: 房源关联客源VO </p>
 * <p>Date: 2020/8/10 18:20 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class HouseTrendVo extends BaseStatsVo {

    /**
     * 时间
     */
    @ApiModelProperty(value = "时间（yyyy-MM-dd HH:mm:ss.SSS）", example = "2020-09-14 12:13:14.000")
    private String date;

    /**
     * 房源
     */
    @ApiModelProperty(value = "ID", example = "43425")
    private String houseId;

    /**
     * 新房、二手房、租房
     */
    @ApiModelProperty(value = "房源类型（二手房=SHHS、新房=NWHS、租房=RTHS）", example = "NWHS")
    private String houseType;
}
