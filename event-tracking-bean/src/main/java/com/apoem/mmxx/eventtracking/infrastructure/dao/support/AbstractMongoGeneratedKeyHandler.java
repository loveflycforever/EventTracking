package com.apoem.mmxx.eventtracking.infrastructure.dao.support;

import com.apoem.mmxx.eventtracking.BaseConstants;
import com.apoem.mmxx.eventtracking.DateUtils;
import com.apoem.mmxx.eventtracking.infrastructure.po.support.MongoTableSequence;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: AbstractMongoGeneratedKeyHandler </p>
 * <p>Description: 抽象MongoDB自增Id处理器 </p>
 * <p>Date: 2020/7/29 11:14 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
public abstract class AbstractMongoGeneratedKeyHandler {

    /**
     * mongoTemplate
     * @return MongoOperations
     */
    public abstract MongoTemplate mongoTemplate();

    /**
     * 获取指定实体的自增键
     * @param klass 指定实体
     * @return 自增键
     */
    public synchronized Long getNextId(Class<?> klass) {
        String tableName = getTableName(klass);
        return query(tableName);
    }

    /**
     * 获取指定表的自增键
     * @param tableName 指定表
     * @return 自增键
     */
    public synchronized Long getNextId(String tableName) {
        return query(tableName);
    }

    private long query(String tableName) {

        Assert.hasText(tableName, "'tableName' must not be empty");

        tableName = StringUtils.lowerCase(tableName);

        Query query = new Query(Criteria.where("name").is(tableName));

        Update update = new Update().inc("sequence", 1);

        FindAndModifyOptions options = new FindAndModifyOptions().returnNew(true).upsert(true);

        MongoTableSequence mongoTableSequence = mongoTemplate().findAndModify(query, update, options, MongoTableSequence.class);

        Assert.notNull(mongoTableSequence, "MongoTableSequence is required");

        return mongoTableSequence.getSequence();
    }

    /**
     * 获取指定实体的表名
     * @param klass 指定实体
     * @return 表名
     */
    public String getTableName(Class<?> klass) {
        return getTableName(LocalDateTime.now(), klass);
    }

    /**
     * 获取指定时间的指定实体的表名（分表）
     * @param localDateTime 指定时间
     * @param klass 指定实体
     * @return 表名
     */
    public String getTableName(LocalDateTime localDateTime, Class<?> klass) {
        String result;

        Document document = AnnotatedElementUtils.findMergedAnnotation(klass, Document.class);

        Assert.notNull(document, klass.getName() + " annotation @Document[value or collection] is required");
        String collection = document.collection();

        Sharding sharding = AnnotatedElementUtils.findMergedAnnotation(klass, Sharding.class);
        String affix = null;
        if (Objects.nonNull(sharding) && ChronoUnit.DAYS.equals(sharding.unit())) {
            affix = localDateTime.format(DateUtils.YYYYMMDD_FORMATTER);
        }

        if (Objects.nonNull(sharding) && ChronoUnit.MONTHS.equals(sharding.unit())) {
            affix = localDateTime.format(DateUtils.YYYYMM_FORMATTER);
        }

        if (Objects.nonNull(sharding) && ChronoUnit.YEARS.equals(sharding.unit())) {
            affix = localDateTime.format(DateUtils.YYYY_FORMATTER);
        }

        if (Objects.nonNull(sharding) && Objects.nonNull(affix) && Affix.SUFFIX.equals(sharding.fix())) {
            result = StringUtils.joinWith(BaseConstants.UNDERSCORE, collection, affix);
        } else if (Objects.nonNull(sharding) && Objects.nonNull(affix) && Affix.PREFIX.equals(sharding.fix())) {
            result = StringUtils.joinWith(BaseConstants.UNDERSCORE, affix, collection);
        } else if (Objects.nonNull(sharding) && Objects.nonNull(affix) && Affix.FORMAT.equals(sharding.fix())) {
            result = MessageFormatter.arrayFormat(collection, new String[]{affix}).getMessage();
        } else {
            result = collection;
        }
        return result;
    }
}