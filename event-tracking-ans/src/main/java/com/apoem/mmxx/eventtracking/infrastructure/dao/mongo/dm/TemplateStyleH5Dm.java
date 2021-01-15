package com.apoem.mmxx.eventtracking.infrastructure.dao.mongo.dm;

import com.apoem.mmxx.eventtracking.infrastructure.dao.support.AbstractMapReduceDmDaoV2;
import com.apoem.mmxx.eventtracking.infrastructure.dao.support.TaskLabel;
import com.apoem.mmxx.eventtracking.infrastructure.enums.ActionDefinitionEnum;
import com.apoem.mmxx.eventtracking.infrastructure.enums.PeriodTypeEnum;
import com.apoem.mmxx.eventtracking.infrastructure.enums.ProFieldEnum;
import com.apoem.mmxx.eventtracking.infrastructure.po.dm.HouseTemplateH5Entity;
import com.apoem.mmxx.eventtracking.infrastructure.po.dw.ConsumerAcqDailyDwEntity;
import com.apoem.mmxx.eventtracking.infrastructure.po.ro.MrTemplateStyleH5Ro;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: AdvertisementDm </p>
 * <p>Description:  </p>
 * <p>Date: 2020/9/4 10:45 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Repository
@Slf4j
@TaskLabel(name = "维度[城市-房源模板]访问房源H5日统计", desc = "[VIS_HUS_H5]日周期，统计访问量、访客数", order = 1)
public class TemplateStyleH5Dm extends AbstractMapReduceDmDaoV2<HouseTemplateH5Entity, MrTemplateStyleH5Ro, ConsumerAcqDailyDwEntity> {

    @Override
    public Query sourceDataCondition(LocalDateTime localDateTime) {
        return Query.query(Criteria
                .where(ProFieldEnum.ACTION_DEFINITION.getName()).all(
                        ActionDefinitionEnum.SRD_HUS_H5.getName()
                ));
    }

    @Override
    public HouseTemplateH5Entity convertToTargetData(LocalDateTime localDateTime, MrTemplateStyleH5Ro ro) {
        HouseTemplateH5Entity entity = new HouseTemplateH5Entity();

        entity.setDateDay(Integer.parseInt(dateDayFormat(localDateTime)));
        entity.setPeriodType(PeriodTypeEnum.DAY1.getName());

        entity.setCity(ro.getId().getCity());
        entity.setHouseTemplateId(ro.getId().getHouseTemplateId());

        entity.setPageView(ro.getValue().getPageView());
        entity.setUniqueVisitor(ro.getValue().getUniqueVisitor());
        return entity;
    }
}
