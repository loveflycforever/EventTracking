package com.apoem.mmxx.eventtracking.application.service;

import com.apoem.mmxx.eventtracking.domain.trackpointdisposition.model.vo.TrackPointVo;
import com.apoem.mmxx.eventtracking.interfaces.assembler.TrackPointCmd;
import com.apoem.mmxx.eventtracking.interfaces.dto.trackpoint.TrackPointListRequestDto;

import java.util.List;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: TrackPointDispositionService </p>
 * <p>Description: 应用层服务：埋点配置服务 </p>
 * <p>Date: 2020/7/14 12:53 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
public interface TrackPointDispositionService {

    /**
     * 配置埋点信息
     * @param cmd 数据对象
     */
    void appendTrackPoint(TrackPointCmd cmd);

    /**
     * 配置埋点信息
     * @param cmd 数据对象
     */
    void modifyTrackPoint(TrackPointCmd cmd);

    /**
     * 配置埋点信息
     * @param cmd 数据对象
     */
    void removeTrackPoint(TrackPointCmd cmd);

    /**
     * 列表
     * @param dto 数据模型
     * @return 结果
     */
    Integer listTrackPointSize(TrackPointListRequestDto dto);

    /**
     * 配置埋点信息
     * @return 列表
     * @param dto 参数
     */
    List<TrackPointVo> listTrackPoint(TrackPointListRequestDto dto);
}
