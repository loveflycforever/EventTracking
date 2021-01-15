package com.apoem.mmxx.eventtracking.interfaces.dto.acq;

import com.apoem.mmxx.eventtracking.BaseConstants;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: ConsumerAcquisitionRequestDto </p>
 * <p>Description: C端埋点上报数据RequestDTO </p>
 * <p>Date: 2020/7/14 12:26 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Data
@ToString
@ApiModel(description = "C端埋点数据上报请求数据传输对象")
public class ConsumerAcquisitionRequestDto {

    /**
     * 用户所选城市
     */
    @ApiModelProperty(value = "用户所选城市", required = true, example = "江苏")
    @NotBlank(message = "用户所选城市")
    private String city;

    /**
     * 操作时间（yyyy-MM-dd HH:mm:ss.SSS）
     */
    @ApiModelProperty(value = "操作时间（yyyy-MM-dd HH:mm:ss.SSS）", required = true, example = "2020-09-14 12:13:14.000")
    @NotEmpty(message = "操作时间（yyyy-MM-dd HH:mm:ss.SSS）")
    @Pattern(regexp = BaseConstants.DATE_REGEX, message = "操作时间（yyyy-MM-dd HH:mm:ss.SSS）")
    private String opTime;

    /**
     * 程序名称（标识）
     */
    @ApiModelProperty(value = "程序名称（标识）", required = true, example = "mshop")
    @NotBlank(message = "程序名称（标识）")
    private String app;

    /**
     * 程序版本号
     */
    @ApiModelProperty(value = "程序版本号", required = true, example = "100")
    @NotBlank(message = "程序版本号")
    private String appVersion;

    /**
     * 用户 openid
     */
    @ApiModelProperty(value = "用户 openid", required = true, example = "5f222ef358431f2c185f1717")
    @NotBlank(message = "用户 openid")
    private String openId;

    /**
     * 用户 uniqueId
     */
    @ApiModelProperty(value = "用户 uniqueId", example = "990011")
    private String uniqueId;

    // ==================================================

    /**
     * 设备品牌
     */
    @ApiModelProperty(value = "设备品牌", example = "Huawei")
    private String brand;
    /**
     * 设备型号
     */
    @ApiModelProperty(value = "设备型号", example = "note10")
    private String model;
    /**
     * 设备像素比
     */
    @ApiModelProperty(value = "设备像素比", example = "10")
    private String pixelRatio;
    /**
     * 屏幕宽度，单位px
     */
    @ApiModelProperty(value = "屏幕宽度，单位px", example = "300")
    private String screenWidth;
    /**
     * 屏幕高度，单位px
     */
    @ApiModelProperty(value = "屏幕高度，单位px", example = "1000")
    private String screenHeight;
    /**
     * 可使用窗口宽度，单位px
     */
    @ApiModelProperty(value = "可使用窗口宽度，单位px", example = "300")
    private String windowWidth;
    /**
     * 可使用窗口高度，单位px
     */
    @ApiModelProperty(value = "可使用窗口高度，单位px", example = "400")
    private String windowHeight;
    /**
     * 状态栏的高度，单位px
     */
    @ApiModelProperty(value = "状态栏的高度，单位px", example = "100")
    private String statusBarHeight;
    /**
     * 微信版本号
     */
    @ApiModelProperty(value = "微信版本号", example = "10.01")
    private String wxVersion;
    /**
     * 操作系统及版本
     */
    @ApiModelProperty(value = "操作系统及版本", example = "iOS7")
    private String system;
    /**
     * 客户端平台
     */
    @ApiModelProperty(value = "客户端平台", example = "iOS")
    private String platform;
    /**
     * 客户端基础库版本
     */
    @ApiModelProperty(value = "客户端基础库版本", example = "ojdk8")
    private String sdkVersion;

    // ==================================================

    /**
     * 渠道（iOS|Android|小程序-WMP）
     */
    @ApiModelProperty(value = "渠道（iOS|Android|小程序-WMP）", required = true, example = "WMP")
    @NotBlank(message = "渠道（iOS|Android|WMP）")
    private String avenue;

    /**
     * 页面名称
     */
    @ApiModelProperty(value = "页面名称", required = true, example = "test.page")
    @NotBlank(message = "页面名称")
    private String pageName;

    /**
     * 方法名称
     */
    @ApiModelProperty(value = "方法名称", required = true, example = "test.method")
    @NotNull(message = "方法名称")
    private String methodName;

    /**
     * 事件类型
     */
    @ApiModelProperty(value = "事件类型", required = true, example = "CLICK")
    @NotBlank(message = "事件类型")
    private String eventType;

    /**
     * 进入时间（时间戳）
     */
    @ApiModelProperty(value = "进入时间（yyyy-MM-dd HH:mm:ss.SSS）", example = "2020-09-14 11:12:13.000")
    @Pattern(regexp = BaseConstants.DATE_REGEX, message = "进入时间（yyyy-MM-dd HH:mm:ss.SSS）")
    private String startTime;

    /**
     * 退出时间（时间戳）
     */
    @ApiModelProperty(value = "退出时间（yyyy-MM-dd HH:mm:ss.SSS）", example = "2020-09-14 12:13:14.000")
    @Pattern(regexp = BaseConstants.DATE_REGEX, message = "退出时间（yyyy-MM-dd HH:mm:ss.SSS）")
    private String endTime;

    // =============================

    /**
     * 见文档 http://218.94.115.131:3600/project/303/interface/api/6095
     */
    private String content;
}
