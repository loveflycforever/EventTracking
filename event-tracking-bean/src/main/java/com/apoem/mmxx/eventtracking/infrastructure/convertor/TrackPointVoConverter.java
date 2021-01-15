package com.apoem.mmxx.eventtracking.infrastructure.convertor;

import com.apoem.mmxx.eventtracking.BeanCopierUtils;
import com.apoem.mmxx.eventtracking.infrastructure.enums.AvenueEnum;
import com.apoem.mmxx.eventtracking.infrastructure.enums.EndpointEnum;
import com.apoem.mmxx.eventtracking.infrastructure.enums.ActionDefinitionEnum;
import com.apoem.mmxx.eventtracking.infrastructure.enums.ActionTypeEnum;
import com.apoem.mmxx.eventtracking.infrastructure.enums.EventTypeEnum;
import com.apoem.mmxx.eventtracking.domain.trackpointdisposition.model.vo.TrackPointVo;
import com.apoem.mmxx.eventtracking.infrastructure.po.entity.TrackPointEntity;

import java.util.Arrays;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: TrackPointVoConverter </p>
 * <p>Description: 埋点转换器 </p>
 * <p>Date: 2020/8/3 15:09 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
public final class TrackPointVoConverter {

    private TrackPointVoConverter() {
    }

    public static TrackPointVo deserialize(TrackPointEntity trackPointEntity) {
        TrackPointVo trackPointVo = new TrackPointVo();
        BeanCopierUtils.copy(trackPointEntity, trackPointVo);

        trackPointVo.setEndpointVal(EndpointEnum.getDesc(trackPointVo.getEndpoint()));
        trackPointVo.setAvenueVal(AvenueEnum.getDesc(trackPointVo.getAvenue()));
        trackPointVo.setEventTypeVal(EventTypeEnum.getDesc(trackPointVo.getEventType()));
        trackPointVo.setActionTypeVal(ActionTypeEnum.getDesc(trackPointVo.getActionType()));
        String[] array = Arrays.stream(trackPointVo.getActionDefinition())
                .map(ActionDefinitionEnum::getDesc)
                .toArray(String[]::new);
        trackPointVo.setActionDefinitionVal(array);

        return trackPointVo;
    }
}
