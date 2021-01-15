package com.apoem.mmxx.eventtracking.infrastructure.dao.mongo;

import com.apoem.mmxx.eventtracking.infrastructure.po.dm.EventStatsEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: PageViewDao </p>
 * <p>Description:  </p>
 * <p>Date: 2020/9/9 13:18 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Repository
public interface EventViewDao extends MongoRepository<EventStatsEntity, Object> {

    /**
     * 条件查询
     * @return 实体
     */
    List<EventStatsEntity> findByDateDay(Integer dateDay);
}
