package com.apoem.mmxx.eventtracking.interfaces.dto.trackpoint;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: TrackPointListRequestDto </p>
 * <p>Description:  </p>
 * <p>Date: 2020/9/8 8:27 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Data
public class TrackPointListRequestDto {

    @ApiModelProperty(value = "页码", required = true, example = "1")
    @NotNull(message = "页码")
    @Min(1)
    private Integer page;
    @ApiModelProperty(value = "页值", required = true, example = "50")
    @NotNull(message = "页值")
    @Min(1)
    private Integer pageSize;
    private String avenue;
    private String pageName;
    private String methodName;
}
