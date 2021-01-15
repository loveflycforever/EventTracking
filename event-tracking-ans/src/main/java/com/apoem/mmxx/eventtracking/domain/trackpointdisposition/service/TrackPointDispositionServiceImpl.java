package com.apoem.mmxx.eventtracking.domain.trackpointdisposition.service;

import com.apoem.mmxx.eventtracking.application.service.TrackPointDispositionService;
import com.apoem.mmxx.eventtracking.domain.trackpointdisposition.model.vo.TrackPointVo;
import com.apoem.mmxx.eventtracking.domain.trackpointdisposition.service.engine.TrackPointManager;
import com.apoem.mmxx.eventtracking.interfaces.assembler.TrackPointCmd;
import com.apoem.mmxx.eventtracking.interfaces.dto.trackpoint.TrackPointListRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: TrackPointDispositionServiceImpl </p>
 * <p>Description: 应用层服务：埋点配置服务 </p>
 * <p>Date: 2020/7/14 12:53 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Service
public class TrackPointDispositionServiceImpl implements TrackPointDispositionService {

    private final TrackPointManager trackPointManager;

    @Autowired
    public TrackPointDispositionServiceImpl(TrackPointManager trackPointManager) {
        this.trackPointManager = trackPointManager;
    }

    @Override
    public void appendTrackPoint(TrackPointCmd cmd) {
        configTrackPoint(cmd, false);
    }

    @Override
    public void modifyTrackPoint(TrackPointCmd cmd) {
        configTrackPoint(cmd, true);
    }

    @Override
    public void removeTrackPoint(TrackPointCmd cmd) {
        trackPointManager.checksum(cmd, true);
        trackPointManager.remove(cmd);
    }

    @Override
    public Integer listTrackPointSize(TrackPointListRequestDto dto) {
        return trackPointManager.listSize(dto);
    }

    @Override
    public List<TrackPointVo> listTrackPoint(TrackPointListRequestDto dto) {
        return trackPointManager.list(dto);
    }

    private void configTrackPoint(TrackPointCmd cmd, boolean update) {
        trackPointManager.checksum(cmd, false);
        trackPointManager.save(cmd, update);
    }
}
