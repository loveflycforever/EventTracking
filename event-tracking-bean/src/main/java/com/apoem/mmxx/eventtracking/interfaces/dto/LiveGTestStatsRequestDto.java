package com.apoem.mmxx.eventtracking.interfaces.dto;

import com.apoem.mmxx.eventtracking.BaseConstants;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class LiveGTestStatsRequestDto {

    /**
     * 唯一编号
     */
    @ApiModelProperty(value = "唯一编号", required = true, example = "[\"190\",\"191\"]")
    @NotEmpty(message = "唯一编号")
    @Size(min = 1, message = "唯一编号集")
    private String[] id;

    /**
     * 范围
     */
    @ApiModelProperty(value = "范围", required = true, example = "ALL/TEST")
    @NotEmpty(message = "范围")
    private String scope;

    /**
     * 请求的时间（yyyy-MM-dd HH:mm:ss.SSS）
     */
    @ApiModelProperty(value = "请求的时间（yyyy-MM-dd HH:mm:ss.SSS）", required = true, example = "2020-09-14 12:13:14.000")
    @NotEmpty(message = "请求的时间（yyyy-MM-dd HH:mm:ss.SSS）")
    @Pattern(regexp = BaseConstants.DATE_REGEX, message = "请求的时间（yyyy-MM-dd HH:mm:ss.SSS）")
    private String acquireDate;
}
