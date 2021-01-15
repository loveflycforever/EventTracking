package com.apoem.mmxx.eventtracking.interfaces.dto;

import com.apoem.mmxx.eventtracking.BaseConstants;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: PosterStatsRequestDto </p>
 * <p>Description: 海报访问统计DTO </p>
 * <p>Date: 2020/11/17 10:38 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author somebody
 * @version v1.0
 */
@Data
public class PosterStatsRequestDto {

    /**
     * 唯一编号集
     */
    @ApiModelProperty(value = "唯一编号集", required = true, example = "[\"190\",\"191\"]")
    @NotEmpty(message = "唯一编号集")
    @Size(min = 1, message = "唯一编号集")
    private String[] id;

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
