package com.apoem.mmxx.eventtracking.domain.analysis.service;

import com.apoem.mmxx.eventtracking.application.service.ArrangementService;
import com.apoem.mmxx.eventtracking.DateUtils;
import com.apoem.mmxx.eventtracking.domain.analysis.service.engine.StatisticsManager;
import com.apoem.mmxx.eventtracking.infrastructure.po.entity.BizLogEntity;
import com.apoem.mmxx.eventtracking.infrastructure.po.entity.TrackPointEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: AnalysisServiceImpl </p>
 * <p>Description:  </p>
 * <p>Date: 2020/8/17 9:35 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Service
@Slf4j
public class ArrangementServiceImpl implements ArrangementService {

    @Autowired
    private StatisticsManager acquisitionManager2;

    @Override
    @Async
    public void today() {
        LocalDateTime localDateTime = LocalDateTime.now();
        acquisitionManager2.trailWhole(localDateTime);
    }

    @Override
    @Async
    public void appoint(String date) {
        LocalDateTime localDateTime = DateUtils.localDateTime2(date);
        acquisitionManager2.trailWhole(localDateTime);
    }

    @Override
    @Async
    public void appoint(LocalDateTime localDateTime) {
        acquisitionManager2.trailStep(localDateTime);
    }

    @Override
    public List<TrackPointEntity> scan(String date, boolean bool) {
        LocalDateTime localDateTime = DateUtils.localDateTime2(date);
        return acquisitionManager2.scan(localDateTime, bool);
    }

    @Override
    public List<BizLogEntity> scanLog() {
        return acquisitionManager2.scanLog();
    }

    @Override
    public void check(LocalDateTime signDate) {
        acquisitionManager2.check(signDate);
    }
}
