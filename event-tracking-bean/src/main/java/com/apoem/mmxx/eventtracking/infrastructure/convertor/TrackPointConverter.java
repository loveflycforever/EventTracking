package com.apoem.mmxx.eventtracking.infrastructure.convertor;

import com.apoem.mmxx.eventtracking.BeanCopierUtils;
import com.apoem.mmxx.eventtracking.domain.trackpointdisposition.model.aggregates.ModuleInfo;
import com.apoem.mmxx.eventtracking.domain.trackpointdisposition.model.aggregates.TrackPoint;
import com.apoem.mmxx.eventtracking.infrastructure.po.entity.TrackPointEntity;
import com.apoem.mmxx.eventtracking.interfaces.assembler.TrackPointCmd;

import java.util.Objects;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: TrackPointConverter </p>
 * <p>Description: 埋点转换器 </p>
 * <p>Date: 2020/8/3 15:09 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
public final class TrackPointConverter {

    private TrackPointConverter() {
    }

    public static TrackPointEntity serialize(TrackPoint trackPoint) {
        TrackPointEntity trackPointEntity = new TrackPointEntity();
        BeanCopierUtils.copy(trackPoint, trackPointEntity);

        ModuleInfo moduleInfo = trackPoint.getModuleInfo();
        if (Objects.nonNull(moduleInfo)) {
            trackPointEntity.setModuleId(moduleInfo.getId());
        }
        return trackPointEntity;
    }

    public static TrackPoint deserialize(TrackPointCmd cmd) {

        TrackPoint trackPoint = new TrackPoint();
        BeanCopierUtils.copy(cmd, trackPoint);

        return trackPoint;
    }
}
