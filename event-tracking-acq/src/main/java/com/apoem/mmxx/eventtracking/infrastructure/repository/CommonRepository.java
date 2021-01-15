package com.apoem.mmxx.eventtracking.infrastructure.repository;

import com.apoem.mmxx.eventtracking.infrastructure.po.support.SerialNumber;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: CommonRepository </p>
 * <p>Description: 通用仓库 </p>
 * <p>Date: 2020/7/21 17:28 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Repository
@Slf4j
public class CommonRepository {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public CommonRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    static FindAndModifyOptions findAndModifyOptions;

    static {
        findAndModifyOptions = new FindAndModifyOptions();
        findAndModifyOptions.upsert(true);
        findAndModifyOptions.returnNew(true);
    }

    public String getSerialNumber() {
        LocalDate currentDate = LocalDate.now();
        String date = dateTimeFormatter.format(currentDate);

        Query query = new Query(Criteria.where("date").is(date));
        Update update = new Update().inc("number", 1);

        SerialNumber andModify = mongoTemplate.findAndModify(query, update, findAndModifyOptions, SerialNumber.class);
        Assert.notNull(andModify, "MsSerialNumber must not null !");
        return String.format("%s%08d", date, andModify.getNumber());
    }
}
