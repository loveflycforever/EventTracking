package com.apoem.mmxx.eventtracking.domain.acquisition.model.aggregates;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: TrackPointInfo </p>
 * <p>Description: 上报的埋点信息 </p>
 * <p>Date: 2020/7/17 11:10 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TrackPointInfo {

    /**
     * 渠道（iOS|Android|WMP）
     */
    private String avenue;

    /**
     * 方法名称
     */
    private String methodName;

    /**
     * 操作类型，轨迹用（访问=VIS|收藏=COL|分享=RPS|空=EMPTY）
     */
    private String actionType;

    /**
     * 操作定义（访问经纪人=VIS_AGT|访问房源=VIS_HUS|查看资讯VIS_IFO|
     * 使用IM=USE_IM|使用手机=USE_PHN|
     * 收藏经纪人COL_AGT|收藏房源COL_HUS|收藏资讯COL_IFO|
     * 转发资讯RPS_IFO|
     * 空=EMPTY）
     */
    private String[] actionDefinition;

    /**
     * 进入时间（时间戳）
     */
    private Date startTime;
    /**
     * 退出时间（时间戳）
     */
    private Date endTime;
}
