package com.apoem.mmxx.eventtracking.infrastructure.po.ro;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: CrmCommunityRo </p>
 * <p>Description: 小区意向临时结果 </p>
 * <p>Date: 2020/9/11 13:39 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author somebody
 * @version v1.0
 */
@Data
@ToString
public class CrmCommunityRo {

    private Key id;
    private Value value;

    @Data
    public static class Key {
        private String uniqueId;
        private String houseType;
        private String city;
        private String communityId;
        private String communityName;
    }

    @Data
    public static class Value {
        private Date updateDate;
        private Integer viewCount;
    }

}
