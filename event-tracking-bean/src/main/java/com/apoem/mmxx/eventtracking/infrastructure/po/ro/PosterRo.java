package com.apoem.mmxx.eventtracking.infrastructure.po.ro;

import com.apoem.mmxx.eventtracking.infrastructure.dao.support.Affix;
import com.apoem.mmxx.eventtracking.infrastructure.dao.support.Sharding;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: PosterRo </p>
 * <p>Description: H5海报PV、UV统计中间结果 </p>
 * <p>Date: 2020/11/17 14:17 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author somebody
 * @version v1.0
 */
@Data
@ToString
@Sharding(fix = Affix.FORMAT)
@Document("et_{}_poster")
public class PosterRo {

    private Key id;
    private Value value;

    @Data
    public static class Key {
        private String posterId;
        private String agentId;
        private String city;
    }

    @Data
    public static class Value {
        private Long pageView;
        private Long uniqueVisitor;
    }

}
