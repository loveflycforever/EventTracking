package com.apoem.mmxx.eventtracking.infrastructure.po.ro;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: CrmDailyCommunityRo </p>
 * <p>Description: 客户板块意向MR临时结果 </p>
 * <p>Date: 2020/9/10 14:57 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author somebody
 * @version v1.0
 */
@Data
@ToString
public class CrmDailyPlateCommunityRo {

    private Key id;
    private Value value;

    @Data
    public static class Key {
        private String uniqueId;
        private String houseType;
        private String city;
        private String plateId;
        private String plateName;
        private String communityId;
        private String communityName;
    }

    @Data
    public static class Value {
        private Date date;
        private Integer viewCount;
    }

}
