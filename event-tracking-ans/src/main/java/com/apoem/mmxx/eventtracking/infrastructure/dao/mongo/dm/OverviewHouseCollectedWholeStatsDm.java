package com.apoem.mmxx.eventtracking.infrastructure.dao.mongo.dm;

import com.apoem.mmxx.eventtracking.infrastructure.dao.support.AbstractMapReduceDmDaoV2;
import com.apoem.mmxx.eventtracking.infrastructure.dao.support.TaskLabel;
import com.apoem.mmxx.eventtracking.infrastructure.enums.ActionDefinitionEnum;
import com.apoem.mmxx.eventtracking.infrastructure.enums.PeriodTypeEnum;
import com.apoem.mmxx.eventtracking.infrastructure.enums.ProFieldEnum;
import com.apoem.mmxx.eventtracking.infrastructure.po.dm.OverviewHouseCollectedStatsEntity;
import com.apoem.mmxx.eventtracking.infrastructure.po.dw.ConsumerAcqDailyDwEntity;
import com.apoem.mmxx.eventtracking.infrastructure.po.ro.MrOverviewHouseCollectedStatsWholeRo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: OverviewStatsDm </p>
 * <p>Description: 运营后台-总览 </p>
 * <p>Date: 2020/8/21 9:44 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Repository
@Slf4j
@TaskLabel(name = "维度[城市]日总量统计", desc = "[COL_HUS]日周期，统计收藏量、收藏数", order = 1)
public class OverviewHouseCollectedWholeStatsDm extends AbstractMapReduceDmDaoV2<OverviewHouseCollectedStatsEntity, MrOverviewHouseCollectedStatsWholeRo, ConsumerAcqDailyDwEntity> {

    @Override
    public Query sourceDataCondition(LocalDateTime localDateTime) {
        return Query.query(Criteria
                .where(ProFieldEnum.ACTION_DEFINITION.getName()).all(
                        ActionDefinitionEnum.COL_HUS.getName()
                ));}

    @Override
    public boolean removeHistory() {
        return true;
    }

    @Override
    public Query removeHistoryCondition(LocalDateTime localDateTime) {
        return Query.query(new Criteria().andOperator(
                Criteria.where(ProFieldEnum.DATE_DAY.getName()).is(Integer.parseInt(dateDayFormat(localDateTime))),
                Criteria.where(ProFieldEnum.INPUT_TYPE.getName()).is("WHOLE"),
                Criteria.where(ProFieldEnum.PERIOD_TYPE.getName()).is(PeriodTypeEnum.DAY1.getName())));
    }

    @Override
    public OverviewHouseCollectedStatsEntity convertToTargetData(LocalDateTime localDateTime, MrOverviewHouseCollectedStatsWholeRo ro) {
        OverviewHouseCollectedStatsEntity entity = new OverviewHouseCollectedStatsEntity();

        entity.setPeriodType(PeriodTypeEnum.DAY1.getName());
        entity.setDateDay(Integer.parseInt(dateDayFormat(localDateTime)));

        entity.setCity(ro.getId().getCity());
        entity.setInputType("WHOLE");

        entity.setPageView(ro.getValue().getPageView());
        entity.setUniqueVisitor(ro.getValue().getUniqueVisitor());
        return entity;
    }
}
