package com.apoem.mmxx.eventtracking.interfaces.assembler;

import com.apoem.mmxx.eventtracking.BeanCopierUtils;
import com.apoem.mmxx.eventtracking.interfaces.dto.trackpoint.TrackPointDeleteRequestDto;
import com.apoem.mmxx.eventtracking.interfaces.dto.trackpoint.TrackPointInsertRequestDto;
import com.apoem.mmxx.eventtracking.interfaces.dto.trackpoint.TrackPointUpdateRequestDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: TrackPointCmd </p>
 * <p>Description: 埋点配置领域DTO </p>
 * <p>Date: 2020/7/31 10:26 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Data
@ToString
@EqualsAndHashCode
public class TrackPointCmd {

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
     * 操作类型，轨迹用（访问=VIS|收藏=COL|分享=RPS|缺省=EMPTY）
     */
    private String actionType;

    /**
     * 动作定义（访问经纪人=VIS_AGT|访问房源=VIS_HUS|使用IM=USE_IM|使用手机=USE_PHN|收藏房源COL_HUS|查看资讯VIS_IFO|缺省EMPTY）
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
     * 转换器
     * @param dto WebAcquisitionDto dto
     * @return 转换结果
     */
    public static TrackPointCmd convertFrom(TrackPointInsertRequestDto dto) {
        TrackPointCmd cmd = new TrackPointCmd();
        BeanCopierUtils.copy(dto, cmd);
        return cmd;
    }

    public static TrackPointCmd convertFrom(TrackPointUpdateRequestDto dto) {
        TrackPointCmd cmd = new TrackPointCmd();
        BeanCopierUtils.copy(dto, cmd);
        return cmd;
    }

    public static TrackPointCmd convertFrom(TrackPointDeleteRequestDto dto) {
        TrackPointCmd cmd = new TrackPointCmd();
        BeanCopierUtils.copy(dto, cmd);
        return cmd;
    }
}
