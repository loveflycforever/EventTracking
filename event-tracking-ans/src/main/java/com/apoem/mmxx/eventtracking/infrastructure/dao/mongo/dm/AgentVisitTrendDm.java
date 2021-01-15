package com.apoem.mmxx.eventtracking.infrastructure.dao.mongo.dm;

import com.apoem.mmxx.eventtracking.infrastructure.dao.support.AbstractMapReduceDmDaoV2;
import com.apoem.mmxx.eventtracking.infrastructure.dao.support.TaskLabel;
import com.apoem.mmxx.eventtracking.infrastructure.enums.ActionDefinitionEnum;
import com.apoem.mmxx.eventtracking.infrastructure.enums.PeriodTypeEnum;
import com.apoem.mmxx.eventtracking.infrastructure.enums.ProFieldEnum;
import com.apoem.mmxx.eventtracking.infrastructure.po.dm.AgentVisitTrendEntity;
import com.apoem.mmxx.eventtracking.infrastructure.po.dw.ConsumerAcqDailyDwEntity;
import com.apoem.mmxx.eventtracking.infrastructure.po.ro.MrAgentVisitTrendRo;
import com.apoem.mmxx.eventtracking.infrastructure.po.ro.support.MrStandardValue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: AgentVisitTrendDm </p>
 * <p>Description: 效果分析-经纪人日趋势-新房、二手房、租房 </p>
 * <p>Date: 2020/7/14 12:47 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Repository
@Slf4j
@TaskLabel(name = "维度[城市-经纪人-条口]客户访问经纪人日统计", desc = "[VIS_AGT]日周期，统计访问量、访客数", order = 1)
public class AgentVisitTrendDm extends AbstractMapReduceDmDaoV2<AgentVisitTrendEntity, MrAgentVisitTrendRo, ConsumerAcqDailyDwEntity> {

    @Override
    protected boolean hasCrossData() {
        return true;
    }

    @Override
    public Query crossDataCondition(LocalDateTime localDateTime) {
        String yesterday = dateDayFormat(localDateTime.minusDays(1));
        return Query.query(new Criteria().andOperator(
                Criteria.where(ProFieldEnum.DATE_DAY.getName()).is(Integer.parseInt(yesterday)),
                Criteria.where(ProFieldEnum.PERIOD_TYPE.getName()).is(PeriodTypeEnum.DAY1.getName())));
    }

    @Override
    public Query sourceDataCondition(LocalDateTime localDateTime) {
        return Query.query(new Criteria().andOperator(
                Criteria.where(ProFieldEnum.ACTION_DEFINITION.getName())
                        .all(ActionDefinitionEnum.VIS_HUS.getName()),
                Criteria.where(ProFieldEnum.ACTION_DEFINITION.getName())
                        .all(ActionDefinitionEnum.VIS_AGT.getName())));
    }

    @Override
    public AgentVisitTrendEntity convertToTargetData(LocalDateTime localDateTime, MrAgentVisitTrendRo o) {
        MrAgentVisitTrendRo.Key key = o.getId();
        MrStandardValue value = o.getValue();
        AgentVisitTrendEntity entity = new AgentVisitTrendEntity();
        entity.setPeriodType(PeriodTypeEnum.DAY1.getName());

        entity.setAgentId(key.getAgentId());
        entity.setCity(key.getCity());
        entity.setHouseType(key.getHouseType());

        entity.setPageView(value.getPageView());
        entity.setUniqueVisitor(value.getUniqueVisitor());
        return entity;
    }

    @Override
    public void afterTargetData(LocalDateTime localDateTime, List<AgentVisitTrendEntity> crossData, List<AgentVisitTrendEntity> mrConvertedData) {
        List<AgentVisitTrendEntity> result = AgentTrendCrossUtils.exe(localDateTime, crossData, mrConvertedData);
        result.forEach(mongoTemplate()::save);
    }

}
