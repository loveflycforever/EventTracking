package com.apoem.mmxx.eventtracking.infrastructure.po.ro.support;

import lombok.Data;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: MrStoreStatsRo </p>
 * <p>Description: 门店MR临时结果 </p>
 * <p>Date: 2020/8/21 9:44 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Data
@MrSignedValue
public class MrStandardValue {

    @Pv
    private Long pageView;
    @Uv
    private Long uniqueVisitor;
}