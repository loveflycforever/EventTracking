package com.apoem.mmxx.eventtracking.infrastructure.dao.mongo;

import com.apoem.mmxx.eventtracking.infrastructure.po.entity.TrailRecordEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: TrailRecordDao </p>
 * <p>Description:  </p>
 * <p>Date: 2020/9/28 9:11 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Repository
public interface TrailRecordDao extends MongoRepository<TrailRecordEntity, Object> {

    TrailRecordEntity findByDateDay(Integer dayDate);
}
