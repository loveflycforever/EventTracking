package com.apoem.mmxx.eventtracking.infrastructure.dao.mongo.dm;

import com.apoem.mmxx.eventtracking.infrastructure.dao.support.AbstractMapReduceDmDao;
import com.apoem.mmxx.eventtracking.infrastructure.dao.support.CriteriaSupport;
import com.apoem.mmxx.eventtracking.infrastructure.dao.support.TaskLabel;
import com.apoem.mmxx.eventtracking.infrastructure.enums.ActionDefinitionEnum;
import com.apoem.mmxx.eventtracking.infrastructure.enums.PeriodTypeEnum;
import com.apoem.mmxx.eventtracking.infrastructure.enums.ProFieldEnum;
import com.apoem.mmxx.eventtracking.infrastructure.po.dm.LiveGActAgentViewEntity;
import com.apoem.mmxx.eventtracking.infrastructure.po.dw.ConsumerAcqDailyDwEntity;
import com.apoem.mmxx.eventtracking.infrastructure.po.ro.LiveGActAgentViewRo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.mapreduce.MapReduceResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Repository
@Slf4j
@TaskLabel(name = "维度[活动-经纪人]日统计", desc = "[VIS_GAME]日周期，统计活动浏览总数", order = 1)
public class LiveGActAgentViewDm extends AbstractMapReduceDmDao<LiveGActAgentViewEntity, LiveGActAgentViewRo> {
    @Override
    public Class<?> sourceDataClass() {
        return ConsumerAcqDailyDwEntity.class;
    }

    @Override
    public Query sourceDataCondition(LocalDateTime localDateTime) {
        return Query.query(Criteria
                .where(ProFieldEnum.ACTION_DEFINITION.getName()).all(
                        ActionDefinitionEnum.VIS_GAME.getName()
                ));
    }

    @Override
    public Query removeHistoryCondition(LocalDateTime localDateTime) {
        String dateDay = dateDayFormat(localDateTime);
        return new Query(new CriteriaSupport<>()
                .whereIs(LiveGActAgentViewEntity.builder()
                        .dateDay(Integer.parseInt(dateDay))
                        .periodType(PeriodTypeEnum.DAY1.getName()).build())
                .get());
    }

    @Override
    public Class<LiveGActAgentViewRo> mrResultClass() {
        return LiveGActAgentViewRo.class;
    }

    @Override
    public Class<LiveGActAgentViewEntity> targetClass() {
        return LiveGActAgentViewEntity.class;
    }

    @Override
    public boolean removeTemporary() {
        return true;
    }

    @Override
    protected boolean hasCrossData() {
        return false;
    }

    @Override
    public boolean removeHistory() {
        return true;
    }

    @Override
    public String getReduceFunction() {
        return "classpath:mr/liveGActAgentReduce.js";
    }

    @Override
    public String getMapFunction() {
        return "classpath:mr/liveGActAgentMap.js";
    }

    @Override
    public List<LiveGActAgentViewEntity> convertToTargetData(LocalDateTime localDateTime, MapReduceResults<LiveGActAgentViewRo> reduceResults) {
        Iterator<LiveGActAgentViewRo> iterator = reduceResults.iterator();

        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterator, Spliterator.ORDERED), false)
                .map(ro -> {
                    LiveGActAgentViewEntity entity = new LiveGActAgentViewEntity();

                    entity.setDateDay(Integer.parseInt(dateDayFormat(localDateTime)));
                    entity.setPeriodType(PeriodTypeEnum.DAY1.getName());
                    entity.setGameId(ro.getId().getGameId());
                    entity.setAgentId(ro.getId().getAgentId());
                    entity.setViewCount(ro.getValue().getPageView());

                    return entity;
                }).collect(Collectors.toList());
    }

    @Override
    public void saveTargetData(LocalDateTime localDateTime, List<LiveGActAgentViewEntity> crossData, List<LiveGActAgentViewEntity> convertedTargetData) {
        convertedTargetData.forEach(o -> {
            Date current = new Date();
            o.setCreateTime(current);
            o.setUpdateTime(current);
            mongoTemplate().save(o);
        });
    }
}
