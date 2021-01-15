package com.apoem.mmxx.eventtracking.domain.analysis.service.logic;

import com.apoem.mmxx.eventtracking.domain.acquisition.model.aggregates.Acquisition;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: BaseParaller </p>
 * <p>Description:  </p>
 * <p>Date: 2020/8/12 14:54 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
public interface BaseParaller {

    /**
     * 掩饰方法
     * @param acquisition 请求数据
     */
    void mask(Acquisition<?> acquisition);
}
