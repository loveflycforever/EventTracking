package com.apoem.mmxx.eventtracking.infrastructure.dao.mongo.ods;

import com.apoem.mmxx.eventtracking.exception.LogSupport;
import com.apoem.mmxx.eventtracking.infrastructure.common.holder.CurrentRequestHolder;
import com.apoem.mmxx.eventtracking.infrastructure.dao.support.AbstractAutoIncrIdDao;
import com.apoem.mmxx.eventtracking.infrastructure.dao.support.MongoGeneratedKeyHandler;
import com.apoem.mmxx.eventtracking.infrastructure.po.ods.ConsumerAcquisitionOdsEntity;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: ConsumerAcquisitionOdsDao </p>
 * <p>Description: C端ODS访问接口 </p>
 * <p>Date: 2020/7/14 12:47 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Repository
@Slf4j
@Accessors(fluent = true)
public class ConsumerAcquisitionOdsDao extends AbstractAutoIncrIdDao<ConsumerAcquisitionOdsEntity> {

    @Getter
    private final MongoTemplate mongoTemplate;
    @Getter
    private final MongoGeneratedKeyHandler mongoGeneratedKeyHandler;

    @Autowired
    public ConsumerAcquisitionOdsDao(MongoTemplate mongoTemplate, MongoGeneratedKeyHandler mongoGeneratedKeyHandler) {
        this.mongoTemplate = mongoTemplate;
        this.mongoGeneratedKeyHandler = mongoGeneratedKeyHandler;
    }

    @Override
    public ConsumerAcquisitionOdsEntity insertEntity(ConsumerAcquisitionOdsEntity entity) {
        ConsumerAcquisitionOdsEntity result = super.insertEntity(entity);
        log.info(LogSupport.info(CurrentRequestHolder.getSerialNumber(), result));
        return result;
    }

    public ConsumerAcquisitionOdsEntity insertEntity(LocalDateTime localDateTime, ConsumerAcquisitionOdsEntity entity) {
        String tableName = mongoGeneratedKeyHandler().getTableName(localDateTime, getTypeClass());
        entity.setId(generatedKey(tableName));
        this.createNow(entity);
        ConsumerAcquisitionOdsEntity result = mongoTemplate().insert(entity, tableName);
        log.info("{} --- {}", Thread.currentThread().getStackTrace()[1], CurrentRequestHolder.getSerialNumber());
        return result;
    }

    private Long generatedKey(String tableName) {
        return mongoGeneratedKeyHandler().getNextId(tableName);
    }

    public Pair<Long, Long> minMaxId(LocalDateTime shardingDate) {
        String tableName = mongoGeneratedKeyHandler().getTableName(shardingDate, getTypeClass());
        ConsumerAcquisitionOdsEntity minIdObject = mongoTemplate().findOne(
                new Query().limit(1).with(new Sort(Sort.Direction.ASC, "id")),
                ConsumerAcquisitionOdsEntity.class, tableName);
        ConsumerAcquisitionOdsEntity maxIdObject = mongoTemplate().findOne(
                new Query().limit(1).with(new Sort(Sort.Direction.DESC, "id")),
                ConsumerAcquisitionOdsEntity.class, tableName);

        return ImmutablePair.of(
                minIdObject != null ? minIdObject.getId() : -1,
                maxIdObject != null ? maxIdObject.getId() : -2
        );
    }

    public List<ConsumerAcquisitionOdsEntity> findByIdRange(long rangeStart, long rangeEnd, LocalDateTime shardingDate) {
        String tableName = mongoGeneratedKeyHandler().getTableName(shardingDate, getTypeClass());
        Query query = Query.query(Criteria.where("id").gte(rangeStart).lte(rangeEnd));
        return mongoTemplate().find(query, this.getTypeClass(), tableName);
    }
}
