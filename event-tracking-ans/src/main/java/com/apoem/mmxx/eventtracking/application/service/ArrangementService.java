package com.apoem.mmxx.eventtracking.application.service;

import com.apoem.mmxx.eventtracking.infrastructure.po.entity.BizLogEntity;
import com.apoem.mmxx.eventtracking.infrastructure.po.entity.TrackPointEntity;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: ArrangementService </p>
 * <p>Description: 应用层服务：分析服务 </p>
 * <p>Date: 2020/7/15 10:59 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
public interface ArrangementService {

    void today();

    void appoint(String date);

    void appoint(LocalDateTime localDateTime);

    List<TrackPointEntity> scan(String date, boolean bool);

    List<BizLogEntity> scanLog();

    void check(LocalDateTime signDate);
}
