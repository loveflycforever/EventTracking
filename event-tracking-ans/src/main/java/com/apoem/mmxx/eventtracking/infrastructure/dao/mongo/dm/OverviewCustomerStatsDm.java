package com.apoem.mmxx.eventtracking.infrastructure.dao.mongo.dm;

import com.apoem.mmxx.eventtracking.infrastructure.dao.support.AbstractMapReduceDmDaoV2;
import com.apoem.mmxx.eventtracking.infrastructure.dao.support.TaskLabel;
import com.apoem.mmxx.eventtracking.infrastructure.enums.ActionDefinitionEnum;
import com.apoem.mmxx.eventtracking.infrastructure.enums.PeriodTypeEnum;
import com.apoem.mmxx.eventtracking.infrastructure.enums.ProFieldEnum;
import com.apoem.mmxx.eventtracking.infrastructure.po.dm.OverviewCustomerStatsEntity;
import com.apoem.mmxx.eventtracking.infrastructure.po.dw.ConsumerAcqDailyDwEntity;
import com.apoem.mmxx.eventtracking.infrastructure.po.ro.MrOverviewCustomerStatsRo;
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
@TaskLabel(name = "维度[城市]客户访问日统计", desc = "[CUS_USE]日周期，统计访问量、访客数", order = 1)
public class OverviewCustomerStatsDm extends AbstractMapReduceDmDaoV2<OverviewCustomerStatsEntity, MrOverviewCustomerStatsRo, ConsumerAcqDailyDwEntity> {

    @Override
    public Query sourceDataCondition(LocalDateTime localDateTime) {
        return Query.query(new Criteria().orOperator(
                Criteria.where(ProFieldEnum.ACTION_DEFINITION.getName())
                        .all(ActionDefinitionEnum.CUS_USE.getName())));
    }

    @Override
    public OverviewCustomerStatsEntity convertToTargetData(LocalDateTime localDateTime, MrOverviewCustomerStatsRo ro) {
        OverviewCustomerStatsEntity entity = new OverviewCustomerStatsEntity();

        entity.setPeriodType(PeriodTypeEnum.DAY1.getName());
        entity.setDateDay(Integer.parseInt(dateDayFormat(localDateTime)));

        entity.setCity(ro.getId().getCity());

        entity.setPageView(ro.getValue().getPageView());
        entity.setUniqueVisitor(ro.getValue().getUniqueVisitor());
        return entity;
    }
}
