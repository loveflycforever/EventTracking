package com.apoem.mmxx.eventtracking.domain.analysis.service.logic;

import com.apoem.mmxx.eventtracking.domain.acquisition.model.aggregates.Acquisition;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: VisitHouseParaller </p>
 * <p>Description:  </p>
 * <p>Date: 2020/8/12 15:07 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Service
@Slf4j
public class VisitHouseParaller implements BaseParaller {

    @Override
    public void mask(Acquisition<?> acquisition) {
        // 输出到业务表
        log.info(Thread.currentThread().getStackTrace()[1].getClass() + "输出到业务表" + Thread.currentThread().getStackTrace()[1].getMethodName());
    }
}
