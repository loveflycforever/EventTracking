package com.apoem.mmxx.eventtracking.domain.acquisition.service;

import com.apoem.mmxx.eventtracking.application.service.AcquisitionHandleService;
import com.apoem.mmxx.eventtracking.domain.acquisition.service.engine.AcquisitionManager;
import com.apoem.mmxx.eventtracking.infrastructure.common.holder.CurrentRequestHolder;
import com.apoem.mmxx.eventtracking.interfaces.assembler.BusinessAcquisitionCmd;
import com.apoem.mmxx.eventtracking.interfaces.assembler.ConsumerAcquisitionCmd;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: AcquisitionHandleServiceImpl </p>
 * <p>Description: 应用层实现：上报数据处理服务实现 </p>
 * <p>Date: 2020/7/14 13:28 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Service
@Slf4j
public class AcquisitionHandleServiceImpl implements AcquisitionHandleService {

    @Resource
    private AcquisitionManager acquisitionHandler;

    @Override
    public Object processBusinessEndpoint(BusinessAcquisitionCmd cmd) {

        log.info("{} --- {}, cmd content {}", Thread.currentThread().getStackTrace()[1], CurrentRequestHolder.getSerialNumber(), cmd.toString());
        // 校验
        acquisitionHandler.checksum(cmd);
        // 存储
        acquisitionHandler.save(cmd);
        // 转换有效数据
        acquisitionHandler.post(cmd);
        return true;
    }

    @Override
    public Object processConsumerEndpoint(ConsumerAcquisitionCmd cmd) {
        log.info("{} --- {}, cmd content {}", Thread.currentThread().getStackTrace()[1], CurrentRequestHolder.getSerialNumber(), cmd.toString());
        // 校验
        acquisitionHandler.checksum(cmd);
        // 存储
        acquisitionHandler.save(cmd);
        // 转换有效数据
        acquisitionHandler.post(cmd);
        return true;
    }

    @Override
    public Object processWebPageEndpoint(ConsumerAcquisitionCmd cmd) {
        log.info("{} --- {}, cmd content {}", Thread.currentThread().getStackTrace()[1], CurrentRequestHolder.getSerialNumber(), cmd.toString());
        // 校验
        acquisitionHandler.checksum(cmd);
        // 存储
        acquisitionHandler.save(cmd);
        // 转换有效数据
        acquisitionHandler.post(cmd);
        return true;
    }
}
