package com.apoem.mmxx.eventtracking.infrastructure.dao.mongo;

import com.apoem.mmxx.eventtracking.infrastructure.po.dm.LiveGActNormalOccurredEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LiveGActNormalOccurredDao extends MongoRepository<LiveGActNormalOccurredEntity, Object> {

    LiveGActNormalOccurredEntity findByGameIdAndTestAndOccurredAndDateDay(String gameId, String test, String occurred, Integer dateDay);
}
