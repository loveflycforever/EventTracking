package com.apoem.mmxx.eventtracking.application.service;

import org.springframework.scheduling.annotation.Async;

import java.time.LocalDateTime;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: AnalysisService </p>
 * <p>Description: 应用层服务：统计服务 </p>
 * <p>Date: 2020/7/15 10:59 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
public interface StatisticsService {

    void yesterday();

    /**
     * 指定日期 yyyyMMdd
     * @param date yyyyMMdd
     */
    void appoint(String date);


    @Async
    void appoint(LocalDateTime localDateTime);

    void check();
}
