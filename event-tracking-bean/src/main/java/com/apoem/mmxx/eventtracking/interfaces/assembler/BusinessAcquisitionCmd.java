package com.apoem.mmxx.eventtracking.interfaces.assembler;

import com.apoem.mmxx.eventtracking.BeanCopierUtils;
import com.apoem.mmxx.eventtracking.interfaces.dto.acq.BusinessAcquisitionRequestDto;
import com.apoem.mmxx.eventtracking.interfaces.dto.acq.Params;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: BusinessAcquisitionCmd </p>
 * <p>Description: B端上报数据领域DTO </p>
 * <p>Date: 2020/7/15 13:58 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Data
@ToString(callSuper = true)
public class BusinessAcquisitionCmd {
    /**
     * app名称(示例 房房房)
     */
    private String app;
    /**
     * 应用版本(示例 8.0.93)
     */
    private String appVersion;
    /**
     * 终端类型(枚举值 1 android, 21 ipad, 22 iphone, 20 其它IOS设备, 30 web, 40 小程序)
     */
    private String platform;
    /**
     * 硬件设备类型(示例 iPhone XS Max)
     */
    private String modelType;
    /**
     * 操作系统版本(示例 13.3)
     */
    private String osVersion;
    /**
     * 客户端唯一标识(示例 35EC2387-C8C3-43FC-A642-D5D494E2568E)
     */
    private String deviceId;
    /**
     * 来源渠道(示例 App Store)
     */
    private String channel;
    /**
     * 城市(示例 南京)
     */
    private String city;
    /**
     * 用户所用运营商(示例 中国移动)
     */
    private String provider;
    /**
     * 登录类型(枚举值 G 访客, R 注册用户)
     */
    private String roleType;
    /**
     * 登录账号(示例 13812341234)
     */
    private String loginAccount;
    /**
     * 网络类型(枚举值 0 wifi, 1 2G, 2 3G, 3 4G)
     */
    private String networkType;
    /**
     * 屏幕分辨率(示例 1242*2208)
     */
    private String screen;
    /**
     * 地理位置(示例 31.311626;118.358301)
     */
    private String coordinate;
    /**
     * 事件列表
     */
    private List<Params> params;

    /**
     * 转换器
     * @param dto BusinessAcquisitionRequestDto dto
     * @return 转换结果
     */
    public static BusinessAcquisitionCmd convertFrom(BusinessAcquisitionRequestDto dto) {
        BusinessAcquisitionCmd cmd = new BusinessAcquisitionCmd();
        BeanCopierUtils.copy(dto, cmd);
        return cmd;
    }
}
