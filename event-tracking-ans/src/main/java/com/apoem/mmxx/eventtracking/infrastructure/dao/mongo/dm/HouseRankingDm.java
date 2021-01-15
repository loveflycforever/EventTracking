package com.apoem.mmxx.eventtracking.infrastructure.dao.mongo.dm;

import com.apoem.mmxx.eventtracking.infrastructure.enums.PeriodTypeEnum;
import com.apoem.mmxx.eventtracking.infrastructure.enums.ProFieldEnum;
import com.apoem.mmxx.eventtracking.infrastructure.enums.ActionDefinitionEnum;
import com.apoem.mmxx.eventtracking.infrastructure.dao.support.TaskLabel;
import com.apoem.mmxx.eventtracking.infrastructure.dao.support.AbstractMapReduceDmDao;
import com.apoem.mmxx.eventtracking.infrastructure.dao.support.CriteriaSupport;
import com.apoem.mmxx.eventtracking.infrastructure.po.dm.HouseRankingEntity;
import com.apoem.mmxx.eventtracking.infrastructure.po.dw.ConsumerAcqDailyDwEntity;
import com.apoem.mmxx.eventtracking.infrastructure.po.ro.MrHouseRankingRo;
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
 * <p>Name: AgentHouseRankingDmDao </p>
 * <p>Description: 效果分析-经纪人房源访问昨日统计 </p>
 * <p>Date: 2020/8/21 9:44 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Repository
@Slf4j
@TaskLabel(name = "维度[城市-房源-条口]日统计", desc = "[VIS_HUS|COL_HUS|RPS_HUS]日周期，统计访问量、访客数、收藏量、转发量", order = 1)
public class HouseRankingDm extends AbstractMapReduceDmDao<HouseRankingEntity, MrHouseRankingRo> {

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
                .whereIs(HouseRankingEntity.builder().dateDay(Integer.parseInt(dateDay))
                        .periodType(PeriodTypeEnum.DAY1.getName())
                        .build())
                .get());
    }

    @Override
    protected boolean hasCrossData() {
        return false;
    }

    @Override
    public Class<HouseRankingEntity> targetClass() {
        return HouseRankingEntity.class;
    }

    @Override
    public Class<MrHouseRankingRo> mrResultClass() {
        return MrHouseRankingRo.class;
    }

    @Override
    public boolean removeTemporary() {
        return true;
    }

    @Override
    public Query sourceDataCondition(LocalDateTime localDateTime) {
        return Query.query(new Criteria().orOperator(
                Criteria.where(ProFieldEnum.ACTION_DEFINITION.getName()).all(ActionDefinitionEnum.VIS_HUS.getName()),
                Criteria.where(ProFieldEnum.ACTION_DEFINITION.getName()).all(ActionDefinitionEnum.COL_HUS.getName()),
                Criteria.where(ProFieldEnum.ACTION_DEFINITION.getName()).all(ActionDefinitionEnum.RPS_HUS.getName()),
                Criteria.where(ProFieldEnum.ACTION_DEFINITION.getName()).all(ActionDefinitionEnum.IMC_HUS.getName()),
                Criteria.where(ProFieldEnum.ACTION_DEFINITION.getName()).all(ActionDefinitionEnum.PNC_HUS.getName())));
    }

    @Override
    public String getReduceFunction() {
        return "classpath:mr/houseRankingDayReduce.js";
    }

    @Override
    public String getMapFunction() {
        return "classpath:mr/houseRankingDayMap.js";
    }

    @Override
    public List<HouseRankingEntity> convertToTargetData(LocalDateTime localDateTime, MapReduceResults<MrHouseRankingRo> reduceResults) {
        Iterator<? extends MrHouseRankingRo> iterator = reduceResults.iterator();

        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterator, Spliterator.ORDERED), false)
                .map(o -> convertToTargetData(localDateTime, o)).collect(Collectors.toList());
    }

    public HouseRankingEntity convertToTargetData(LocalDateTime localDateTime, MrHouseRankingRo o) {
        HouseRankingEntity rankingEntity = new HouseRankingEntity().init();

        MrHouseRankingRo.Key key = o.getId();
        MrHouseRankingRo.Value value = o.getValue();

        rankingEntity.setDateDay(Integer.parseInt(dateDayFormat(localDateTime)));
        rankingEntity.setPeriodType(PeriodTypeEnum.DAY1.getName());

        // 主要维度
        rankingEntity.setCity(key.getCity());
        rankingEntity.setHouseId(key.getHouseId());
        rankingEntity.setHouseType(key.getHouseType());

        rankingEntity.setHouseName(value.getHouseName());
        rankingEntity.setHouseArea(value.getHouseArea());
        rankingEntity.setHouseTotalPrice(value.getHouseTotalPrice());
        rankingEntity.setHouseAveragePrice(value.getHouseAveragePrice());
        rankingEntity.setHouseAreaLower(value.getHouseAreaLower());
        rankingEntity.setHouseAreaUpper(value.getHouseAreaUpper());
        rankingEntity.setHouseLivingRoom(value.getHouseLivingRoom());
        rankingEntity.setHouseBedroom(value.getHouseBedroom());
        rankingEntity.setHouseBathroom(value.getHouseBathroom());
        rankingEntity.setCommunityId(value.getCommunityId());
        rankingEntity.setCommunityName(value.getCommunityName());
        rankingEntity.setPlateId(value.getPlateId());
        rankingEntity.setPlateName(value.getPlateName());
        rankingEntity.setAgentId(value.getAgentId());
        rankingEntity.setStoreId(value.getStoreId());

        rankingEntity.setPageView(value.getPageView());
        rankingEntity.setUniqueVisitor(value.getUniqueVisitor());
        rankingEntity.setCollected(value.getCollected());
        rankingEntity.setReposted(value.getReposted());
        rankingEntity.setImConnected(value.getImConnected());
        rankingEntity.setPhoneConnected(value.getPhoneConnected());

        return rankingEntity;
    }

    @Override
    public void saveTargetData(LocalDateTime localDateTime, List<HouseRankingEntity> crossData, List<HouseRankingEntity> mrConvertedData) {
        mrConvertedData.forEach(o -> {
            Date current = new Date();
            o.setCreateTime(current);
            o.setUpdateTime(current);
            o.setMark(0);
            mongoTemplate().save(o);
        });
    }
}
