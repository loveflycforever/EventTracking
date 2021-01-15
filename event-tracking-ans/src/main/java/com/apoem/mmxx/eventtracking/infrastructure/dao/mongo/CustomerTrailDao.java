package com.apoem.mmxx.eventtracking.infrastructure.dao.mongo;

import com.apoem.mmxx.eventtracking.infrastructure.po.dm.CustomerTrailEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: CustomerTrailDao </p>
 * <p>Description: 客户轨迹 </p>
 * <p>Date: 2020/8/27 17:41 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Repository
public interface CustomerTrailDao extends MongoRepository<CustomerTrailEntity, Object> {

    /**
     * 删除
     * @param dateDay 日期
     */
    void deleteByDateDay(Integer dateDay);

    List<CustomerTrailEntity> findByDateDay(Integer dateDay);

}
