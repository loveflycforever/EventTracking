package com.apoem.mmxx.eventtracking.infrastructure.dao.mongo.dm;

import com.apoem.mmxx.eventtracking.infrastructure.dao.support.AbstractMapReduceDmDaoV2;
import com.apoem.mmxx.eventtracking.infrastructure.dao.support.TaskLabel;
import com.apoem.mmxx.eventtracking.infrastructure.enums.ActionDefinitionEnum;
import com.apoem.mmxx.eventtracking.infrastructure.enums.PeriodTypeEnum;
import com.apoem.mmxx.eventtracking.infrastructure.enums.ProFieldEnum;
import com.apoem.mmxx.eventtracking.infrastructure.po.dm.OverviewAgentStatsEntity;
import com.apoem.mmxx.eventtracking.infrastructure.po.dw.BusinessAcqDailyDwEntity;
import com.apoem.mmxx.eventtracking.infrastructure.po.ro.MrOverviewAgentStatsRo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: AgentStatsDm </p>
 * <p>Description: 运营后台-经纪人统计 </p>
 * <p>Date: 2020/8/21 9:44 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Repository
@Slf4j
@TaskLabel(name = "维度[城市]经纪人访问日统计", desc = "[AGT_USE]日周期，统计访问量、访客数", order = 1)
public class OverviewAgentStatsDm extends AbstractMapReduceDmDaoV2<OverviewAgentStatsEntity, MrOverviewAgentStatsRo, BusinessAcqDailyDwEntity> {

    @Override
    public Query sourceDataCondition(LocalDateTime localDateTime) {
        return Query.query(new Criteria().orOperator(
                Criteria.where(ProFieldEnum.ACTION_DEFINITION.getName())
                        .all(ActionDefinitionEnum.AGT_USE.getName())));
    }

    @Override
    public OverviewAgentStatsEntity convertToTargetData(LocalDateTime localDateTime, MrOverviewAgentStatsRo ro) {
        OverviewAgentStatsEntity entity = new OverviewAgentStatsEntity();

        entity.setDateDay(Integer.parseInt(dateDayFormat(localDateTime)));
        entity.setPeriodType(PeriodTypeEnum.DAY1.getName());

        entity.setCity(ro.getId().getCity());

        entity.setPageView(ro.getValue().getPageView());
        entity.setUniqueVisitor(ro.getValue().getUniqueVisitor());
        return entity;
    }
}
