package com.apoem.mmxx.eventtracking.infrastructure.convertor;

import com.apoem.mmxx.eventtracking.DateUtils;
import com.apoem.mmxx.eventtracking.infrastructure.po.dm.CustomerTrailEntity;
import com.apoem.mmxx.eventtracking.domain.analysis.model.vo.CustomerTrailVo;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: CustomerTrailVoConverter </p>
 * <p>Description:  </p>
 * <p>Date: 2020/8/28 10:24 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
public class CustomerTrailVoConverter {
    public static List<CustomerTrailVo> deserialize(List<CustomerTrailEntity> entities) {
        List<CustomerTrailVo> result = Collections.emptyList();
        if (CollectionUtils.isNotEmpty(entities)) {
            result = entities.stream()
                    .map(CustomerTrailVoConverter::deserialize)
                    .collect(Collectors.toList());
        }
        return result;
    }

    private static CustomerTrailVo deserialize(CustomerTrailEntity entity) {
        CustomerTrailVo result = new CustomerTrailVo();

        Optional.ofNullable(entity).ifPresent(o -> {
            result.setDate(DateUtils.dateString(o.getOpTime()));
            result.setScope(o.getActionType());
            result.setDef(o.getActionDefinition());
            result.setNumber(String.valueOf(o.getViewTimes()));
            result.setContent(o.getContent());
            result.setDuration(String.valueOf(o.getDuration()));
            result.setNextKey(String.valueOf(o.getIdKey()));
        });
        return result;
    }
}
