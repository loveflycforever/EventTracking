package com.apoem.mmxx.eventtracking.infrastructure.repository;

import com.apoem.mmxx.eventtracking.DateUtils;
import com.apoem.mmxx.eventtracking.infrastructure.po.entity.CustomerActionRecordEntity;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
@Data
public class AcDefRecordRepository {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public AcDefRecordRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    static FindAndModifyOptions findAndModifyOptions;

    static {
        findAndModifyOptions = new FindAndModifyOptions();
        findAndModifyOptions.upsert(true);
        findAndModifyOptions.returnNew(true);
    }
    public Optional<CustomerActionRecordEntity> getLastDayMax(Integer dateDay, String acDef, String city,
                                                              String customerId, String houseId, String houseType) {
        Query query = new Query(Criteria
                .where("date_day").lt(dateDay)
                .and("action_definition").is(acDef)
                .and("city").is(city)
                .and("customer_id").is(customerId)
                .and("house_id").is(houseId)
                .and("house_type").is(houseType)
        ).limit(1).with(new Sort(Sort.Direction.DESC, "date_day"));
        List<CustomerActionRecordEntity> entityList = mongoTemplate.find(query, CustomerActionRecordEntity.class);
        if (CollectionUtils.isEmpty(entityList)) {
            return Optional.empty();
        }
        return Optional.ofNullable(entityList.get(0));
    }

    public void deleteByDateDay(Integer dateDay) {
        mongoTemplate.remove(Query.query(Criteria.where("date_day").is(dateDay)), CustomerActionRecordEntity.class);
    }

    public boolean initToday(LocalDateTime localDateTime, String acDef, String city,
                             String customerId, String houseId, String houseType) {

        Integer dateDay = DateUtils.numericalYyyyMmDd(localDateTime);

        Query query = new Query(Criteria
                .where("date_day").is(dateDay)
                .and("action_definition").is(acDef)
                .and("city").is(city)
                .and("customer_id").is(customerId)
                .and("house_id").is(houseId)
                .and("house_type").is(houseType)
        );

        return mongoTemplate.exists(query, CustomerActionRecordEntity.class);
    }

    public CustomerActionRecordEntity incToday(LocalDateTime localDateTime, String acDef, String city,
                                               String customerId, String houseId, String houseType) {
        Integer dateDay = DateUtils.numericalYyyyMmDd(localDateTime);
        Query query = new Query(Criteria
                .where("date_day").is(dateDay)
                .and("action_definition").is(acDef)
                .and("city").is(city)
                .and("customer_id").is(customerId)
                .and("house_id").is(houseId)
                .and("house_type").is(houseType)
        );

        Date date = new Date();
        Update update = new Update()
                .inc("number", 1)
                .setOnInsert("update_time", date);

        return mongoTemplate.findAndModify(query, update, findAndModifyOptions, CustomerActionRecordEntity.class);
    }

    public void initDateDay(LocalDateTime localDateTime, String acDef, String city,
                            String customerId, String houseId, String houseType) {

        Integer prevDateDay = DateUtils.numericalYyyyMmDd(localDateTime.minusDays(1));

        Query queryPrev = new Query(Criteria
                .where("date_day").is(prevDateDay)
                .and("action_definition").is(acDef)
                .and("city").is(city)
                .and("customer_id").is(customerId)
                .and("house_id").is(houseId)
                .and("house_type").is(houseType)
        );

        List<CustomerActionRecordEntity> prevEntities = mongoTemplate.find(queryPrev, CustomerActionRecordEntity.class);

        long prevDayMaxNumber = 0L;
        if (CollectionUtils.isNotEmpty(prevEntities)) {
            prevDayMaxNumber = prevEntities.get(0).getPrevDayMaxNumber() + prevEntities.get(0).getNumber();
        }

        Integer dateDay = DateUtils.numericalYyyyMmDd(localDateTime);
        Query query = new Query(Criteria
                .where("date_day").is(dateDay)
                .and("action_definition").is(acDef)
                .and("city").is(city)
                .and("customer_id").is(customerId)
                .and("house_id").is(houseId)
                .and("house_type").is(houseType)
        );

        Date date = new Date();
        Update update = new Update();
        update.setOnInsert("date_day", dateDay);
        update.setOnInsert("action_definition", acDef);
        update.setOnInsert("city", city);
        update.setOnInsert("customer_id", customerId);
        update.setOnInsert("house_id", houseId);
        update.setOnInsert("house_type", houseType);
        update.setOnInsert("prev_day_max_number", prevDayMaxNumber);
        update.setOnInsert("number", 0L);
        update.setOnInsert("update_time", date);
        update.setOnInsert("create_time", date);
        mongoTemplate.upsert(query, update, CustomerActionRecordEntity.class);
    }
}