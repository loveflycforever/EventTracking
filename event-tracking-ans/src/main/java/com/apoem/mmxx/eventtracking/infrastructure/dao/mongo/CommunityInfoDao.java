package com.apoem.mmxx.eventtracking.infrastructure.dao.mongo;

import com.apoem.mmxx.eventtracking.infrastructure.po.entity.CommunityInfoEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: CommunityInfoDao </p>
 * <p>Description:  </p>
 * <p>Date: 2020/9/25 16:09 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author somebody
 * @version v1.0
 */
@Repository
public interface CommunityInfoDao extends MongoRepository<CommunityInfoEntity, Object> {

    CommunityInfoEntity findByCityAndCommunityId(String city, String communityId);

}
