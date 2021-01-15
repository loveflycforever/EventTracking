package com.apoem.mmxx.eventtracking.domain.acquisition.repository;

import com.apoem.mmxx.eventtracking.domain.acquisition.model.aggregates.Acquisition;
import com.apoem.mmxx.eventtracking.domain.acquisition.model.aggregates.BusinessInfo;
import com.apoem.mmxx.eventtracking.domain.acquisition.model.aggregates.ConsumerInfo;

import java.util.List;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: IAcquisitionRepository </p>
 * <p>Description: 业务数据仓库 </p>
 * <p>Date: 2020/7/17 11:10 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
public interface IAcquisitionWriteRepository {

    /**
     * 保存C端数据
     * @param acquisition 请求
     */
    void saveConsumer(Acquisition<ConsumerInfo> acquisition);

    /**
     * 批量保存B端数据
     * @param acquisitions 请求
     */
    void saveBusinessBatch(List<Acquisition<BusinessInfo>> acquisitions);
}
