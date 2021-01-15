package com.apoem.mmxx.eventtracking.infrastructure.dao.mongo.dm;

import com.apoem.mmxx.eventtracking.infrastructure.dao.support.AbstractMapReduceDmDaoV2;
import com.apoem.mmxx.eventtracking.infrastructure.dao.support.TaskLabel;
import com.apoem.mmxx.eventtracking.infrastructure.enums.ActionDefinitionEnum;
import com.apoem.mmxx.eventtracking.infrastructure.enums.HouseTypeEnum;
import com.apoem.mmxx.eventtracking.infrastructure.enums.PeriodTypeEnum;
import com.apoem.mmxx.eventtracking.infrastructure.enums.ProFieldEnum;
import com.apoem.mmxx.eventtracking.infrastructure.po.dm.AgentH5StatsEntity;
import com.apoem.mmxx.eventtracking.infrastructure.po.dw.ConsumerAcqDailyDwEntity;
import com.apoem.mmxx.eventtracking.infrastructure.po.ro.MrAgentH5StatsWholeRo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: AgentH5StatsWholeDm </p>
 * <p>Description: 运营后台-经纪人统计-H5 </p>
 * <p>Date: 2020/8/21 9:44 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Repository
@Slf4j
@TaskLabel(name = "维度[城市-经纪人]客户访问房源H5日统计", desc = "[VIS_AGT_H5]日周期，统计访问量、访客数", order = 1)
public class AgentH5StatsWholeDm extends AbstractMapReduceDmDaoV2<AgentH5StatsEntity, MrAgentH5StatsWholeRo, ConsumerAcqDailyDwEntity> {

    @Override
    public Query removeHistoryCondition(LocalDateTime localDateTime) {
        return Query.query(new Criteria().andOperator(
                Criteria.where(ProFieldEnum.DATE_DAY.getName()).is(Integer.parseInt(dateDayFormat(localDateTime))),
                Criteria.where(ProFieldEnum.PERIOD_TYPE.getName()).is(PeriodTypeEnum.DAY1.getName()),
                Criteria.where(ProFieldEnum.HOUSE_TYPE.getName()).is(HouseTypeEnum.WHOLE.getName())));
    }

    @Override
    public Query sourceDataCondition(LocalDateTime localDateTime) {
        return Query.query(new Criteria().orOperator(
                Criteria.where(ProFieldEnum.ACTION_DEFINITION.getName())
                        .all(ActionDefinitionEnum.VIS_AGT_H5.getName())));
    }

    @Override
    public AgentH5StatsEntity convertToTargetData(LocalDateTime localDateTime, MrAgentH5StatsWholeRo ro) {

        AgentH5StatsEntity entity = new AgentH5StatsEntity();

        entity.setDateDay(Integer.parseInt(dateDayFormat(localDateTime)));
        entity.setPeriodType(PeriodTypeEnum.DAY1.getName());

        entity.setAgentId(ro.getId().getAgentId());
        entity.setCity(ro.getId().getCity());
        entity.setHouseType(HouseTypeEnum.WHOLE.getName());

        entity.setPageView(ro.getValue().getPageView());
        entity.setUniqueVisitor(ro.getValue().getUniqueVisitor());
        return entity;
    }
}
