package com.apoem.mmxx.eventtracking.infrastructure.po.ro;

import lombok.Data;
import lombok.ToString;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: CrmAvgPriceDistributionRo </p>
 * <p>Description: 均价浏览统计临时结果 </p>
 * <p>Date: 2020/9/14 10:03 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author somebody
 * @version v1.0
 */
@Data
@ToString
public class CrmAvgPriceDistributionRo {

    private Key id;
    private Value value;

    @Data
    public static class Key {
        private String uniqueId;
        private String houseType;
        private String city;
        private String avgPriceRange;
    }

    @Data
    public static class Value {
        private Integer viewCount;
    }

}
