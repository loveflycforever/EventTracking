package com.apoem.mmxx.eventtracking.infrastructure.dao.mongo.dm;

import com.apoem.mmxx.eventtracking.infrastructure.enums.PeriodTypeEnum;
import com.apoem.mmxx.eventtracking.infrastructure.enums.ProFieldEnum;
import com.apoem.mmxx.eventtracking.infrastructure.enums.ActionDefinitionEnum;
import com.apoem.mmxx.eventtracking.infrastructure.dao.support.TaskLabel;
import com.apoem.mmxx.eventtracking.infrastructure.dao.support.AbstractMapReduceDmDao;
import com.apoem.mmxx.eventtracking.infrastructure.dao.support.CriteriaSupport;
import com.apoem.mmxx.eventtracking.infrastructure.po.dm.AgentCommunityRankingEntity;
import com.apoem.mmxx.eventtracking.infrastructure.po.dw.ConsumerAcqDailyDwEntity;
import com.apoem.mmxx.eventtracking.infrastructure.po.ro.MrAgentCommunityRankingDayRo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.springframework.data.mongodb.core.mapreduce.MapReduceResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.BinaryOperator;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: AgentCommunityRankingDay1Dm </p>
 * <p>Description: 效果分析-经纪人小区访问昨日统计 </p>
 * <p>Date: 2020/8/21 9:44 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Repository
@Slf4j
@TaskLabel(name = "维度[城市-经纪人-小区-条口]日统计", desc = "[VIS_HUS|COL_HUS|RPS_HUS]日周期，统计访问量、访客数、收藏量、转发量", order = 1)
public class AgentCommunityRankingDm extends AbstractMapReduceDmDao<AgentCommunityRankingEntity, MrAgentCommunityRankingDayRo> {

    @Override
    public boolean removeHistory() {
        return true;
    }

    @Override
    public Class<?> sourceDataClass() {
        return ConsumerAcqDailyDwEntity.class;
    }

    @Override
    public Query removeHistoryCondition(LocalDateTime localDateTime) {
        String dateDay = dateDayFormat(localDateTime);
        return Query.query(new CriteriaSupport<>()
                .whereIs(AgentCommunityRankingEntity.builder().dateDay(Integer.parseInt(dateDay))
                        .periodType(PeriodTypeEnum.DAY1.getName()).build())
                .get());
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
    public Class<AgentCommunityRankingEntity> targetClass() {
        return AgentCommunityRankingEntity.class;
    }

    @Override
    public Class<MrAgentCommunityRankingDayRo> mrResultClass() {
        return MrAgentCommunityRankingDayRo.class;
    }

    @Override
    public Query sourceDataCondition(LocalDateTime localDateTime) {
        return Query.query(new Criteria().orOperator(
                Criteria.where(ProFieldEnum.ACTION_DEFINITION.getName())
                        .all(ActionDefinitionEnum.VIS_HUS.getName()),
                Criteria.where(ProFieldEnum.ACTION_DEFINITION.getName())
                        .all(ActionDefinitionEnum.COL_HUS.getName()),
                Criteria.where(ProFieldEnum.ACTION_DEFINITION.getName())
                        .all(ActionDefinitionEnum.RPS_HUS.getName())));
    }

    @Override
    public String getReduceFunction() {
        return "classpath:mr/agentCommunityRankingDayReduce.js";
    }

    @Override
    public String getMapFunction() {
        return "classpath:mr/agentCommunityRankingDayMap.js";
    }

    @Override
    public List<AgentCommunityRankingEntity> convertToTargetData(LocalDateTime localDateTime, MapReduceResults<MrAgentCommunityRankingDayRo> reduceResults) {
        Iterator<? extends MrAgentCommunityRankingDayRo> iterator = reduceResults.iterator();

        BinaryOperator<AgentCommunityRankingEntity> binaryOperator = binaryOperator();

        Map<ImmutableTriple<String, String, String>, AgentCommunityRankingEntity> cross = StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterator, Spliterator.ORDERED), false)
                .collect(Collectors.groupingBy(this::crossKey,
                        Collector.of(
                                () -> new AgentCommunityRankingEntity().init(),
                                (initEntity, resultObject) -> binaryOperator.apply(initEntity, convertToTargetData(localDateTime, Collections.emptyMap(), resultObject)),
                                binaryOperator, entity -> entity)));

