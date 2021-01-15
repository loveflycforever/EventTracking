package com.apoem.mmxx.eventtracking.interfaces.dto.acq;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: AppAcquisitionDto </p>
 * <p>Description: App端请求Dto </p>
 * <p>Date: 2020/7/14 12:26 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Data
@ToString
@ApiModel(description = "C端埋点数据上报请求数据传输对象")
public class BusinessAcquisitionRequestDto {

    /**
     * app名称(示例 房房房)
     */
    @ApiModelProperty(value = "app名称(示例 房房房)", required = true)
    @NotEmpty(message = "app名称(示例 房房房)")
    private String app;
    /**
     * 应用版本(示例 8.0.93)
     */
    @ApiModelProperty(value = "应用版本(示例 8.0.93)", required = true)
    @NotEmpty(message = "应用版本(示例 8.0.93)")
    private String appVersion;
    /**
     * 终端类型(枚举值 1 android, 21 ipad, 22 iphone, 20 其它IOS设备, 30 web, 40 小程序)
     */
    @ApiModelProperty(value = "终端类型(枚举值 1 android, 21 ipad, 22 iphone, 20 其它IOS设备, 30 web, 40 小程序)", required = true)
    @NotEmpty(message = "终端类型(枚举值 1 android, 21 ipad, 22 iphone, 20 其它IOS设备, 30 web, 40 小程序)")
    private String platform;
    /**
     * 硬件设备类型(示例 iPhone XS Max)
     */
    @ApiModelProperty(value = "硬件设备类型(示例 iPhone XS Max)", required = true)
    @NotEmpty(message = "硬件设备类型(示例 iPhone XS Max)")
    private String modelType;
    /**
     * 操作系统版本(示例 13.3)
     */
    @ApiModelProperty(value = "操作系统版本(示例 13.3)", required = true)
    @NotEmpty(message = "操作系统版本(示例 13.3)")
    private String osVersion;
    /**
     * 客户端唯一标识(示例 35EC2387-C8C3-43FC-A642-D5D494E2568E)
     */
    @ApiModelProperty(value = "客户端唯一标识(示例 35EC2387-C8C3-43FC-A642-D5D494E2568E)", required = true)
    @NotEmpty(message = "客户端唯一标识(示例 35EC2387-C8C3-43FC-A642-D5D494E2568E)")
    private String deviceId;
    /**
     * 来源渠道(示例 App Store)
     */
    @ApiModelProperty(value = "来源渠道(示例 App Store)", required = true)
    @NotEmpty(message = "来源渠道(示例 App Store)")
    private String channel;
    /**
     * 城市(示例 南京)
     */
    @ApiModelProperty(value = "城市(示例 南京)", required = true)
    @NotEmpty(message = "城市(示例 南京)")
    private String city;
    /**
     * 用户所用运营商(示例 中国移动)
     */
    @ApiModelProperty(value = "用户所用运营商(示例 中国移动)")
    private String provider;
    /**
     * 登录类型(枚举值 G 访客, R 注册用户)
     */
    @ApiModelProperty(value = "登录类型(枚举值 G 访客, R 注册用户)")
    private String roleType;
    /**
     * 登录账号(示例 13812341234)
     */
    @ApiModelProperty(value = "登录账号(示例 13812341234)")
    private String loginAccount;
    /**
     * 网络类型(枚举值 0 wifi, 1 2G, 2 3G, 3 4G)
     */
    @ApiModelProperty(value = "网络类型(枚举值 0 wifi, 1 2G, 2 3G, 3 4G)")
    private String networkType;
    /**
     * 屏幕分辨率(示例 1242*2208)
     */
    @ApiModelProperty(value = "屏幕分辨率(示例 1242*2208)")
    private String screen;
    /**
     * 地理位置(示例 31.311626;118.358301)
     */
    @ApiModelProperty(value = "地理位置(示例 31.311626;118.358301)")
    private String coordinate;
    /**
     * 事件列表
     */
    @ApiModelProperty(value = "事件列表")
    private List<Params> params;
}
