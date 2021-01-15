package com.apoem.mmxx.eventtracking.infrastructure.dao.mongo;

import com.apoem.mmxx.eventtracking.infrastructure.po.dm.LiveGActAgentViewEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LiveGActAgentViewDao extends MongoRepository<LiveGActAgentViewEntity, Object> {

    LiveGActAgentViewEntity findByGameIdAndAgentIdAndDateDay(String gameId, String agentId, Integer dateDay);
}
