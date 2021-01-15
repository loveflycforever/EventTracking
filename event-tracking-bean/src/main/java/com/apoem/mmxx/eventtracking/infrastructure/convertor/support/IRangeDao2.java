package com.apoem.mmxx.eventtracking.infrastructure.convertor.support;

import com.apoem.mmxx.eventtracking.infrastructure.enums.RangeTypeEnum;
import com.apoem.mmxx.eventtracking.infrastructure.po.entity.RangeEntity;
import com.mongodb.client.result.UpdateResult;

import java.util.List;

public interface IRangeDao2 {

    UpdateResult makeDateDayHistory(Integer dateDay, String cityCode, RangeTypeEnum rangeTypeEnum);
    UpdateResult deletePrevDateDay(Integer dateDay, String cityCode, RangeTypeEnum rangeTypeEnum);
    UpdateResult deleteDateDayHistory(Integer dateDay, String cityCode, RangeTypeEnum rangeTypeEnum);
    List<RangeEntity> findByCityAndRangeTypeName(String cityCode, RangeTypeEnum rangeTypeEnum);
}
