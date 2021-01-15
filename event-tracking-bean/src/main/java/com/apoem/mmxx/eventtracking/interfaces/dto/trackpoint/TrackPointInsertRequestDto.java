package com.apoem.mmxx.eventtracking.interfaces.dto.trackpoint;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
@Data
@ApiModel(description="埋点信息对象")
public class TrackPointInsertRequestDto {

    /**
     * 页面名称
     */
    @ApiModelProperty(value = "页面名称", required = true, example = "test.page")
    @NotBlank(message = "页面名称")
    private String pageName;

    /**
     * 页面中文名称
     */
    @ApiModelProperty(value = "页面中文名称", required = true, example = "测试页面")
    @NotBlank(message = "页面中文名称")
    private String pageNameCn;

    /**
     * 方法名称（iOS、Android或小程序的方法名）
     */
    @ApiModelProperty(value = "方法名称（iOS、Android或小程序的方法名）", required = true, example = "test.method")
    @NotNull(message = "方法名称（iOS、Android或小程序的方法名）")
    private String methodName;

    /**
     * 方法中文名称（iOS、Android或小程序的方法中文名）
     */
    @ApiModelProperty(value = "方法中文名称（iOS、Android或小程序的方法中文名）", required = true, example = "测试方法")
    @NotNull(message = "方法中文名称（iOS、Android或小程序的方法中文名）")
    private String methodNameCn;

    /**
     * 模块ID
     */
    @ApiModelProperty(value = "模块ID", required = true, example = "1")
    @NotNull(message = "模块ID")
    @Min(message = "模块ID", value = 1)
    private Long moduleId;

    /**
     * 事件类型（打开页面=VIEW|页面点击=CLICK|页面长按=HOLD）
     */
    @ApiModelProperty(value = "事件类型（打开页面=VIEW|页面点击=CLICK）", required = true, example = "VIEW")
    @NotBlank(message = "事件类型（打开页面=VIEW|页面点击=CLICK）")
    private String eventType;

    /**
     * 操作类型，轨迹用（访问=VISIT|收藏=COLLECT|分享=REPOST|缺省=EMPTY）
     */
    @ApiModelProperty(value = "操作类型，轨迹用（访问=VISIT|收藏=COLLECT|分享=REPOST|缺省=EMPTY）", required = true, example = "VISIT")
    @NotBlank(message = "操作类型，轨迹用（访问=VISIT|收藏=COLLECT|分享=REPOST|缺省=EMPTY）")
    private String actionType;

    /**
     * 动作定义（访问经纪人=VIS_AGT|访问房源=VIS_HUS|使用IM=USE_IM|使用手机=USE_PHN|收藏房源COL_HUS|查看资讯VIS_IFO|缺省=EMPTY）
     */
    @ApiModelProperty(value = "动作定义（访问经纪人=VIS_AGT|访问房源=VIS_HUS|使用IM=USE_IM|使用手机=USE_PHN|收藏房源COL_HUS|查看资讯VIS_IFO|缺省=EMPTY）", required = true, example = "[\"VIS_AGT\",\"COL_HUS\"]")
    @NotNull(message = "动作定义（访问经纪人=VIS_AGT|访问房源=VIS_HUS|使用IM=USE_IM|使用手机=USE_PHN|收藏房源COL_HUS|查看资讯VIS_IFO|缺省=EMPTY）")
    @Size(min = 1, message = "动作定义（访问经纪人=VIS_AGT|访问房源=VIS_HUS|使用IM=USE_IM|使用手机=USE_PHN|收藏房源COL_HUS|查看资讯VIS_IFO|缺省=EMPTY）")
    private String[] actionDefinition;

    /**
     * 渠道（iOS|Android|WMP）
     */
    @ApiModelProperty(value = "渠道（iOS|Android|WMP）", required = true, example = "iOS")
    @NotBlank(message = "渠道（iOS|Android|WMP）")
    private String avenue;

    /**
     * 终端（B端=Business|C端=Consumer）
     */
    @ApiModelProperty(value = "终端（B端=Business|C端=Consumer）", required = true, example = "Business")
    @NotBlank(message = "终端（B端=Business|C端=Consumer）")
    private String endpoint;
}
