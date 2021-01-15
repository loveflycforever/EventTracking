package com.apoem.mmxx.eventtracking.infrastructure.dao.mongo.dm;

import com.apoem.mmxx.eventtracking.infrastructure.dao.support.AbstractMapReduceDmDaoV2;
import com.apoem.mmxx.eventtracking.infrastructure.dao.support.TaskLabel;
import com.apoem.mmxx.eventtracking.infrastructure.enums.ActionDefinitionEnum;
import com.apoem.mmxx.eventtracking.infrastructure.enums.PeriodTypeEnum;
import com.apoem.mmxx.eventtracking.infrastructure.enums.ProFieldEnum;
import com.apoem.mmxx.eventtracking.infrastructure.po.dm.StoreStatsEntity;
import com.apoem.mmxx.eventtracking.infrastructure.po.dw.ConsumerAcqDailyDwEntity;
import com.apoem.mmxx.eventtracking.infrastructure.po.ro.MrStoreStatsRo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: StoreStatsDm </p>
 * <p>Description: 门店后台-门店访问量、访客数、收藏数统计 </p>
 * <p>Date: 2020/8/21 9:44 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Repository
@Slf4j
@TaskLabel(name = "维度[城市-门店-条口]客户访问门店日统计", desc = "[VIS_HUS]日周期，统计访问量、访客数", order = 1)
public class StoreStatsDm extends AbstractMapReduceDmDaoV2<StoreStatsEntity, MrStoreStatsRo, ConsumerAcqDailyDwEntity> {

    @Override
    public Query sourceDataCondition(LocalDateTime localDateTime) {
        return Query.query(new Criteria().andOperator(
                Criteria.where(ProFieldEnum.ACTION_DEFINITION.getName())
                        .all(ActionDefinitionEnum.VIS_HUS.getName())));
    }

    @Override
    public StoreStatsEntity convertToTargetData(LocalDateTime localDateTime, MrStoreStatsRo ro) {
        StoreStatsEntity e = new StoreStatsEntity();

        e.setPeriodType(PeriodTypeEnum.DAY1.getName());
        e.setDateDay(Integer.parseInt(dateDayFormat(localDateTime)));

        e.setStoreId(ro.getId().getStoreId());
        e.setHouseType(ro.getId().getHouseType());
        e.setCity(ro.getId().getCity());

        e.setPageView(ro.getValue().getPageView());
        e.setUniqueVisitor(ro.getValue().getUniqueVisitor());
        return e;
    }
}
