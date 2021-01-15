package com.apoem.mmxx.eventtracking.domain.acquisition.model.aggregates;

import com.apoem.mmxx.eventtracking.infrastructure.enums.EndpointEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: Acquisition </p>
 * <p>Description: 聚合根：上报数据</p>
 * <p>Date: 2020/7/15 13:19 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Acquisition<T> {

    private EndpointEnum endpoint;

    /**
     * 客户/商户信息
     */
    private T customInfo;

    /**
     * 额外内容信息
     */
    private String contentInfo;
}
