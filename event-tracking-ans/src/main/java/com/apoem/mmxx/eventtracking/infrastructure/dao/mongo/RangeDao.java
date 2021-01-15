package com.apoem.mmxx.eventtracking.infrastructure.dao.mongo;

import com.apoem.mmxx.eventtracking.infrastructure.po.entity.RangeEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: RangeDao </p>
 * <p>Description:  </p>
 * <p>Date: 2020/9/17 10:51 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Repository
public interface RangeDao extends MongoRepository<RangeEntity, Object> {
}
