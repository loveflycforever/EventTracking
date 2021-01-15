package com.apoem.mmxx.eventtracking.infrastructure.dao.mongo;

import com.apoem.mmxx.eventtracking.infrastructure.po.dm.LiveGActShopShareEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LiveGActShopShareDao extends MongoRepository<LiveGActShopShareEntity, Object> {

    LiveGActShopShareEntity findByGameIdAndStoreIdAndOccurredAndDateDay(String gameId, String storeId, String occurred, Integer dateDay);
}
