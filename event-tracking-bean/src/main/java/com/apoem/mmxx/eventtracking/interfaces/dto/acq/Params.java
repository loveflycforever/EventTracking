package com.apoem.mmxx.eventtracking.interfaces.dto.acq;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Params {

    /**
     * 未知
     */
    private String id;

    /**
     * 事件类型(枚举值 start, page, click, api, qrode, search, call, sign, adclick, im, register, login, share, push, didi, crash, performance)
     */
    private String key;
    /**
     * 操作时间(毫秒unix时间戳)(示例 1569316260227)
     */
    private String opTime;
    /**
     * 会话ID(示例 824D14C6-DB55-47B6-9879-4420CC89F969)
     */
    private String sessionId;
    /**
     * 页面编号(示例 NewHouseSaleInfoViewController)
     */
    private String pageId;
    /**
     * 页面名称(示例 NewHouseSaleInfoViewController)
     */
    private String pageName;
    /**
     * 广告ID(示例 55064)
     */
    private String adId;
    /**
     * 广告内容(示例 启动动画广告)
     */
    private String adDesc;
    /**
     * 请求类型(示例 getAdListByPosition)
     */
    private String requestType;
    /**
     * 请求体(示例 942)
     */
    private String requestBody;
    /**
     * 响应(示例 Success)
     */
    private String response;
    /**
     * 拨打的电话号码(示例 4008181365转150083)
     */
    private String telNo;
    /**
     * 元素编号(示例 shanyan_init)
     */
    private String objectId;
    /**
     * 事件对象(示例 1023-requestPreLogin(){"result":80800,"msg":"WIFI切换超时","reqId":"ed56d030e98f3603888435d2e58b6e0f"})
     */
    private String eventObj;
    /**
     * 对方ID
     */
    private String friendId;
    /**
     * 进入时间(毫秒unix时间戳)(示例 1569316260227)
     */
    private String startTime;
    /**
     * 离开时间(毫秒unix时间戳)(示例 1569316260227)
     */
    private String endTime;
    /**
     * 序号(示例 3)
     */
    private String pagination;
    /**
     * 目标页编号(示例 com.unknown.library.ui.user.TaofangCoinActivty)
     */
    private String targetPageId;
    /**
     * 二维码所属渠道(示例 分享微信好友)
     */
    private String codeChannel;
    /**
     * 注册账号(示例 13812341234)
     */
    private String accountName;
    /**
     * 搜索关键词(示例 亚东)
     */
    private String searchKey;
    /**
     * 搜索关键词(示例 1)
     */
    private String searchType;
    /**
     * 内容ID(示例 1-253756)
     */
    private String contextId;
    /**
     * 模板ID(示例 1-253756)
     */
    private String posterCode;

    /**
     * 内容
     */
    private String content;

    /**
     * 202011 APP设计存数据字段
     */
    private String businessData;
    private String commonValue;

}