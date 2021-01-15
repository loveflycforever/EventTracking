package com.apoem.mmxx.eventtracking.infrastructure.repository;

import com.apoem.mmxx.eventtracking.infrastructure.dao.mongo.RelationshipDao;
import com.apoem.mmxx.eventtracking.infrastructure.po.entity.RelationshipEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: RelationshipRepository </p>
 * <p>Description: OpenId 和 UniqueId关系 Repository </p>
 * <p>Date: 2020/7/14 12:47 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Repository
@CacheConfig(cacheNames = {"relationship"})
public class RelationshipRepository implements IRelationshipRepository {

    @Autowired
    private RelationshipDao relationshipDao;

    @Override
    @Cacheable(key="#uniqueId")
    public List<RelationshipEntity> selectWith(String uniqueId) {
        return Optional.ofNullable(relationshipDao
                .findByUniqueId(uniqueId))
                .orElse(Collections.emptyList());
    }
}
