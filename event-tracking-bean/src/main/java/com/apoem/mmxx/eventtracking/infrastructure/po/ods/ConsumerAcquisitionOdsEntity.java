package com.apoem.mmxx.eventtracking.infrastructure.po.ods;

import com.apoem.mmxx.eventtracking.infrastructure.dao.support.Sharding;
import com.apoem.mmxx.eventtracking.infrastructure.po.support.AutoIncrIdEntity;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: ConsumerAcquisitionOdsEntity </p>
 * <p>Description: C 端业务请求流水实体 </p>
 * <p>Date: 2020/7/16 9:35 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Sharding
@Document(collection="et_ods_consumer_acquisition")
public class ConsumerAcquisitionOdsEntity extends AutoIncrIdEntity {
    /**
     * 用户所选城市
     */
    private String city;

    /**
     * 操作时间（13位时间戳）
     */
    private Date opTime;

    /**
     * 小程序名称
     */
    private String appId;

    /**
     * 小程序版本号
     */
    private String appVersion;

    /**
     * 小程序用户 openid
     */
    private String openId;

    /**
     * 小程序用户 uniqueId
     */
    private String uniqueId;

    // ==================================================

    /**
     * 设备品牌
     */
    private String brand;
    /**
     * 设备型号
     */
    private String model;
    /**
     * 设备像素比
     */
    private String pixelRatio;
    /**
     * 屏幕宽度，单位px
     */
    private String screenWidth;
    /**
     * 屏幕高度，单位px
     */
    private String screenHeight;
    /**
     * 可使用窗口宽度，单位px
     */
    private String windowWidth;
    /**
     * 可使用窗口高度，单位px
     */
    private String windowHeight;
    /**
     * 状态栏的高度，单位px
     */
    private String statusBarHeight;
    /**
     * 微信版本号
     */
    private String wxVersion;
    /**
     * 操作系统及版本
     */
    private String system;
    /**
     * 客户端平台
     */
    private String platform;
    /**
     * 客户端基础库版本
     */
    private String sdkVersion;

    // ==================================================

    /**
     * 渠道（iOS|Android|WMP）
     */
    private String avenue;

    /**
     * 页面名称
     */
    private String pageName;

    /**
     * 方法名称
     */
    private String methodName;

    /**
     * 事件类型
     */
    private String eventType;

    /**
     * 进入时间（时间戳）
     */
    private Date startTime;
    /**
     * 退出时间（时间戳）
     */
    private Date endTime;

    // =============================

    /**
     * 自定义内容
     */
    private String content;
}
