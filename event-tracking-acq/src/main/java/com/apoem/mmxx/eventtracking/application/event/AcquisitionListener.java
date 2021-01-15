package com.apoem.mmxx.eventtracking.application.event;

import com.apoem.mmxx.eventtracking.interfaces.assembler.BusinessAcquisitionCmd;
import com.apoem.mmxx.eventtracking.interfaces.assembler.ConsumerAcquisitionCmd;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: AcquisitionListener </p>
 * <p>Description: 应用层服务：实时任务处理监听器，空实现 </p>
 * <p>Date: 2020/7/14 12:53 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
public interface AcquisitionListener {

    /**
     * 处理 App 端请求
     * @param cmd 请求对象
     * @return 处理结果状态
     */
    Object accept(ConsumerAcquisitionCmd cmd);

    /**
     * 处理 App 端请求
     * @param cmd 请求对象
     * @return 处理结果状态
     */
    Object accept(BusinessAcquisitionCmd cmd);
}
