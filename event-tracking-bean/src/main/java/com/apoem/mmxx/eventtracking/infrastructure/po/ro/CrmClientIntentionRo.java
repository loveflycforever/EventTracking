package com.apoem.mmxx.eventtracking.infrastructure.po.ro;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: CrmClientIntentionRo </p>
 * <p>Description: 客户意向临时结果 </p>
 * <p>Date: 2020/9/9 16:07 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author somebody
 * @version v1.0
 */
@Data
@ToString
public class CrmClientIntentionRo {

    private Key id;
    private Value value;

    @Data
    public static class Key {
        private String city;
        private String uniqueId;
        private String houseType;
    }

    @Data
    public static class Value {
        private Date updateDate;
        private Long houseArea;
        private Long houseTotalPrice;
        private Long houseAveragePrice;
        private Long houseBedroom;
        private Integer houseCount;
    }

}
