package com.apoem.mmxx.eventtracking.infrastructure.po.entity;

import com.apoem.mmxx.eventtracking.infrastructure.po.support.AutoIncrIdEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: TrackPointEntity </p>
 * <p>Description: 实体：埋点实体 </p>
 * <p>Date: 2020/7/14 12:33 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Document(collection="et_track_point")
@CompoundIndexes({
        @CompoundIndex(name = "idx", def = "{'endpoint' : 1, 'avenue' : 1, 'page_name' : 1, 'method_name' : 1, 'event_type' : 1}")
})
public class TrackPointEntity extends AutoIncrIdEntity {

    /**
     * 页面名称
     */
    private String pageName;

    /**
     * 页面中文名称
     */
    private String pageNameCn;

    /**
     * 事件类型（打开页面=VIEW|页面点击=CLICK|页面长按=HOLD）
     */
    private String eventType;

    /**
     * 方法名称（iOS方法名、Android方法名、小程序方法名）
     */
    private String methodName;

    /**
     * 方法中文名称
     */
    private String methodNameCn;

    /**
     * 模块Id
     */
    private Long moduleId;

    /**
     * 操作类型，轨迹用（访问=VISIT|收藏=COLLECT|分享=REPOST|空=EMPTY）
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
     * 渠道（iOS|Android|WMP）
     */
    private String avenue;

    /**
     * 终端（B端=Business|C端=Consumer）
     */
    private String endpoint;
}
