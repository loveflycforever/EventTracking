package com.apoem.mmxx.eventtracking.infrastructure.po.ro;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: CrmDailyClientVisitRo </p>
 * <p>Description: 每日客户浏览收藏经纪人联系统计临时结果 </p>
 * <p>Date: 2020/9/24 16:05 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author somebody
 * @version v1.0
 */
@Data
@ToString
public class CrmDailyClientVisitRo {

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
        private Long viewCount;
        private Long collectCount;
        private Long telCount;
        private Date date;
    }

}
