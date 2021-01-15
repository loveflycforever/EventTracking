package com.apoem.mmxx.eventtracking.infrastructure.repository;

import com.google.common.collect.Lists;
import com.apoem.mmxx.eventtracking.DateUtils;
import com.apoem.mmxx.eventtracking.domain.analysis.model.vo.BaseStatsVo;
import com.apoem.mmxx.eventtracking.domain.analysis.model.vo.OverviewStatsVo;
import com.apoem.mmxx.eventtracking.infrastructure.convertor.BaseStatsVoConverter;
import com.apoem.mmxx.eventtracking.infrastructure.dao.mongo.*;
import com.apoem.mmxx.eventtracking.infrastructure.po.dm.*;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: OperationSummaryReport </p>
 * <p>Description: 运营总表 </p>
 * <p>Date: 2020/7/15 11:24 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Repository
public class OverviewRepository {

    @Autowired
    private OverviewCustomerStatsDao overviewCustomerStatsDao;

    @Autowired
    private OverviewAgentStatsDao overviewAgentStatsDao;

    @Autowired
    private CourseStatsDao courseStatsDao;

    @Autowired
    private AdStatsDao adStatsDao;

    @Autowired
    private OverviewHouseCollectedStatsDao overviewHouseCollectedStatsDao;

    @Autowired
    private OverviewCustomerH5StatsDao overviewH5StatsDao;

    public OverviewStatsVo checkStats(String city, String inputType, LocalDateTime localDateTime) {
        Integer dateDay = DateUtils.numericalYyyyMmDd(localDateTime);
        OverviewAgentStatsEntity agentStatsEntity = overviewAgentStatsDao.findByCityAndDateDay(city, dateDay);
        OverviewCustomerStatsEntity customerStatsEntity = overviewCustomerStatsDao.findByCityAndDateDay(city, dateDay);
        OverviewHouseCollectedStatsEntity houseCollectedStatsEntity = overviewHouseCollectedStatsDao.findByCityAndInputTypeAndDateDay(city, inputType, dateDay);
        OverviewCustomerH5StatsEntity overviewH5StatsEntity = overviewH5StatsDao.findByCityAndDateDay(city, dateDay);
        return BaseStatsVoConverter.deserialize(
                agentStatsEntity,
                customerStatsEntity,
                houseCollectedStatsEntity,
                overviewH5StatsEntity);
    }

    public List<BaseStatsVo> adCheckStats(String city, List<String> ids, LocalDateTime localDateTime) {
        Integer numericalAt = DateUtils.numericalYyyyMmDd(localDateTime);

        List<AdvertisementEntity> entityList = new ArrayList<>(ids.size());
        Lists.partition(ids, 100).forEach(l -> {
            List<AdvertisementEntity> list =  adStatsDao.findByCityAndAdvIdInAndDateDay(city, l, numericalAt);
            if (CollectionUtils.isNotEmpty(list)) {
                entityList.addAll(list);
            }
        });

        return BaseStatsVoConverter.deserialize6(entityList);
    }

    public List<BaseStatsVo> courseCheckStats(String city, List<String> ids, LocalDateTime localDateTime) {
        Integer numericalAt = DateUtils.numericalYyyyMmDd(localDateTime);

        List<CourseEntity> entityList = new ArrayList<>(ids.size());
        Lists.partition(ids, 100).forEach(l -> {
            List<CourseEntity> list = courseStatsDao.findByCityAndCourseIdInAndDateDay(city, l, numericalAt);
            if (CollectionUtils.isNotEmpty(list)) {
                entityList.addAll(list);
            }
        });

        return BaseStatsVoConverter.deserialize7(entityList);
    }
}
