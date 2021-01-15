package com.apoem.mmxx.eventtracking.infrastructure.dao.mongo.dm;

import com.apoem.mmxx.eventtracking.infrastructure.enums.PeriodTypeEnum;
import com.apoem.mmxx.eventtracking.infrastructure.enums.HouseTypeEnum;
import com.apoem.mmxx.eventtracking.infrastructure.dao.support.TaskLabel;
import com.apoem.mmxx.eventtracking.infrastructure.dao.support.CriteriaSupport;
import com.apoem.mmxx.eventtracking.infrastructure.po.dm.AgentCommunityRankingEntity;
import com.apoem.mmxx.eventtracking.infrastructure.po.ro.MrAgentCommunityRankingDayRo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: AgentCommunityRankingDay1WholeDm </p>
 * <p>Description: 效果分析-经纪人小区访问昨日统计汇总 </p>
 * <p>Date: 2020/8/21 9:44 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Repository
@Slf4j
@TaskLabel(name = "维度[城市-经纪人-小区]日总量统计", desc = "[VIS_HUS|COL_HUS|RPS_HUS]日周期，统计访问量、访客数、收藏量、转发量", order = 1)
public class AgentCommunityRankingWholeDm extends AgentCommunityRankingDm {

    @Override
    public Query removeHistoryCondition(LocalDateTime localDateTime) {
        String dateDay = dateDayFormat(localDateTime);
        return new Query(new CriteriaSupport<>()
                .whereIs(AgentCommunityRankingEntity.builder().dateDay(Integer.parseInt(dateDay))
                        .periodType(PeriodTypeEnum.DAY1.getName())
                        .houseType(HouseTypeEnum.WHOLE.getName()).build())
                .get());
    }

    @Override
    public String getReduceFunction() {
        return "classpath:mr/agentCommunityRankingWholeReduce.js";
    }

    @Override
    public String getMapFunction() {
        return "classpath:mr/agentCommunityRankingWholeMap.js";
    }

    @Override
    public AgentCommunityRankingEntity convertToTargetData(LocalDateTime localDateTime, Map<ImmutableTriple<String, String, String>, AgentCommunityRankingEntity> cross, MrAgentCommunityRankingDayRo o) {
        AgentCommunityRankingEntity rankingEntity = super.convertToTargetData(localDateTime, cross, o);
        rankingEntity.setHouseType(HouseTypeEnum.WHOLE.getName());
        return rankingEntity;
    }
}
