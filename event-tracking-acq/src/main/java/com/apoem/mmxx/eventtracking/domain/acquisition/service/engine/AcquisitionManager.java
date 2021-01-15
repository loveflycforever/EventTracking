package com.apoem.mmxx.eventtracking.domain.acquisition.service.engine;

import com.apoem.mmxx.eventtracking.domain.acquisition.repository.IAcquisitionWriteRepository;
import com.apoem.mmxx.eventtracking.domain.acquisition.model.aggregates.Acquisition;
import com.apoem.mmxx.eventtracking.domain.acquisition.model.aggregates.BusinessInfo;
import com.apoem.mmxx.eventtracking.domain.acquisition.model.aggregates.ConsumerInfo;
import com.apoem.mmxx.eventtracking.infrastructure.common.holder.CurrentRequestHolder;
import com.apoem.mmxx.eventtracking.infrastructure.convertor.AcquisitionConverter;
import com.apoem.mmxx.eventtracking.interfaces.assembler.BusinessAcquisitionCmd;
import com.apoem.mmxx.eventtracking.interfaces.assembler.ConsumerAcquisitionCmd;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: AcquisitionHandle </p>
 * <p>Description: 领域层服务：上报数据服务</p>
 * <p>Date: 2020/7/15 14:20 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Service
@Slf4j
public class AcquisitionManager {

    private final IAcquisitionWriteRepository acquisitionRepository;

    @Autowired
    public AcquisitionManager(IAcquisitionWriteRepository acquisitionRepository) {
        this.acquisitionRepository = acquisitionRepository;
    }

    public void checksum(BusinessAcquisitionCmd cmd) {
        if (Objects.isNull(cmd)) {
            throw new IllegalStateException();
        }
    }

    public void checksum(ConsumerAcquisitionCmd cmd) {
        if (Objects.isNull(cmd)) {
            throw new IllegalStateException();
        }
    }

    public void save(BusinessAcquisitionCmd cmd) {
        List<Acquisition<BusinessInfo>> acquisitions = AcquisitionConverter.deserialize(cmd);
        log.info(CurrentRequestHolder.getSerialNumber(), cmd.toString());
        acquisitionRepository.saveBusinessBatch(acquisitions);
    }

    public void save(ConsumerAcquisitionCmd cmd) {
        Acquisition<ConsumerInfo> acquisition = AcquisitionConverter.deserialize(cmd);
        log.info(Thread.currentThread().getStackTrace()[1] + " --- " +CurrentRequestHolder.getSerialNumber());
        acquisitionRepository.saveConsumer(acquisition);
    }

    @Async
    public void post(ConsumerAcquisitionCmd cmd) {
    }

    @Async
    public void post(BusinessAcquisitionCmd cmd) {
    }
}
