package com.apoem.mmxx.eventtracking.infrastructure.dao.support;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: BaseDao </p>
 * <p>Description: DAO定义 </p>
 * <p>Date: 2020/7/29 11:12 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
public interface BaseDao<T, ID> {

    /**
     * 获取实现类泛型
     *
     * @return 实现类泛型
     */
    @SuppressWarnings("unchecked")
    default Class<T> getTypeClass() {
        Type type = Arrays.stream(getClass().getGenericInterfaces())
                .filter(o -> o instanceof ParameterizedType)
                .filter(o -> BaseDao.class.isAssignableFrom((Class<?>) ((ParameterizedType) o).getRawType()))
                .findAny()
                .orElse(null);

        if (type == null) {
            throw new IllegalArgumentException("Interface BaseDao.class is required");
        }

        return (Class<T>) ((ParameterizedType) type).getActualTypeArguments()[0];
    }

    /**
     * 创建时间，参数补充
     * @param entity 实体
     */
    void createNow(T entity);

    /**
     * 自增键
     *
     * @return nextKey
     */
    ID generatedKey();

    /**
     * 增
     * @param t 实体参数
     * @return 结果
     */
    T insertEntity(T t);

    /**
     * 删
     * @param t 实体参数
     * @return 结果
     */
    T deleteEntity(T t);

    /**
     * 改
     * @param t 实体参数
     * @return 结果
     */
    T updateEntity(T t);

    /**
     * 查
     * @param t 实体参数
     * @return 查询结果
     */
    List<T> selectEntities(T t);

}
