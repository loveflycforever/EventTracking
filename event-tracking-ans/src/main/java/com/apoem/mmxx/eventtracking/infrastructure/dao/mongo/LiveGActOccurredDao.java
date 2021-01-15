package com.apoem.mmxx.eventtracking.infrastructure.dao.mongo;

import com.apoem.mmxx.eventtracking.infrastructure.po.dm.LiveGActOccurredEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LiveGActOccurredDao extends MongoRepository<LiveGActOccurredEntity, Object> {

    LiveGActOccurredEntity findByGameIdAndOccurredAndDateDay(String gameId, String occurred, Integer dateDay);
}
