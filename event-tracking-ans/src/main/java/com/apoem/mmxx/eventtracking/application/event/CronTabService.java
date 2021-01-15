package com.apoem.mmxx.eventtracking.application.event;

import com.apoem.mmxx.eventtracking.application.service.ArrangementService;
import com.apoem.mmxx.eventtracking.application.service.StatisticsService;
import com.apoem.mmxx.eventtracking.infrastructure.BizServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: CronTabService </p>
 * <p>Description:  </p>
 * <p>Date: 2020/9/29 18:16 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Service
@Slf4j
public class CronTabService {

    @Value("#{${cron_tab_service.enable}}")
    private boolean enable;

    @Autowired
    private StatisticsService statisticsService;
    @Autowired
    private ArrangementService arrangementService;

    @Autowired
    private BizServiceImpl bizService2;

    @Scheduled(cron = "0 0/1 * * * ? ")
    public void check() {
        if(enable) {
            log.info("Quack~quack~quack~quack~quack~quack~quack~quack~quack~~~");
        }
    }

    @Scheduled(cron = "0 0/3 * * * ? ")
    public void trail() {
        if(enable) {
            LocalDateTime localDateTime = LocalDateTime.now();
            arrangementService.appoint(localDateTime);
        }
    }

    @Scheduled(cron = "0 15 0 * * ? ")
    public void ddd() {
        if(enable) {
            // 拉取业务数据
            bizService2.requestCityData();
        }
    }

    @Scheduled(cron = "0 30 0 * * ? ")
    public void dm() {
        if(enable) {
            // 要在拉取业务数据之后执行
            LocalDateTime localDateTime = LocalDateTime.now().minusDays(1);
            statisticsService.appoint(localDateTime);
        }
    }
}