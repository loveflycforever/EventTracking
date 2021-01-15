package com.apoem.mmxx.eventtracking.infrastructure.po.ro;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: CrmDailyBlockRo </p>
 * <p>Description: 每日客户物业意向临时结果 </p>
 * <p>Date: 2020/9/18 14:23 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author somebody
 * @version v1.0
 */
@Data
@ToString
public class CrmDailyBlockRo {

    private Key id;
    private Value value;

    @Data
    public static class Key {
        private String uniqueId;
        private String houseType;
        private String city;
        private String blockId;
        private String blockName;
    }

    @Data
    public static class Value {
        private Date date;
        private Integer viewCount;
    }

}
