package com.apoem.mmxx.eventtracking.infrastructure.repository;

import com.apoem.mmxx.eventtracking.infrastructure.enums.EndpointEnum;
import com.apoem.mmxx.eventtracking.domain.acquisition.model.aggregates.Acquisition;
import com.apoem.mmxx.eventtracking.domain.acquisition.model.aggregates.BusinessInfo;
import com.apoem.mmxx.eventtracking.domain.acquisition.model.aggregates.ConsumerInfo;
import com.apoem.mmxx.eventtracking.domain.acquisition.repository.IAcquisitionWriteRepository;
import com.apoem.mmxx.eventtracking.infrastructure.common.holder.CurrentRequestHolder;
import com.apoem.mmxx.eventtracking.infrastructure.convertor.AcquisitionConverter;
import com.apoem.mmxx.eventtracking.infrastructure.dao.mongo.ods.BusinessAcquisitionOdsDao;
import com.apoem.mmxx.eventtracking.infrastructure.dao.mongo.ods.ConsumerAcquisitionOdsDao;
import com.apoem.mmxx.eventtracking.infrastructure.po.ods.BusinessAcquisitionOdsEntity;
import com.apoem.mmxx.eventtracking.infrastructure.po.ods.ConsumerAcquisitionOdsEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

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
public class AcquisitionOdsWriteRepository implements IAcquisitionWriteRepository {

    @Resource
    private BusinessAcquisitionOdsDao businessAcquisitionOdsDao;

    @Resource
    private ConsumerAcquisitionOdsDao consumerAcquisitionOdsDao;

    @Override
    public void saveConsumer(Acquisition<ConsumerInfo> acquisition) {
        log.info(Thread.currentThread().getStackTrace()[1] + " --- " + CurrentRequestHolder.getSerialNumber());
        EndpointEnum endpoint = acquisition.getEndpoint();
        if(endpoint == EndpointEnum.CONSUMER) {
            ConsumerAcquisitionOdsEntity entity = AcquisitionConverter.serializeConsumer(acquisition);
            consumerAcquisitionOdsDao.insertEntity(entity);
        }
    }

    @Override
    public void saveBusinessBatch(List<Acquisition<BusinessInfo>> acquisitions) {
        List<BusinessAcquisitionOdsEntity> collect = acquisitions.stream().map(AcquisitionConverter::serializeBusiness).collect(Collectors.toList());
        businessAcquisitionOdsDao.insertEntities(collect);
    }
}
