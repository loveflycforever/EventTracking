package com.apoem.mmxx.eventtracking.infrastructure.dao.mongo.dm;

import com.apoem.mmxx.eventtracking.infrastructure.dao.support.AbstractMapReduceDmDaoV2;
import com.apoem.mmxx.eventtracking.infrastructure.dao.support.TaskLabel;
import com.apoem.mmxx.eventtracking.infrastructure.enums.ActionDefinitionEnum;
import com.apoem.mmxx.eventtracking.infrastructure.enums.PeriodTypeEnum;
import com.apoem.mmxx.eventtracking.infrastructure.enums.ProFieldEnum;
import com.apoem.mmxx.eventtracking.infrastructure.po.dm.HouseRankingH5Entity;
import com.apoem.mmxx.eventtracking.infrastructure.po.dw.ConsumerAcqDailyDwEntity;
import com.apoem.mmxx.eventtracking.infrastructure.po.ro.MrHouseRankingH5Ro;
import com.apoem.mmxx.eventtracking.infrastructure.po.ro.support.MrStandardValue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

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
@TaskLabel(name = "维度[城市-房源-条口]客户访问房源H5日统计", desc = "[VIS_HUS_H5]日周期，统计访问量、访客数", order = 1)
public class HouseRankingH5Dm extends AbstractMapReduceDmDaoV2<HouseRankingH5Entity, MrHouseRankingH5Ro, ConsumerAcqDailyDwEntity> {

    @Override
    public Query sourceDataCondition(LocalDateTime localDateTime) {
        return Query.query(new Criteria().orOperator(
                Criteria.where(ProFieldEnum.ACTION_DEFINITION.getName()).all(ActionDefinitionEnum.VIS_HUS_H5.getName())));
    }

    @Override
    public HouseRankingH5Entity convertToTargetData(LocalDateTime localDateTime, MrHouseRankingH5Ro o) {
        HouseRankingH5Entity rankingEntity = new HouseRankingH5Entity();

        MrHouseRankingH5Ro.Key key = o.getId();
        MrStandardValue value = o.getValue();

        rankingEntity.setDateDay(Integer.parseInt(dateDayFormat(localDateTime)));
        rankingEntity.setPeriodType(PeriodTypeEnum.DAY1.getName());

        // 主要维度
        rankingEntity.setCity(key.getCity());
        rankingEntity.setHouseId(key.getHouseId());
        rankingEntity.setHouseType(key.getHouseType());

        rankingEntity.setPageView(value.getPageView());
        rankingEntity.setUniqueVisitor(value.getUniqueVisitor());

        return rankingEntity;
    }
}
