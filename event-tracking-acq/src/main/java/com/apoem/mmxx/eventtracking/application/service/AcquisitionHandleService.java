package com.apoem.mmxx.eventtracking.application.service;

import com.apoem.mmxx.eventtracking.interfaces.assembler.*;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: AcquisitionHandleService </p>
 * <p>Description: 应用层服务：埋点业务上报数据处理服务 </p>
 * <p>Date: 2020/7/14 12:53 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
public interface AcquisitionHandleService {

    /**
     * 处理 B 端请求
     * @param cmd 请求对象
     * @return 处理结果状态
     */
    Object processBusinessEndpoint(BusinessAcquisitionCmd cmd);

    /**
     * 处理 C 端请求
     * @param cmd 请求对象
     * @return 处理结果状态
     */
    Object processConsumerEndpoint(ConsumerAcquisitionCmd cmd);

    Object processWebPageEndpoint(ConsumerAcquisitionCmd cmd);
}
