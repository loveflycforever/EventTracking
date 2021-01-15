package com.apoem.mmxx.eventtracking.infrastructure.dao.support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: MongoGeneratedKeyHandler </p>
 * <p>Description: MongoDB自增Id处理 </p>
 * <p>Date: 2020/7/29 11:14 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Component
public class MongoGeneratedKeyHandler extends AbstractMongoGeneratedKeyHandler {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public MongoGeneratedKeyHandler(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public MongoTemplate mongoTemplate() {
        return mongoTemplate;
    }
}