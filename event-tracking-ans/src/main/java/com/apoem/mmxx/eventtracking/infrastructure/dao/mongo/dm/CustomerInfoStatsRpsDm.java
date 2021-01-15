package com.apoem.mmxx.eventtracking.infrastructure.dao.mongo.dm;

import com.apoem.mmxx.eventtracking.infrastructure.dao.support.AbstractMapReduceDmDaoV2;
import com.apoem.mmxx.eventtracking.infrastructure.dao.support.TaskLabel;
import com.apoem.mmxx.eventtracking.infrastructure.enums.ActionDefinitionEnum;
import com.apoem.mmxx.eventtracking.infrastructure.enums.ActionTypeEnum;
import com.apoem.mmxx.eventtracking.infrastructure.enums.PeriodTypeEnum;
import com.apoem.mmxx.eventtracking.infrastructure.enums.ProFieldEnum;
import com.apoem.mmxx.eventtracking.infrastructure.po.dm.CustomerInfoActionStatsEntity;
import com.apoem.mmxx.eventtracking.infrastructure.po.dw.ConsumerAcqDailyDwEntity;
import com.apoem.mmxx.eventtracking.infrastructure.po.ro.MrCustomerInfoActionStatsRo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: CustomerStatsDm </p>
 * <p>Description:  </p>
 * <p>Date: 2020/9/17 16:12 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Repository
@Slf4j
@TaskLabel(name = "维度[城市-客户]客户访问资讯日统计", desc = "[RPS_IFO]日周期，统计转发资讯量", order = 1)
public class CustomerInfoStatsRpsDm extends AbstractMapReduceDmDaoV2<CustomerInfoActionStatsEntity, MrCustomerInfoActionStatsRo, ConsumerAcqDailyDwEntity> {

    @Override
    public Query removeHistoryCondition(LocalDateTime localDateTime) {
        return Query.query(new Criteria().andOperator(
                Criteria.where(ProFieldEnum.DATE_DAY.getName()).is(Integer.parseInt(dateDayFormat(localDateTime))),
                Criteria.where(ProFieldEnum.PERIOD_TYPE.getName()).is(PeriodTypeEnum.DAY1.getName()),
                Criteria.where(ProFieldEnum.ACTION_TYPE.getName()).is(ActionTypeEnum.RPS.getName())));
    }

    @Override
    public Query sourceDataCondition(LocalDateTime localDateTime) {
        return Query.query(new Criteria().orOperator(
                Criteria.where(ProFieldEnum.ACTION_DEFINITION.getName()).all(ActionDefinitionEnum.RPS_IFO.getName())
        ));
    }

    @Override
    public CustomerInfoActionStatsEntity convertToTargetData(LocalDateTime localDateTime, MrCustomerInfoActionStatsRo ro) {
        CustomerInfoActionStatsEntity entity = new CustomerInfoActionStatsEntity();

        entity.setDateDay(Integer.parseInt(dateDayFormat(localDateTime)));
        entity.setPeriodType(PeriodTypeEnum.DAY1.getName());

        entity.setActionType(ActionTypeEnum.RPS.getName());

        entity.setCity(ro.getId().getCity());
        entity.setCustomerId(ro.getId().getCustomerId());

        entity.setPageView(ro.getValue().getPageView());
        entity.setUniqueVisitor(ro.getValue().getUniqueVisitor());
        return entity;
    }
}
