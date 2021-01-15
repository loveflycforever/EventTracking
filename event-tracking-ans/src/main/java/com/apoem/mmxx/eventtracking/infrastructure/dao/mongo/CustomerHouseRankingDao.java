package com.apoem.mmxx.eventtracking.infrastructure.dao.mongo;

import com.apoem.mmxx.eventtracking.infrastructure.po.dm.CustomerHouseRankingEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: CustomerHouseRankingDao </p>
 * <p>Description: 客源分析-房源统计 </p>
 * <p>Date: 2020/8/27 10:23 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Repository
public interface CustomerHouseRankingDao extends MongoRepository<CustomerHouseRankingEntity, Object> {
    void deleteByDateDay(Integer dateDay);
}
