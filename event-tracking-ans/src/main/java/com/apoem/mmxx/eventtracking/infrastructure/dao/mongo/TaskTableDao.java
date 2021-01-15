package com.apoem.mmxx.eventtracking.infrastructure.dao.mongo;

import com.apoem.mmxx.eventtracking.infrastructure.dao.support.TaskStatusEnum;
import com.apoem.mmxx.eventtracking.infrastructure.po.entity.TaskTableEntity;
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
 * <p>Name: TaskTableDao </p>
 * <p>Description:  </p>
 * <p>Date: 2020/9/9 15:31 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Repository
public class TaskTableDao {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public TaskTableDao(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public UpdateResult update(TaskTableEntity t, TaskStatusEnum statusEnum) {
        Query query = new Query(Criteria
                .where("date_day").is(t.getDateDay())
                .and("task_name").is(t.getTaskName())
        );
        Date data = new Date();
        Update update = new Update()
                .set("update_time", data)
                .set("status", statusEnum.getName());
        return mongoTemplate.updateMulti(query, update, TaskTableEntity.class);
    }

    public void upsert(TaskTableEntity t, TaskStatusEnum statusEnum) {
        Query query = new Query(Criteria
                .where("date_day").is(t.getDateDay())
                .and("task_name").is(t.getTaskName())
        );

        Date date = new Date();
        Update update = new Update()
                .setOnInsert("date_day", t.getDateDay())
                .setOnInsert("task_name", t.getTaskName())
                .setOnInsert("task_desc", t.getTaskDesc())
                .setOnInsert("order", t.getOrder())
                .setOnInsert("create_time", date)
                .set("update_time", date)
                .set("status", statusEnum.getName());
        UpdateResult updateResult = mongoTemplate.upsert(query, update, TaskTableEntity.class);

    }
}
