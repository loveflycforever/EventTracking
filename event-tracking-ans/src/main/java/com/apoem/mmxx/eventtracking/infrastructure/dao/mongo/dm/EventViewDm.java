package com.apoem.mmxx.eventtracking.infrastructure.dao.mongo.dm;

import com.apoem.mmxx.eventtracking.infrastructure.dao.support.AbstractMapReduceDmDaoV2;
import com.apoem.mmxx.eventtracking.infrastructure.dao.support.TaskLabel;
import com.apoem.mmxx.eventtracking.infrastructure.enums.PeriodTypeEnum;
import com.apoem.mmxx.eventtracking.infrastructure.po.dm.EventStatsEntity;
import com.apoem.mmxx.eventtracking.infrastructure.po.dw.ConsumerAcqDailyDwEntity;
import com.apoem.mmxx.eventtracking.infrastructure.po.ro.MrEventStatsRo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: PageDm </p>
 * <p>Description:  </p>
 * <p>Date: 2020/9/4 10:45 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Repository
@Slf4j
@TaskLabel(name = "维度[城市-页面-事件]客户访问日统计", desc = "[EVENT_TYPE-EVENT]日周期，统计访问量、访客数", order = 1)
public class EventViewDm extends AbstractMapReduceDmDaoV2<EventStatsEntity, MrEventStatsRo, ConsumerAcqDailyDwEntity> {

    @Override
    public Query sourceDataCondition(LocalDateTime localDateTime) {
        return new Query();
    }

    @Override
    public EventStatsEntity convertToTargetData(LocalDateTime localDateTime, MrEventStatsRo ro) {
        EventStatsEntity entity = new EventStatsEntity();

        entity.setDateDay(Integer.parseInt(dateDayFormat(localDateTime)));
        entity.setPeriodType(PeriodTypeEnum.DAY1.getName());

        entity.setCity(ro.getId().getCity());
        entity.setPageName(ro.getId().getPageName());
        entity.setMethodName(ro.getId().getMethodName());
        entity.setEventType(ro.getId().getEventType());

        entity.setPageView(ro.getValue().getPageView());
        entity.setUniqueVisitor(ro.getValue().getUniqueVisitor());
        return entity;
    }
}
