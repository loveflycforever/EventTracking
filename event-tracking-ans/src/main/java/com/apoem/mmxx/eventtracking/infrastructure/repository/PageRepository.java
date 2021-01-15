package com.apoem.mmxx.eventtracking.infrastructure.repository;

import com.apoem.mmxx.eventtracking.DateUtils;
import com.apoem.mmxx.eventtracking.domain.analysis.model.vo.EventStatsVo;
import com.apoem.mmxx.eventtracking.domain.analysis.model.vo.PageStatsVo;
import com.apoem.mmxx.eventtracking.domain.analysis.model.vo.TrailStatsVo;
import com.apoem.mmxx.eventtracking.domain.trackpointdisposition.model.vo.TrackPointVo;
import com.apoem.mmxx.eventtracking.infrastructure.dao.mongo.CustomerTrailDao;
import com.apoem.mmxx.eventtracking.infrastructure.dao.mongo.EventViewDao;
import com.apoem.mmxx.eventtracking.infrastructure.dao.mongo.PageViewDao;
import com.apoem.mmxx.eventtracking.infrastructure.dao.mongo.RelationshipDao;
import com.apoem.mmxx.eventtracking.infrastructure.enums.AvenueEnum;
import com.apoem.mmxx.eventtracking.infrastructure.enums.EndpointEnum;
import com.apoem.mmxx.eventtracking.infrastructure.po.dm.CustomerTrailEntity;
import com.apoem.mmxx.eventtracking.infrastructure.po.dm.EventStatsEntity;
import com.apoem.mmxx.eventtracking.infrastructure.po.dm.PageStatsEntity;
import com.apoem.mmxx.eventtracking.infrastructure.po.entity.RelationshipEntity;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: PageRepository </p>
 * <p>Description: 房源报表 </p>
 * <p>Date: 2020/7/15 11:28 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Repository
public class PageRepository {

    @Autowired
    private PageViewDao pageViewDao;

    @Autowired
    private RelationshipDao relationshipDao;

    @Autowired
    private EventViewDao eventViewDao;

    @Autowired
    private CustomerTrailDao customerTrailDao;

    @Autowired
    private TrackPointRepository trackPointRepository;

    public List<PageStatsVo> scanPage(LocalDateTime date) {
        Integer numericalAt = DateUtils.numericalYyyyMmDd(date);
        List<PageStatsEntity> entities = pageViewDao.findByDateDay(numericalAt);
        List<PageStatsVo> result = Collections.emptyList();
        if (CollectionUtils.isNotEmpty(entities)) {
            result = entities.stream().map(this::deserialize).collect(Collectors.toList());
        }
        return result;
    }

    private PageStatsVo deserialize(PageStatsEntity pageStatsEntity) {
        String pageName = trackPointRepository.getPageName(pageStatsEntity.getPageName());

        PageStatsVo pageStatsVo = new PageStatsVo();
        pageStatsVo.setCity(pageStatsEntity.getCity());
        pageStatsVo.setPageName(pageStatsEntity.getPageName());
        pageStatsVo.setPageNameCn(pageName);
        pageStatsVo.setPageView(pageStatsEntity.getPageView());
        pageStatsVo.setUniqueVisitor(pageStatsEntity.getUniqueVisitor());
        return pageStatsVo;
    }

    public List<EventStatsVo> scanEvent(LocalDateTime date) {
        Integer numericalAt = DateUtils.numericalYyyyMmDd(date);
        List<EventStatsEntity> entities = eventViewDao.findByDateDay(numericalAt);
        List<EventStatsVo> result = Collections.emptyList();
        if (CollectionUtils.isNotEmpty(entities)) {
            result = entities.stream().map(this::deserialize).collect(Collectors.toList());
        }
        return result;

    }

    private EventStatsVo deserialize(EventStatsEntity entity) {
        TrackPointVo trackPointVo = trackPointRepository.selectWith(
                EndpointEnum.CONSUMER.getName(),
                entity.getPageName(),
                entity.getMethodName(),
                entity.getEventType(),
                AvenueEnum.WMP.getName());

        String methodNameCn = "";
        String pageNameCn = "";
        if (trackPointVo != null) {
            methodNameCn = trackPointVo.getMethodNameCn();
            pageNameCn = trackPointVo.getPageNameCn();
        }

        EventStatsVo eventStatsVo = new EventStatsVo();
        eventStatsVo.setPageName(entity.getPageName());
        eventStatsVo.setPageNameCn(pageNameCn);
        eventStatsVo.setMethodName(entity.getMethodName());
        eventStatsVo.setMethodNameCn(methodNameCn);
        eventStatsVo.setEventType(entity.getEventType());
        eventStatsVo.setCity(entity.getCity());
        eventStatsVo.setPageView(entity.getPageView());
        eventStatsVo.setUniqueVisitor(entity.getUniqueVisitor());
        return eventStatsVo;
    }

    public List<TrailStatsVo> scanTrail(LocalDateTime date) {
        Integer numericalAt = DateUtils.numericalYyyyMmDd(date);
        List<CustomerTrailEntity> entities = customerTrailDao.findByDateDay(numericalAt);
        List<TrailStatsVo> result = Collections.emptyList();
        if (CollectionUtils.isNotEmpty(entities)) {
            result = entities.stream().map(this::deserialize).collect(Collectors.toList());
        }
        return result;
    }

    private TrailStatsVo deserialize(CustomerTrailEntity entity) {
        TrackPointVo trackPointVo = trackPointRepository.selectWith(
                EndpointEnum.CONSUMER.getName(),
                entity.getPageName(),
                entity.getMethodName(),
                entity.getEventType(),
                AvenueEnum.WMP.getName());

        String methodNameCn = "";
        String pageNameCn = "";
        if (trackPointVo != null) {
            methodNameCn = trackPointVo.getMethodNameCn();
            pageNameCn = trackPointVo.getPageNameCn();
        }

        List<RelationshipEntity> relationshipEntities = relationshipDao.findByOpenId(entity.getCustomerId());
        String uniqueId = "";
        if (CollectionUtils.isNotEmpty(relationshipEntities)) {
            uniqueId = relationshipEntities.get(0).getUniqueId();
        }

        TrailStatsVo eventStatsVo = new TrailStatsVo();
        eventStatsVo.setCity(entity.getCity());
        eventStatsVo.setCustomerUniqueId(uniqueId);
        eventStatsVo.setCustomerOpenId(entity.getCustomerId());
        eventStatsVo.setAgentId(entity.getAgentId());
        eventStatsVo.setOpTime(entity.getOpTime());
        eventStatsVo.setOriginOpTime(entity.getOriginOpTime());
        eventStatsVo.setOdsId(entity.getOdsId());
        eventStatsVo.setActionType(entity.getActionType());
        eventStatsVo.setViewTimes(entity.getViewTimes());
        eventStatsVo.setDuration(entity.getDuration());
        eventStatsVo.setActionDefinition(entity.getActionDefinition());
        eventStatsVo.setHouseId(entity.getHouseId());
        eventStatsVo.setHouseType(entity.getHouseType());
        eventStatsVo.setPageName(entity.getPageName());
        eventStatsVo.setPageNameCn(pageNameCn);
        eventStatsVo.setMethodName(entity.getMethodName());
        eventStatsVo.setMethodNameCn(methodNameCn);
        eventStatsVo.setEventType(entity.getEventType());
        return eventStatsVo;
    }
}
