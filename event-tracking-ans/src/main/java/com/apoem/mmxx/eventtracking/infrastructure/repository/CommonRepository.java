package com.apoem.mmxx.eventtracking.infrastructure.repository;

import com.apoem.mmxx.eventtracking.infrastructure.dao.support.UpdateResultWrapper;
import com.apoem.mmxx.eventtracking.infrastructure.po.support.ForceReinitializeToken;
import com.apoem.mmxx.eventtracking.infrastructure.po.support.SerialNumber;
import com.mongodb.client.result.UpdateResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.UUID;
import java.util.stream.LongStream;

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

    public void initializeNextMonthSerialNumber() {
        LocalDate nextMonthDay = LocalDate.now().plusMonths(1);
        LocalDate firstDayOfNextMonth = nextMonthDay.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate lastDayOfNextMonth = nextMonthDay.with(TemporalAdjusters.lastDayOfMonth());

        long startInclusive = Long.parseLong(dateTimeFormatter.format(firstDayOfNextMonth));
        long endInclusive = Long.parseLong(dateTimeFormatter.format(lastDayOfNextMonth));
        LongStream.rangeClosed(startInclusive, endInclusive).forEach(date -> {
            Query query = new Query(Criteria.where("date").is(date));
            Update update = new Update().setOnInsert("number", 0);
            UpdateResult upsert = mongoTemplate.upsert(query, update, SerialNumber.class);
            log.info(new UpdateResultWrapper(upsert).toString());
        });
    }

    public void forceReinitializeSerialNumber(String token, String startDate, String endDate) {

        isDate(startDate);
        isDate(endDate);

        long startInclusive = Long.parseLong(startDate);
        long endInclusive = Long.parseLong(endDate);

        isBefore(startInclusive, endInclusive);

        Query qy = new Query(Criteria.where("token").is(token));
        List<ForceReinitializeToken> entities = mongoTemplate.find(qy, ForceReinitializeToken.class);
        if (CollectionUtils.isEmpty(entities)) {
            return;
        }

        LongStream.rangeClosed(startInclusive, endInclusive).forEach(index -> {
            Query query = new Query(Criteria.where("id").is(index));
            Update update = new Update().set("number", 0);
            UpdateResult updateResult = mongoTemplate.upsert(query, update, SerialNumber.class);
            log.info(new UpdateResultWrapper(updateResult).toString());
        });
    }

    private void isBefore(long startInclusive, long endInclusive) {
        if(endInclusive < startInclusive) {
            throw new IllegalStateException();
        }
    }

    private void isDate(String date) {
        try {
            dateTimeFormatter.parse(date);
        } catch (Exception e) {
            throw new IllegalStateException();
        }
    }

    public void acquireForceReinitializeToken() {
        UUID uuid = UUID.randomUUID();
        ForceReinitializeToken entity = new ForceReinitializeToken();
        entity.setToken(uuid.toString());
        mongoTemplate.insert(entity);
    }
}
