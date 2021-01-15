package com.apoem.mmxx.eventtracking.infrastructure.repository;

import com.apoem.mmxx.eventtracking.infrastructure.dao.mongo.ods.BusinessAcquisitionOdsDao;
import com.apoem.mmxx.eventtracking.infrastructure.dao.mongo.ods.ConsumerAcquisitionOdsDao;
import com.apoem.mmxx.eventtracking.infrastructure.dao.support.LongIdPair;
import com.apoem.mmxx.eventtracking.infrastructure.po.ods.BusinessAcquisitionOdsEntity;
import com.apoem.mmxx.eventtracking.infrastructure.po.ods.ConsumerAcquisitionOdsEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: AcquisitionOdsRepository </p>
 * <p>Description: 请求处理仓库 </p>
 * <p>Date: 2020/7/14 12:47 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Repository
@Slf4j
public class AcquisitionOdsReadRepository implements IAcquisitionReadRepository {

    @Resource
    private BusinessAcquisitionOdsDao businessAcquisitionOdsDao;

    @Resource
    private ConsumerAcquisitionOdsDao consumerAcquisitionOdsDao;

    public LongIdPair consumerAcqDailyMinMaxId(LocalDateTime localDateTime) {
        Pair<Long, Long> pair = consumerAcquisitionOdsDao.minMaxId(localDateTime);
        return new LongIdPair(pair);
    }

    public LongIdPair businessAcqDailyMinMaxId(LocalDateTime localDateTime) {
        Pair<Long, Long> pair = businessAcquisitionOdsDao.minMaxId(localDateTime);
        return new LongIdPair(pair);
    }


    public List<ConsumerAcquisitionOdsEntity> findConsumerAcq(long valueStart, long valueEnd, LocalDateTime localDateTime) {

        return consumerAcquisitionOdsDao.findByIdRange(valueStart, valueEnd, localDateTime);
    }


    public List<BusinessAcquisitionOdsEntity> findBusinessAcq(long valueStart, long valueEnd, LocalDateTime localDateTime) {

        return businessAcquisitionOdsDao.findByIdRange(valueStart, valueEnd, localDateTime);
    }
}
