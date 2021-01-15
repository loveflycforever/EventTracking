package com.apoem.mmxx.eventtracking.infrastructure.po.ro;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: CrmClientIntentionRo </p>
 * <p>Description: 每日客户意向临时结果 </p>
 * <p>Date: 2020/9/9 9:49 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author somebody
 * @version v1.0
 */
@Data
@ToString
public class CrmDailyClientIntentionRo {

    private Key id;
    private Value value;

    @Data
    public static class Key {
        private String uniqueId;
        private String houseType;
        private String city;
    }

    @Data
    public static class Value {
        private Date date;
        private Long houseTotalPrice;
        private Long houseArea;
        private Long houseAveragePrice;
        private Long houseBedroom;
        private Integer houseCount;
    }

}
