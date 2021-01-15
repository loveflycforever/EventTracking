package com.apoem.mmxx.eventtracking.infrastructure.dao.mongo.dm;

import com.apoem.mmxx.eventtracking.infrastructure.dao.support.AbstractMapReduceDmDaoV2;
import com.apoem.mmxx.eventtracking.infrastructure.dao.support.TaskLabel;
import com.apoem.mmxx.eventtracking.infrastructure.enums.ActionDefinitionEnum;
import com.apoem.mmxx.eventtracking.infrastructure.enums.HouseTypeEnum;
import com.apoem.mmxx.eventtracking.infrastructure.enums.PeriodTypeEnum;
import com.apoem.mmxx.eventtracking.infrastructure.enums.ProFieldEnum;
import com.apoem.mmxx.eventtracking.infrastructure.po.dm.AgentVisitTrendEntity;
import com.apoem.mmxx.eventtracking.infrastructure.po.dw.ConsumerAcqDailyDwEntity;
import com.apoem.mmxx.eventtracking.infrastructure.po.ro.MrAgentVisitTrendWholeRo;
import com.apoem.mmxx.eventtracking.infrastructure.po.ro.support.MrStandardValue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: AgentVisitTrendWholeDm </p>
 * <p>Description: 效果分析-经纪人日趋势-全部 </p>
 * <p>Date: 2020/7/14 12:47 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Repository
@Slf4j
@TaskLabel(name = "维度[城市-经纪人]日总量统计", desc = "[VIS_HUS]日周期，统计访问量、访客数", order = 1)
public class AgentVisitTrendWholeDm extends AbstractMapReduceDmDaoV2<AgentVisitTrendEntity, MrAgentVisitTrendWholeRo, ConsumerAcqDailyDwEntity> {

    @Override
    public Query removeHistoryCondition(LocalDateTime localDateTime) {
        return Query.query(new Criteria().andOperator(
                Criteria.where(ProFieldEnum.DATE_DAY.getName()).is(Integer.parseInt(dateDayFormat(localDateTime))),
                Criteria.where(ProFieldEnum.PERIOD_TYPE.getName()).is(PeriodTypeEnum.DAY1.getName()),
                Criteria.where(ProFieldEnum.HOUSE_TYPE.getName()).is(HouseTypeEnum.WHOLE.getName())));
    }

    @Override
    public Query crossDataCondition(LocalDateTime localDateTime) {
        String yesterday = dateDayFormat(localDateTime.minusDays(1));
        return Query.query(new Criteria().andOperator(
                Criteria.where(ProFieldEnum.DATE_DAY.getName()).is(Integer.parseInt(yesterday)),
                Criteria.where(ProFieldEnum.PERIOD_TYPE.getName()).is(PeriodTypeEnum.DAY1.getName()),
                Criteria.where(ProFieldEnum.HOUSE_TYPE.getName()).is(HouseTypeEnum.WHOLE.getName())));
    }

    @Override
    public Query sourceDataCondition(LocalDateTime localDateTime) {
        return Query.query(new Criteria().orOperator(
                Criteria.where(ProFieldEnum.ACTION_DEFINITION.getName())
                        .all(ActionDefinitionEnum.VIS_AGT.getName())));
    }

    @Override
    public AgentVisitTrendEntity convertToTargetData(LocalDateTime localDateTime, MrAgentVisitTrendWholeRo o) {
        MrAgentVisitTrendWholeRo.Key key = o.getId();
        MrStandardValue value = o.getValue();
        AgentVisitTrendEntity entity = new AgentVisitTrendEntity();
        entity.setPeriodType(PeriodTypeEnum.DAY1.getName());
        entity.setAgentId(key.getAgentId());
        entity.setCity(key.getCity());
        entity.setHouseType(HouseTypeEnum.WHOLE.getName());
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
