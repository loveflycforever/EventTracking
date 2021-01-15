package com.apoem.mmxx.eventtracking.infrastructure.dao.mongo;

import com.apoem.mmxx.eventtracking.infrastructure.po.dm.CustomerHouseActionStatsEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerHouseActionStatsDao extends MongoRepository<CustomerHouseActionStatsEntity, Object> {

    /**
     * 查找
     * @param city 城市
     * @param ids id集合
     * @param dateDay 事件
     * @param houseType 房源类型
     * @return 数据结果
     */
    List<CustomerHouseActionStatsEntity> findByCityAndCustomerIdInAndHouseTypeAndDateDay(String city, List<String> ids, String houseType, Integer dateDay);
}
