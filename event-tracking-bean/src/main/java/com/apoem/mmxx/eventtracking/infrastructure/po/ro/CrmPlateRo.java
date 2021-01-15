package com.apoem.mmxx.eventtracking.infrastructure.po.ro;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: CrmPlateRo </p>
 * <p>Description: 板块意向临时结果 </p>
 * <p>Date: 2020/9/11 13:39 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author somebody
 * @version v1.0
 */
@Data
@ToString
public class CrmPlateRo {

    private Key id;
    private Value value;

    @Data
    public static class Key {
        private String uniqueId;
        private String houseType;
        private String city;
        private String plateId;
        private String plateName;
    }

    @Data
    public static class Value {
        private Date updateDate;
        private Integer viewCount;
    }

}
