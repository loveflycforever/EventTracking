package com.apoem.mmxx.eventtracking.infrastructure.dao.mongo.dm;

import com.apoem.mmxx.eventtracking.infrastructure.dao.support.AbstractMapReduceDmDaoV3;
import com.apoem.mmxx.eventtracking.infrastructure.dao.support.TaskLabel;
import com.apoem.mmxx.eventtracking.infrastructure.enums.ActionDefinitionEnum;
import com.apoem.mmxx.eventtracking.infrastructure.enums.ProFieldEnum;
import com.apoem.mmxx.eventtracking.infrastructure.po.dw.ConsumerAcqDailyDwEntity;
import com.apoem.mmxx.eventtracking.infrastructure.po.ro.support2.HouseRankingH5Entity2;
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
public class HouseRankingH5Dm2 extends AbstractMapReduceDmDaoV3<HouseRankingH5Entity2, ConsumerAcqDailyDwEntity> {

    @Override
    public Query sourceDataCondition(LocalDateTime localDateTime) {
        return Query.query(new Criteria().orOperator(
                Criteria.where(ProFieldEnum.ACTION_DEFINITION.getName()).all(ActionDefinitionEnum.VIS_HUS_H5.getName())));
    }
}
