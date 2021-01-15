package com.apoem.mmxx.eventtracking.infrastructure.dao.support;

import com.apoem.mmxx.eventtracking.infrastructure.po.support.AutoIncrIdEntity;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: AbstractDao </p>
 * <p>Description: 抽象DAO </p>
 * <p>Date: 2020/7/29 11:12 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
public abstract class AbstractAutoIncrIdDao<T extends AutoIncrIdEntity> implements BaseDao<T, Long> {

    @Override
    @SuppressWarnings("unchecked")
    public Class<T> getTypeClass() {
        Type type = getClass().getGenericSuperclass();
        return (Class<T>) ((ParameterizedType) type).getActualTypeArguments()[0];
    }

    @Override
    public void createNow(T entity) {
        entity.setCreateTime(new Date());
    }

    /**
     * mongoTemplate
     *
     * @return mongoTemplate
     */
    public abstract MongoTemplate mongoTemplate();

    /**
     * 自增主键处理器
     *
     * @return 处理器
     */
    public abstract AbstractMongoGeneratedKeyHandler mongoGeneratedKeyHandler();

    /**
     * 自增键
     *
     * @return nextKey
     */
    @Override
    public Long generatedKey() {
        return mongoGeneratedKeyHandler().getNextId(getTypeClass());
    }

    @Override
    public T insertEntity(T t) {
        t.setId(generatedKey());
        this.createNow(t);
        String tableName = mongoGeneratedKeyHandler().getTableName(getTypeClass());
        return mongoTemplate().insert(t, tableName);
    }

    public Collection<T> insertEntities(List<T> listT) {
        listT.forEach(o -> {
            o.setId(generatedKey());
            this.createNow(o);
        });
        String tableName = mongoGeneratedKeyHandler().getTableName(getTypeClass());
        return mongoTemplate().insert(listT, tableName);
    }

    @Override
    public T deleteEntity(T t) {
        // maybe empty
        return t;
    }

    @Override
    public T updateEntity(T t) {
        // maybe empty
        return t;
    }

    @Override
    public List<T> selectEntities(T t) {
        // maybe empty
        return Collections.emptyList();
    }
}
