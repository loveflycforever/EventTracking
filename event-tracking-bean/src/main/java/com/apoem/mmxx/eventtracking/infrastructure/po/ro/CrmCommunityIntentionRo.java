package com.apoem.mmxx.eventtracking.infrastructure.po.ro;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: CrmCommunityIntentionRo </p>
 * <p>Description: 小区意向临时结果 </p>
 * <p>Date: 2020/9/17 13:49 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author somebody
 * @version v1.0
 */
@Data
@ToString
public class CrmCommunityIntentionRo {

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
        private String communityName;
        private Date updateDate;
    }

}
