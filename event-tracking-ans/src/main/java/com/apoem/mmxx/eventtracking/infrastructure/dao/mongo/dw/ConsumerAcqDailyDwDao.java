package com.apoem.mmxx.eventtracking.infrastructure.dao.mongo.dw;

import com.apoem.mmxx.eventtracking.infrastructure.po.dw.ConsumerAcqDailyDwEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: ConsumerAcquisition30DwDao </p>
 * <p>Description: C端DW访问接口-30日数据 </p>
 * <p>Date: 2020/7/14 12:47 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Repository
public interface ConsumerAcqDailyDwDao extends MongoRepository<ConsumerAcqDailyDwEntity, Object> {
}
