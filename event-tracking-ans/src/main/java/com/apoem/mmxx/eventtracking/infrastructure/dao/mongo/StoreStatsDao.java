package com.apoem.mmxx.eventtracking.infrastructure.dao.mongo;

import com.apoem.mmxx.eventtracking.infrastructure.po.dm.StoreStatsEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: StoreTrendDao </p>
 * <p>Description: 门店后台-门店访问量、访客数、收藏数统计 </p>
 * <p>Date: 2020/8/26 9:29 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Repository
public interface StoreStatsDao extends MongoRepository<StoreStatsEntity, Object> {

    /**
     * 条件查询
     *
     * @param city 城市
     * @param storeId 门店
     * @param dateDay 日
     * @return 实体
     */
    List<StoreStatsEntity> findByCityAndStoreIdInAndHouseTypeAndDateDay(String city, List<String> storeId, String houseType, Integer dateDay);
}
