package com.apoem.mmxx.eventtracking.infrastructure.dao.mongo;

import com.apoem.mmxx.eventtracking.infrastructure.po.dm.LiveGActShopOccurredEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LiveGActShopOccurredDao extends MongoRepository<LiveGActShopOccurredEntity, Object> {

    LiveGActShopOccurredEntity findByGameIdAndStoreIdAndOccurredAndDateDay(String gameId, String storeId, String occurred, Integer dateDay);
}
