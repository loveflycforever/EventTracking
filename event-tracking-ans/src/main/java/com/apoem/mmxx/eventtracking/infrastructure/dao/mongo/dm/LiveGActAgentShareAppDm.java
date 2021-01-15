package com.apoem.mmxx.eventtracking.infrastructure.dao.mongo.dm;

import com.apoem.mmxx.eventtracking.infrastructure.dao.support.AbstractMapReduceDmDao;
import com.apoem.mmxx.eventtracking.infrastructure.dao.support.CriteriaSupport;
import com.apoem.mmxx.eventtracking.infrastructure.dao.support.TaskLabel;
import com.apoem.mmxx.eventtracking.infrastructure.enums.ActionDefinitionEnum;
import com.apoem.mmxx.eventtracking.infrastructure.enums.PeriodTypeEnum;
import com.apoem.mmxx.eventtracking.infrastructure.enums.ProFieldEnum;
import com.apoem.mmxx.eventtracking.infrastructure.po.dm.LiveGActAgentShareEntity;
import com.apoem.mmxx.eventtracking.infrastructure.po.dw.BusinessAcqDailyDwEntity;
import com.apoem.mmxx.eventtracking.infrastructure.po.ro.LiveGActAgentShareAppRo;
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
@TaskLabel(name = "维度[活动-经纪人]liveG分享日统计", desc = "[RPS_GAME_APP]日周期，统计活动分享总数", order = 1)
public class LiveGActAgentShareAppDm extends AbstractMapReduceDmDao<LiveGActAgentShareEntity, LiveGActAgentShareAppRo> {

    @Override
    public Class<?> sourceDataClass() {
        return BusinessAcqDailyDwEntity.class;
    }

    @Override
    public Query sourceDataCondition(LocalDateTime localDateTime) {
        Criteria criteria = new Criteria();
        criteria.orOperator(Criteria.where(ProFieldEnum.ACTION_DEFINITION.getName()).all(ActionDefinitionEnum.RPS_GAME_H5.getName()),
                Criteria.where(ProFieldEnum.ACTION_DEFINITION.getName()).all(ActionDefinitionEnum.RPS_GAME_APP.getName()));
        return Query.query(criteria);
    }

    @Override
    public Query removeHistoryCondition(LocalDateTime localDateTime) {
        String dateDay = dateDayFormat(localDateTime);
        return new Query(new CriteriaSupport<>()
                .whereIs(LiveGActAgentShareEntity.builder()
                        .dateDay(Integer.parseInt(dateDay))
                        .periodType(PeriodTypeEnum.DAY1.getName()).build())
                .get().and("occurred").is("APP"));
    }

    @Override
    public Class<LiveGActAgentShareAppRo> mrResultClass() {
        return LiveGActAgentShareAppRo.class;
    }

    @Override
    public Class<LiveGActAgentShareEntity> targetClass() {
        return LiveGActAgentShareEntity.class;
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
        return "classpath:mr/liveGActAgentAppReduce.js";
    }

    @Override
    public String getMapFunction() {
        return "classpath:mr/liveGActAgentAppMap.js";
    }

    @Override
    public List<LiveGActAgentShareEntity> convertToTargetData(LocalDateTime localDateTime, MapReduceResults<LiveGActAgentShareAppRo> reduceResults) {
        Iterator<LiveGActAgentShareAppRo> iterator = reduceResults.iterator();

        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterator, Spliterator.ORDERED), false)
                .map(ro -> {
                    LiveGActAgentShareEntity entity = new LiveGActAgentShareEntity();

                    entity.setDateDay(Integer.parseInt(dateDayFormat(localDateTime)));
                    entity.setPeriodType(PeriodTypeEnum.DAY1.getName());
                    entity.setGameId(ro.getId().getGameId());
                    entity.setAgentId(ro.getId().getLoginAccount());
                    entity.setOccurred("APP");
                    entity.setShareCount(ro.getValue().getPageView());

                    return entity;
                }).collect(Collectors.toList());
    }

    @Override
    public void saveTargetData(LocalDateTime localDateTime, List<LiveGActAgentShareEntity> crossData, List<LiveGActAgentShareEntity> convertedTargetData) {
        convertedTargetData.forEach(o -> {
            Date current = new Date();
            o.setCreateTime(current);
            o.setUpdateTime(current);
            mongoTemplate().save(o);
        });
    }
}
