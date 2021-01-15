package com.apoem.mmxx.eventtracking.interfaces.dto.trackpoint;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: TrackPointRequestDto </p>
 * <p>Description: 埋点信息对象Dto </p>
 * <p>Date: 2020/7/31 10:26 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
@ApiModel(description="埋点信息对象")
public class TrackPointUpdateRequestDto extends TrackPointInsertRequestDto {

    /**
     * ID
     */
    @ApiModelProperty(value = "ID", required = true, example = "1")
    @NotNull(message = "ID")
    @Min(message = "ID", value = 1)
    private Long id;
}
