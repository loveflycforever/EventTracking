package com.apoem.mmxx.eventtracking.infrastructure.dao.mongo.dm;

import com.apoem.mmxx.eventtracking.infrastructure.dao.support.AbstractMapReduceDmDao;
import com.apoem.mmxx.eventtracking.infrastructure.dao.support.CriteriaSupport;
import com.apoem.mmxx.eventtracking.infrastructure.dao.support.TaskLabel;
import com.apoem.mmxx.eventtracking.infrastructure.enums.ActionDefinitionEnum;
import com.apoem.mmxx.eventtracking.infrastructure.enums.PeriodTypeEnum;
import com.apoem.mmxx.eventtracking.infrastructure.enums.ProFieldEnum;
import com.apoem.mmxx.eventtracking.infrastructure.po.dm.LiveGActShopOccurredEntity;
import com.apoem.mmxx.eventtracking.infrastructure.po.dw.ConsumerAcqDailyDwEntity;
import com.apoem.mmxx.eventtracking.infrastructure.po.ro.LiveGActShopOccurredRo;
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
@TaskLabel(name = "维度[活动+门店+位置]H5 PV、UV日统计", desc = "[VIS_GAME]日周期，统计访问量、访客数", order = 1)
public class LiveGActShopOccurredH5Dm extends AbstractMapReduceDmDao<LiveGActShopOccurredEntity, LiveGActShopOccurredRo> {

    @Override
    public Class<?> sourceDataClass() {
        return ConsumerAcqDailyDwEntity.class;
    }

    @Override
    public Query sourceDataCondition(LocalDateTime localDateTime) {
        return Query.query(Criteria
                .where(ProFieldEnum.ACTION_DEFINITION.getName()).all(
                        ActionDefinitionEnum.VIS_GAME.getName()
                ).and("occurred").is("H5"));
    }

    @Override
    public Query removeHistoryCondition(LocalDateTime localDateTime) {
        String dateDay = dateDayFormat(localDateTime);
        return new Query(new CriteriaSupport<>()
                .whereIs(LiveGActShopOccurredEntity.builder()
                        .dateDay(Integer.parseInt(dateDay))
                        .periodType(PeriodTypeEnum.DAY1.getName()).build())
                .get().and("occurred").is("H5"));
    }

    @Override
    public Class<LiveGActShopOccurredRo> mrResultClass() {
        return LiveGActShopOccurredRo.class;
    }

    @Override
    public Class<LiveGActShopOccurredEntity> targetClass() {
        return LiveGActShopOccurredEntity.class;
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
        return "classpath:mr/liveGActShopOccurredReduce.js";
    }

    @Override
    public String getMapFunction() {
        return "classpath:mr/liveGActShopOccurredMap.js";
    }

    @Override
    public List<LiveGActShopOccurredEntity> convertToTargetData(LocalDateTime localDateTime, MapReduceResults<LiveGActShopOccurredRo> reduceResults) {
        Iterator<LiveGActShopOccurredRo> iterator = reduceResults.iterator();

        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterator, Spliterator.ORDERED), false)
                .map(ro -> {
                    LiveGActShopOccurredEntity entity = new LiveGActShopOccurredEntity();

                    entity.setDateDay(Integer.parseInt(dateDayFormat(localDateTime)));
                    entity.setPeriodType(PeriodTypeEnum.DAY1.getName());
                    entity.setGameId(ro.getId().getGameId());
                    entity.setStoreId(ro.getId().getStoreId());
                    entity.setOccurred("H5");
                    entity.setPageView(ro.getValue().getPageView());
                    entity.setUniqueVisitor(ro.getValue().getUniqueVisitor());

                    return entity;
                }).collect(Collectors.toList());
    }

    @Override
    public void saveTargetData(LocalDateTime localDateTime, List<LiveGActShopOccurredEntity> crossData, List<LiveGActShopOccurredEntity> convertedTargetData) {
        convertedTargetData.forEach(o -> {
            Date current = new Date();
            o.setCreateTime(current);
            o.setUpdateTime(current);
            mongoTemplate().save(o);
        });
    }
}
