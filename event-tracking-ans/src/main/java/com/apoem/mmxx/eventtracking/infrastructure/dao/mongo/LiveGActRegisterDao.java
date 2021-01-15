package com.apoem.mmxx.eventtracking.infrastructure.dao.mongo;

import com.apoem.mmxx.eventtracking.infrastructure.po.dm.LiveGActRegisterEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LiveGActRegisterDao extends MongoRepository<LiveGActRegisterEntity, Object> {

    LiveGActRegisterEntity findByGameIdAndDateDay(String gameId, Integer dateDay);
}
