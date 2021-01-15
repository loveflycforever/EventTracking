package com.apoem.mmxx.eventtracking.infrastructure.dao.mongo;

import com.apoem.mmxx.eventtracking.infrastructure.po.dm.LiveGActParticipantsEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LiveGActParticipantsDao extends MongoRepository<LiveGActParticipantsEntity, Object> {

    LiveGActParticipantsEntity findByGameIdAndOccurredAndDateDay(String gameId, String occurred, Integer dateDay);
}
