package com.apoem.mmxx.eventtracking.domain.acquisition.model.aggregates;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: BusinessInfo </p>
 * <p>Description: 业务信息 </p>
 * <p>Date: 2020/7/22 15:30 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BusinessInfo {

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
     * 未知
     */
    private String paramId;

    /**
     * 事件类型(枚举值 start, page, click, api, qrode, search, call, sign, adclick, im, register, login, share, push, didi, crash, performance)
     */
    private String paramKey;
    /**
     * 操作时间(毫秒unix时间戳)(示例 1569316260227)
     */
    private String paramOpTime;
    /**
     * 会话ID(示例 824D14C6-DB55-47B6-9879-4420CC89F969)
     */
    private String paramSessionId;
    /**
     * 页面编号(示例 NewHouseSaleInfoViewController)
     */
    private String paramPageId;
    /**
     * 页面名称(示例 NewHouseSaleInfoViewController)
     */
    private String paramPageName;
    /**
     * 广告ID(示例 55064)
     */
    private String paramAdId;
    /**
     * 广告内容(示例 启动动画广告)
     */
    private String paramAdDesc;
    /**
     * 请求类型(示例 getAdListByPosition)
     */
    private String paramRequestType;
    /**
     * 请求体(示例 942)
     */
    private String paramRequestBody;
    /**
     * 响应(示例 Success)
     */
    private String paramResponse;
    /**
     * 拨打的电话号码(示例 4008181365转150083)
     */
    private String paramTelNo;
    /**
     * 元素编号(示例 shanyan_init)
     */
    private String paramObjectId;
    /**
     * 事件对象(示例 1023-requestPreLogin(){"result":80800,"msg":"WIFI切换超时","reqId":"ed56d030e98f3603888435d2e58b6e0f"})
     */
    private String paramEventObj;
    /**
     * 对方ID
     */
    private String paramFriendId;
    /**
     * 进入时间(毫秒unix时间戳)(示例 1569316260227)
     */
    private String paramStartTime;
    /**
     * 离开时间(毫秒unix时间戳)(示例 1569316260227)
     */
    private String paramEndTime;
    /**
     * 序号(示例 3)
     */
    private String paramPagination;
    /**
     * 目标页编号(示例 com.unknown.library.ui.user.TaofangCoinActivty)
     */
    private String paramTargetPageId;
    /**
     * 二维码所属渠道(示例 分享微信好友)
     */
    private String paramCodeChannel;
    /**
     * 注册账号(示例 13812341234)
     */
    private String paramAccountName;
    /**
     * 搜索关键词(示例 亚东)
     */
    private String paramSearchKey;
    /**
     * 搜索关键词(示例 1)
     */
    private String paramSearchType;
    /**
     * 内容ID(示例 1-253756)
     */
    private String paramContextId;
    /**
     * 内容ID(示例 1-253756)
     */
    private String paramPosterCode;

    /**
     * 内容
     */
    private String paramContent;

    /**
     * 202011 APP设计存数据字段
     */
    private String paramBusinessData;
    private String paramCommonValue;

}