        iterator = reduceResults.iterator();

        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterator, Spliterator.ORDERED), false)
                .map(o -> convertToTargetData(localDateTime, cross, o)).collect(Collectors.toList());
    }

    private BinaryOperator<AgentCommunityRankingEntity> binaryOperator() {
        return (prev, next) -> {
            prev.setDateDay(next.getDateDay());
            prev.setPeriodType(next.getPeriodType());

            prev.setCity(next.getCity());
            prev.setAgentId(next.getAgentId());
            prev.setHouseType(next.getHouseType());
            prev.setCommunityId(next.getCommunityId());

            prev.setCommunityName(next.getCommunityName());
            prev.setPlateId(next.getPlateId());
            prev.setPlateName(next.getPlateName());

            prev.setTotalPageView(next.getTotalPageView());
            prev.setTotalUniqueVisitor(next.getTotalUniqueVisitor());
            prev.setTotalCollected(next.getTotalCollected());
            prev.setTotalReposted(next.getTotalReposted());

            prev.setPageView(prev.getPageView() + next.getPageView());
            prev.setUniqueVisitor(prev.getUniqueVisitor() + next.getUniqueVisitor());
            prev.setCollected(prev.getCollected() + next.getCollected());
            prev.setReposted(prev.getReposted() + next.getReposted());
            return prev;
        };
    }

    public AgentCommunityRankingEntity convertToTargetData(LocalDateTime localDateTime, Map<ImmutableTriple<String, String, String>, AgentCommunityRankingEntity> cross, MrAgentCommunityRankingDayRo o) {
        MrAgentCommunityRankingDayRo.Key key = o.getId();
        MrAgentCommunityRankingDayRo.Value value = o.getValue();

        AgentCommunityRankingEntity rankingEntity = new AgentCommunityRankingEntity().init();
        rankingEntity.setDateDay(Integer.parseInt(dateDayFormat(localDateTime)));
        rankingEntity.setPeriodType(PeriodTypeEnum.DAY1.getName());

        rankingEntity.setCity(key.getCity());
        rankingEntity.setAgentId(key.getAgentId());
        rankingEntity.setHouseType(key.getHouseType());
        rankingEntity.setCommunityId(key.getCommunityId());

        rankingEntity.setCommunityName(value.getCommunityName());
        rankingEntity.setPlateId(value.getPlateId());
        rankingEntity.setPlateName(value.getPlateName());

        AgentCommunityRankingEntity crossEntity = cross.get(crossKey(o));
        if (Objects.nonNull(crossEntity)) {
            rankingEntity.setTotalPageView(crossEntity.getPageView());
            rankingEntity.setTotalUniqueVisitor(crossEntity.getUniqueVisitor());
            rankingEntity.setTotalCollected(crossEntity.getCollected());
            rankingEntity.setTotalReposted(crossEntity.getReposted());
        }

        rankingEntity.setPageView(value.getPageView());
        rankingEntity.setUniqueVisitor(value.getUniqueVisitor());
        rankingEntity.setCollected(value.getCollected());
        rankingEntity.setReposted(value.getReposted());
        return rankingEntity;
    }

    private ImmutableTriple<String, String, String> crossKey(MrAgentCommunityRankingDayRo o) {
        return ImmutableTriple.of(o.getId().getAgentId(), o.getId().getHouseType(), o.getId().getCity());
    }

    @Override
    public void saveTargetData(LocalDateTime localDateTime, List<AgentCommunityRankingEntity> crossData, List<AgentCommunityRankingEntity> mrConvertedData) {
        mrConvertedData.forEach(o -> {
            Date current = new Date();
            o.setCreateTime(current);
            o.setUpdateTime(current);
            o.setMark(0);
            mongoTemplate().save(o);
        });
    }
}
