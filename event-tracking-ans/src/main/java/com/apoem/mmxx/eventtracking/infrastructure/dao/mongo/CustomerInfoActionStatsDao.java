package com.apoem.mmxx.eventtracking.infrastructure.dao.mongo;

import com.apoem.mmxx.eventtracking.infrastructure.po.dm.CustomerInfoActionStatsEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerInfoActionStatsDao extends MongoRepository<CustomerInfoActionStatsEntity, Object> {
    /**
     * 查找
     * @param city 城市
     * @param ids id集合
     * @param dateDay 事件
     * @return 数据结果
     */
    List<CustomerInfoActionStatsEntity> findByCityAndCustomerIdInAndDateDay(String city, List<String> ids, Integer dateDay);
}
