package com.apoem.mmxx.eventtracking.infrastructure.dao.mongo;

import com.apoem.mmxx.eventtracking.infrastructure.po.dm.LiveGActNormalShareEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LiveGActNormalShareDao extends MongoRepository<LiveGActNormalShareEntity, Object> {

    LiveGActNormalShareEntity findByGameIdAndTestAndOccurredAndDateDay(String gameId, String test, String occurred, Integer dateDay);
}
