package com.apoem.mmxx.eventtracking.infrastructure.dao.mongo;

import com.apoem.mmxx.eventtracking.infrastructure.enums.RangeTypeEnum;
import com.apoem.mmxx.eventtracking.infrastructure.convertor.support.IRangeDao2;
import com.apoem.mmxx.eventtracking.infrastructure.po.entity.RangeEntity;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: RangeDao </p>
 * <p>Description:  </p>
 * <p>Date: 2020/9/17 10:51 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Repository
@CacheConfig(cacheNames = {"comm_range"})
public class RangeDao2 implements IRangeDao2 {
    private final MongoTemplate mongoTemplate;

    @Autowired
    public RangeDao2(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public UpdateResult makeDateDayHistory(Integer dateDay, String cityCode, RangeTypeEnum rangeTypeEnum) {
        Query query = new Query(Criteria
                .where("city").is(cityCode)
                .and("range_type_name").is(rangeTypeEnum.getName())
                .and("date_day").is(dateDay)
                .and("mark").is(0)
        );
        Update update = new Update().set("mark", -1);
        return mongoTemplate.updateMulti(query, update, RangeEntity.class);
    }

    @Override
    public UpdateResult deletePrevDateDay(Integer dateDay, String cityCode, RangeTypeEnum rangeTypeEnum) {
        Query query = new Query(Criteria
                .where("city").is(cityCode)
                .and("range_type_name").is(rangeTypeEnum.getName())
                .and("date_day").lt(dateDay)
        );
        Update update = new Update().set("mark", 1);
        return mongoTemplate.updateMulti(query, update, RangeEntity.class);
    }

    @Override
    public UpdateResult deleteDateDayHistory(Integer dateDay, String cityCode, RangeTypeEnum rangeTypeEnum) {
        Query query = new Query(Criteria
                .where("city").is(cityCode)
                .and("range_type_name").is(rangeTypeEnum.getName())
                .and("date_day").is(dateDay)
                .and("mark").is(-1)
        );
        Update update = new Update().set("mark", 1);
        return mongoTemplate.updateMulti(query, update, RangeEntity.class);
    }

    @Override
    @Cacheable(key="#cityCode + '_' + #rangeTypeEnum.name")
    public List<RangeEntity> findByCityAndRangeTypeName(String cityCode, RangeTypeEnum rangeTypeEnum) {
        Query query = new Query(Criteria
                .where("city").is(cityCode)
                .and("range_type_name").is(rangeTypeEnum.getName())
                .and("mark").lt(1)
        );
        return mongoTemplate.find(query, RangeEntity.class);
    }
}
