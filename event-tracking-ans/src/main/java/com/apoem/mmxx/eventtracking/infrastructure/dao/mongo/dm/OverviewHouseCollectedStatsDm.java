package com.apoem.mmxx.eventtracking.infrastructure.dao.mongo.dm;

import com.apoem.mmxx.eventtracking.infrastructure.dao.support.AbstractMapReduceDmDaoV2;
import com.apoem.mmxx.eventtracking.infrastructure.dao.support.TaskLabel;
import com.apoem.mmxx.eventtracking.infrastructure.enums.ActionDefinitionEnum;
import com.apoem.mmxx.eventtracking.infrastructure.enums.PeriodTypeEnum;
import com.apoem.mmxx.eventtracking.infrastructure.enums.ProFieldEnum;
import com.apoem.mmxx.eventtracking.infrastructure.po.dm.OverviewHouseCollectedStatsEntity;
import com.apoem.mmxx.eventtracking.infrastructure.po.dw.ConsumerAcqDailyDwEntity;
import com.apoem.mmxx.eventtracking.infrastructure.po.ro.MrOverviewHouseCollectedStatsRo;
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
@TaskLabel(name = "维度[城市-录入类型]客户收藏房源日统计", desc = "[COL_HUS]日周期，统计收藏量、收藏数", order = 1)
public class OverviewHouseCollectedStatsDm extends AbstractMapReduceDmDaoV2<OverviewHouseCollectedStatsEntity, MrOverviewHouseCollectedStatsRo, ConsumerAcqDailyDwEntity> {

    @Override
    public Query sourceDataCondition(LocalDateTime localDateTime) {
        return Query.query(Criteria
                .where(ProFieldEnum.ACTION_DEFINITION.getName()).all(
                        ActionDefinitionEnum.COL_HUS.getName()
                ));}

    @Override
    public OverviewHouseCollectedStatsEntity convertToTargetData(LocalDateTime localDateTime, MrOverviewHouseCollectedStatsRo ro) {
        OverviewHouseCollectedStatsEntity entity = new OverviewHouseCollectedStatsEntity();

        entity.setPeriodType(PeriodTypeEnum.DAY1.getName());
        entity.setDateDay(Integer.parseInt(dateDayFormat(localDateTime)));

        entity.setCity(ro.getId().getCity());
        entity.setInputType(ro.getId().getInputType());

        entity.setPageView(ro.getValue().getPageView());
        entity.setUniqueVisitor(ro.getValue().getUniqueVisitor());
        return entity;
    }
}
