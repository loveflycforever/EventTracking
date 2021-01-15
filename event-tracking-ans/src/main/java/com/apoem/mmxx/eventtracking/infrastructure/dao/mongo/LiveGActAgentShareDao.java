package com.apoem.mmxx.eventtracking.infrastructure.dao.mongo;

import com.apoem.mmxx.eventtracking.infrastructure.po.dm.LiveGActAgentShareEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LiveGActAgentShareDao extends MongoRepository<LiveGActAgentShareEntity, Object> {

    LiveGActAgentShareEntity findByGameIdAndAgentIdAndOccurredAndDateDay(String gameId, String agentId, String occurred, Integer dateDay);
}
