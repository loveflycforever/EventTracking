package com.apoem.mmxx.eventtracking.infrastructure.dao.mongo;

import com.apoem.mmxx.eventtracking.infrastructure.po.entity.RelationshipEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: RelateDao </p>
 * <p>Description:  </p>
 * <p>Date: 2020/9/9 13:18 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Repository
public interface RelationshipDao extends MongoRepository<RelationshipEntity, Object> {

    List<RelationshipEntity> findByUniqueId(String uniqueId);

    List<RelationshipEntity> findByOpenId(String openId);
}
