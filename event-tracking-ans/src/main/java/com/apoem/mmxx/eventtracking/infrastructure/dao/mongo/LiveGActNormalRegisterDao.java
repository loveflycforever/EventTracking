package com.apoem.mmxx.eventtracking.infrastructure.dao.mongo;

import com.apoem.mmxx.eventtracking.infrastructure.po.dm.LiveGActNormalRegisterEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LiveGActNormalRegisterDao extends MongoRepository<LiveGActNormalRegisterEntity, Object> {

    LiveGActNormalRegisterEntity findByGameIdAndTestAndDateDay(String gameId, String test, Integer dateDay);
}
