package com.apoem.mmxx.eventtracking.domain.trackpointdisposition.model.aggregates;

import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: TrackPoint </p>
 * <p>Description: 埋点信息领域对象聚合根 </p>
 * <p>Date: 2020/7/31 10:26 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Data
public class TrackPoint {

    /**
     * ID
     */
    private Long id;

    /**
     * 页面名称
     */
    private String pageName;

    /**
     * 页面中文名称
     */
    private String pageNameCn;

    /**
     * 方法名称（iOS、Android或小程序的方法名）
     */
    private String methodName;

    /**
     * 方法中文名称（iOS、Android或小程序的方法中文名）
     */
    private String methodNameCn;

    /**
     * 模块ID
     */
    private Long moduleId;

    /**
     * 事件类型（打开页面=VIEW|页面点击=CLICK|页面长按=HOLD）
     */
    private String eventType;

    /**
     * 操作类型，轨迹用（访问=VIS|收藏=COL|分享=RPS|空=EMPTY）
     */
    private String actionType;

    /**
     * 动作定义（访问经纪人=VIS_AGT|访问房源=VIS_HUS|使用IM=USE_IM|使用手机=USE_PHN|收藏房源COL_HUS|查看资讯VIS_IFO）
     */
    private String[] actionDefinition;

    /**
     * 渠道（iOS|Android|WMP）
     */
    private String avenue;

    /**
     * 终端（B端=Business|C端=Consumer）
     */
    private String endpoint;

    /**
     * 模块信息
     */
    private ModuleInfo moduleInfo;
}
