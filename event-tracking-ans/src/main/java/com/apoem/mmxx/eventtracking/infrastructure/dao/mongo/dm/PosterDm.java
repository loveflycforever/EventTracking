package com.apoem.mmxx.eventtracking.infrastructure.dao.mongo.dm;

import com.apoem.mmxx.eventtracking.infrastructure.dao.support.AbstractMapReduceDmDao;
import com.apoem.mmxx.eventtracking.infrastructure.dao.support.CriteriaSupport;
import com.apoem.mmxx.eventtracking.infrastructure.dao.support.TaskLabel;
import com.apoem.mmxx.eventtracking.infrastructure.enums.ActionDefinitionEnum;
import com.apoem.mmxx.eventtracking.infrastructure.enums.PeriodTypeEnum;
import com.apoem.mmxx.eventtracking.infrastructure.enums.ProFieldEnum;
import com.apoem.mmxx.eventtracking.infrastructure.po.dm.PosterEntity;
import com.apoem.mmxx.eventtracking.infrastructure.po.dw.ConsumerAcqDailyDwEntity;
import com.apoem.mmxx.eventtracking.infrastructure.po.ro.PosterRo;
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
 * <p>Name: PosterDm </p>
 * <p>Description: 海报每日统计 </p>
 * <p>Date: 2020/11/17 10:45 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author somebody
 * @version v1.0
 */
@Repository
@Slf4j
@TaskLabel(name = "维度[城市-经纪人-海报]日统计", desc = "[VIS_POS_H5]日周期，统计访问量、访客数", order = 1)
public class PosterDm extends AbstractMapReduceDmDao<PosterEntity, PosterRo> {
    @Override
    public Class<?> sourceDataClass() {
        return ConsumerAcqDailyDwEntity.class;
    }

    @Override
    public Query sourceDataCondition(LocalDateTime localDateTime) {
        return Query.query(Criteria
                .where(ProFieldEnum.ACTION_DEFINITION.getName()).all(
                        ActionDefinitionEnum.VIS_POS_H5.getName()
                ));
    }

    @Override
    public Query removeHistoryCondition(LocalDateTime localDateTime) {
        String dateDay = dateDayFormat(localDateTime);
        return new Query(new CriteriaSupport<>()
                .whereIs(PosterEntity.builder()
                        .dateDay(Integer.parseInt(dateDay))
                        .periodType(PeriodTypeEnum.DAY1.getName()).build())
                .get());
    }

    @Override
    public Class<PosterRo> mrResultClass() {
        return PosterRo.class;
    }

    @Override
    public Class<PosterEntity> targetClass() {
        return PosterEntity.class;
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
        return "classpath:mr/posterReduce.js";
    }

    @Override
    public String getMapFunction() {
        return "classpath:mr/posterMap.js";
    }

    @Override
    public List<PosterEntity> convertToTargetData(LocalDateTime localDateTime, MapReduceResults<PosterRo> reduceResults) {
        Iterator<PosterRo> iterator = reduceResults.iterator();

        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterator, Spliterator.ORDERED), false)
                .map(ro -> {
                    PosterEntity entity = new PosterEntity();

                    entity.setDateDay(Integer.parseInt(dateDayFormat(localDateTime)));
                    entity.setPeriodType(PeriodTypeEnum.DAY1.getName());
                    entity.setPosterId(ro.getId().getPosterId());
                    entity.setAgentId(ro.getId().getAgentId());
                    entity.setCity(ro.getId().getCity());
                    entity.setPageView(ro.getValue().getPageView());
                    entity.setUniqueVisitor(ro.getValue().getUniqueVisitor());

                    return entity;
                }).collect(Collectors.toList());
    }

    @Override
    public void saveTargetData(LocalDateTime localDateTime, List<PosterEntity> crossData, List<PosterEntity> convertedTargetData) {
        convertedTargetData.forEach(o -> {
            Date current = new Date();
            o.setCreateTime(current);
            o.setUpdateTime(current);
            mongoTemplate().save(o);
        });
    }
}
