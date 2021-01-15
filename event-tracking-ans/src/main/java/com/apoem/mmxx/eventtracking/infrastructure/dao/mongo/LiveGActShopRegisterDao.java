package com.apoem.mmxx.eventtracking.infrastructure.dao.mongo;

import com.apoem.mmxx.eventtracking.infrastructure.po.dm.LiveGActShopRegisterEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LiveGActShopRegisterDao extends MongoRepository<LiveGActShopRegisterEntity, Object> {

    LiveGActShopRegisterEntity findByGameIdAndStoreIdAndDateDay(String gameId, String storeId, Integer dateDay);
}
