package com.apoem.mmxx.eventtracking.infrastructure.dao.mongo;

import com.apoem.mmxx.eventtracking.infrastructure.po.dm.CustomerRangeGlanceEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: CustomerRangeGlanceDao </p>
 * <p>Description: 客源分析-点击统计 </p>
 * <p>Date: 2020/9/1 10:51 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Repository
public interface CustomerRangeGlanceDao extends MongoRepository<CustomerRangeGlanceEntity, Object> {

    /**
     * 条件查询
     *
     * @param city 城市
     * @param customerId 客源
     * @param beginDateDay 开始日
     * @param endDateDay 结束日
     * @return 实体
     */
    List<CustomerRangeGlanceEntity> findByCityAndCustomerIdAndDateDayBetween(String city, String customerId, Integer beginDateDay, Integer endDateDay);

}
