package com.apoem.mmxx.eventtracking.infrastructure.convertor;

import com.apoem.mmxx.eventtracking.DateUtils;
import com.apoem.mmxx.eventtracking.infrastructure.po.dm.CustomerTrailEntity;
import com.apoem.mmxx.eventtracking.domain.analysis.model.vo.CustomerHouseTrailVo;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: CustomerHouseTrailVoConverter </p>
 * <p>Description:  </p>
 * <p>Date: 2020/8/28 10:24 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
public class CustomerHouseTrailVoConverter {
    public static CustomerHouseTrailVo deserialize(List<CustomerTrailEntity> entity, List<CustomerTrailEntity> entities) {
        CustomerHouseTrailVo result = new CustomerHouseTrailVo();
        CustomerHouseTrailVo.HouseTrailRowVo lastVisit = new CustomerHouseTrailVo.HouseTrailRowVo();
        if(CollectionUtils.isNotEmpty(entity)) {
            lastVisit = deserialize(entity.get(0));
        }
        List<CustomerHouseTrailVo.HouseTrailRowVo> rows = Collections.emptyList();
        if (CollectionUtils.isNotEmpty(entities)) {
            rows = entities.stream()
                    .map(CustomerHouseTrailVoConverter::deserialize)
                    .collect(Collectors.toList());
        }

        result.setLastOperation(lastVisit);
        result.setRows(rows);
        return result;
    }

    public static CustomerHouseTrailVo.HouseTrailRowVo deserialize(CustomerTrailEntity entity) {
        CustomerHouseTrailVo.HouseTrailRowVo result = new CustomerHouseTrailVo.HouseTrailRowVo();

        Optional.ofNullable(entity).ifPresent(o -> {
            result.setCustomerId(o.getCustomerId());
            result.setDate(DateUtils.dateString(o.getOpTime()));
            result.setNumber(String.valueOf(o.getViewTimes()));
            result.setScope(o.getActionType());
            result.setDef(o.getActionDefinition());
            result.setDuration(String.valueOf(o.getDuration()));
            result.setContent(o.getContent());
            result.setNextKey(String.valueOf(o.getIdKey()));
        });
        return result;
    }
}
