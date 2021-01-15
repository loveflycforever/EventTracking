package com.apoem.mmxx.eventtracking.infrastructure.dao.mongo;

import com.apoem.mmxx.eventtracking.infrastructure.po.entity.TaskTableEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

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
public interface TaskTableDaoE extends MongoRepository<TaskTableEntity, Object> {

    /**
     * 查找
     * @param dateDay 时间
     * @return 结果集
     */
    List<TaskTableEntity> findByDateDay(Integer dateDay);

}
