package com.apoem.mmxx.eventtracking.infrastructure.po.ro;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: CrmPlateIntentionRo </p>
 * <p>Description: 板块意向临时结果 </p>
 * <p>Date: 2020/9/17 14:25 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author somebody
 * @version v1.0
 */
@Data
@ToString
public class CrmPlateIntentionRo {

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
        private Integer viewCount;
        private String plateName;
        private Date updateDate;
    }

}
