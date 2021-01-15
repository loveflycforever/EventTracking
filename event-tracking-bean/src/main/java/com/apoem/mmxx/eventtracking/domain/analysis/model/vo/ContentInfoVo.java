package com.apoem.mmxx.eventtracking.domain.analysis.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: ResultOb </p>
 * <p>Description:  </p>
 * <p>Date: 2020/9/15 20:07 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ContentInfoVo {

    private Integer dayOfWeek;
    private Integer hourOfDay;

    private BigDecimal resultHouseArea;
    private BigDecimal resultHouseAveragePrice;
    private BigDecimal resultHouseTotalPrice;
    private BigDecimal resultHouseAreaLower;
    private BigDecimal resultHouseAreaUpper;
    private Integer resultHouseBedroom;

    private String resultAreaRange;
    private Integer resultAreaRangeOrder;
    private String resultTotalPriceRange;
    private Integer resultTotalPriceRangeOrder;
    private String resultAvgPriceRange;
    private Integer resultAvgPriceRangeOrder;
    private String resultLayoutRange;
    private Integer resultLayoutRangeOrder;
}
