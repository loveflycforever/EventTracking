package com.apoem.mmxx.eventtracking.infrastructure.dao.mongo;

import com.apoem.mmxx.eventtracking.infrastructure.po.entity.RelationshipEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.Date;

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
public class RelationshipDao2 {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public RelationshipDao2(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public void appendRecord(String uniqueId, String openId) {
        Query query = new Query(
                Criteria.where("unique_id").is(uniqueId)
                        .and("open_id").is(openId)
        );

        Update update = new Update();
        update.setOnInsert("unique_id", uniqueId);
        update.setOnInsert("open_id", openId);
        Date date = new Date();
        update.setOnInsert("update_time", date);
        update.setOnInsert("create_time", date);

        mongoTemplate.upsert(query, update, RelationshipEntity.class);
    }
}
