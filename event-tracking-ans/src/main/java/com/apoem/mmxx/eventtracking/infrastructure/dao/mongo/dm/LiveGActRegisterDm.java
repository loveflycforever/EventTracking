package com.apoem.mmxx.eventtracking.infrastructure.dao.mongo.dm;

import com.apoem.mmxx.eventtracking.infrastructure.dao.support.AbstractMapReduceDmDao;
import com.apoem.mmxx.eventtracking.infrastructure.dao.support.CriteriaSupport;
import com.apoem.mmxx.eventtracking.infrastructure.dao.support.TaskLabel;
import com.apoem.mmxx.eventtracking.infrastructure.enums.ActionDefinitionEnum;
import com.apoem.mmxx.eventtracking.infrastructure.enums.PeriodTypeEnum;
import com.apoem.mmxx.eventtracking.infrastructure.enums.ProFieldEnum;
import com.apoem.mmxx.eventtracking.infrastructure.po.dm.LiveGActRegisterEntity;
import com.apoem.mmxx.eventtracking.infrastructure.po.dw.ConsumerAcqDailyDwEntity;
import com.apoem.mmxx.eventtracking.infrastructure.po.ro.LiveGActRegisterRo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.mapreduce.MapReduceResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: LiveGActRegisterDm </p>
 * <p>Description: 活动参与人数 </p>
 * <p>Date: 2020/11/30 9:13 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author somebody
 * @version v1.0
 */
@Repository
@Slf4j
@TaskLabel(name = "维度[活动]日统计", desc = "[VIS_GAME]日周期，统计活动账户申请人数", order = 1)
public class LiveGActRegisterDm extends AbstractMapReduceDmDao<LiveGActRegisterEntity, LiveGActRegisterRo> {

    @Override
    public Class<?> sourceDataClass() {
        return ConsumerAcqDailyDwEntity.class;
    }

    @Override
    public Query sourceDataCondition(LocalDateTime localDateTime) {
        Criteria criteria = new Criteria();
        criteria.orOperator(Criteria.where("local_status").is("STAS"), Criteria.where("local_status").is("STAF"),
                Criteria.where("local_status").is("STAO"), Criteria.where("local_status").is("STAU"));
        return Query.query(Criteria
                .where(ProFieldEnum.ACTION_DEFINITION.getName()).all(
                        ActionDefinitionEnum.VIS_GAME.getName()
                ).andOperator(criteria));
    }

    @Override
    public Query removeHistoryCondition(LocalDateTime localDateTime) {
        String dateDay = dateDayFormat(localDateTime);
        return new Query(new CriteriaSupport<>()
                .whereIs(LiveGActRegisterEntity.builder()
                        .dateDay(Integer.parseInt(dateDay))
                        .periodType(PeriodTypeEnum.DAY1.getName()).build())
                .get());
    }

    @Override
    public Class<LiveGActRegisterRo> mrResultClass() {
        return LiveGActRegisterRo.class;
    }

    @Override
    public Class<LiveGActRegisterEntity> targetClass() {
        return LiveGActRegisterEntity.class;
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
        return "classpath:mr/liveGActRegisterReduce.js";
    }

    @Override
    public String getMapFunction() {
        return "classpath:mr/liveGActRegisterMap.js";
    }

    @Override
    public List<LiveGActRegisterEntity> convertToTargetData(LocalDateTime localDateTime, MapReduceResults<LiveGActRegisterRo> reduceResults) {
        Iterator<LiveGActRegisterRo> iterator = reduceResults.iterator();

        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterator, Spliterator.ORDERED), false)
                .map(ro -> {
                    LiveGActRegisterEntity entity = new LiveGActRegisterEntity();

                    entity.setDateDay(Integer.parseInt(dateDayFormat(localDateTime)));
                    entity.setPeriodType(PeriodTypeEnum.DAY1.getName());
                    entity.setGameId(ro.getId().getGameId());
                    entity.setUniqueVisitor(ro.getValue().getUniqueVisitor());

                    return entity;
                }).collect(Collectors.toList());
    }

    @Override
    public void saveTargetData(LocalDateTime localDateTime, List<LiveGActRegisterEntity> crossData, List<LiveGActRegisterEntity> convertedTargetData) {
        convertedTargetData.forEach(o -> {
            Date current = new Date();
            o.setCreateTime(current);
            o.setUpdateTime(current);
            mongoTemplate().save(o);
        });
    }
}
