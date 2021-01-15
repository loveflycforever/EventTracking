package com.apoem.mmxx.eventtracking.infrastructure.dao.mongo;

import com.apoem.mmxx.eventtracking.infrastructure.po.entity.TrailRecordEntity;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.Date;

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
public class TrailRecordDao2 {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public TrailRecordDao2(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public void upsert(TrailRecordEntity t) {
        Query query = new Query(Criteria
                .where("date_day").is(t.getDateDay())
        );

        Date date = new Date();
        Update update = new Update()
                .setOnInsert("date_day", t.getDateDay())
//                .setOnInsert("last_id", t.getLastId())
                .setOnInsert("create_time", date)
                .set("last_id", t.getLastId())
                .set("update_time", date);
        UpdateResult updateResult = mongoTemplate.upsert(query, update, TrailRecordEntity.class);
    }
}
