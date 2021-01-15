package com.apoem.mmxx.eventtracking.interfaces.dto.customer;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: VisitTrailRequestDto </p>
 * <p>Description: 客源访问轨迹DTO </p>
 * <p>Date: 2020/8/10 11:25 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Data
@ToString
public class TrailRequestDto implements Serializable {

    /**
     * 客源
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
    @ApiModelProperty(value = "范围（全部-WHOLE、浏览-VIS、收藏-COL、分享-RPS）", required = true, example = "WHOLE")
    @NotEmpty(message = "范围（全部-WHOLE、浏览-VIS、收藏-COL、分享-RPS）")
    private String scope;

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
