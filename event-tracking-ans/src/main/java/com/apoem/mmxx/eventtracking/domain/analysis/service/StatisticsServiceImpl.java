package com.apoem.mmxx.eventtracking.domain.analysis.service;

import com.apoem.mmxx.eventtracking.DateUtils;
import com.apoem.mmxx.eventtracking.application.service.StatisticsService;
import com.apoem.mmxx.eventtracking.domain.analysis.service.engine.StatisticsManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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
public class StatisticsServiceImpl implements StatisticsService {

    @Autowired
    private StatisticsManager statisticsManager;

    @Override
    @Async
    public void yesterday() {
        LocalDateTime localDateTime = LocalDateTime.now().minusDays(1);
        process(localDateTime);
    }

    @Override
    @Async
    public void appoint(String date) {
        LocalDateTime localDateTime = DateUtils.localDateTime2(date);
        process(localDateTime);
    }

    @Override
    @Async
    public void appoint(LocalDateTime localDateTime) {
        process(localDateTime);
    }

    private void process(LocalDateTime date) {
        log.info("开始执行每日任务【{}】", DateUtils.numericalYyyyMmDd(date));

        // 初始化
        statisticsManager.initial(date);
        // 预处理
        statisticsManager.prepare(date);
        // 执行
        statisticsManager.execute(date);
        // 完成后
        statisticsManager.afterCompleted(date);

        log.info("结束执行每日任务【{}】", DateUtils.numericalYyyyMmDd(date));
    }

    @Override
    public void check() {
        log.info("Quack~quack~quack~quack~quack~quack~quack~quack~quack~~~");
    }
}
