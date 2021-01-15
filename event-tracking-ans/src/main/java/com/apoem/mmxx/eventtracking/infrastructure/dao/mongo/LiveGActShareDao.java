package com.apoem.mmxx.eventtracking.infrastructure.dao.mongo;

import com.apoem.mmxx.eventtracking.infrastructure.po.dm.LiveGActShareEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LiveGActShareDao extends MongoRepository<LiveGActShareEntity, Object> {

    LiveGActShareEntity findByGameIdAndOccurredAndDateDay(String gameId, String occurred, Integer dateDay);
}
