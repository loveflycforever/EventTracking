package com.apoem.mmxx.eventtracking.infrastructure.po.ro;

import com.apoem.mmxx.eventtracking.infrastructure.dao.support.Affix;
import com.apoem.mmxx.eventtracking.infrastructure.dao.support.Sharding;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: LiveGActRo </p>
 * <p>Description: liveG活动维度账号申请mr中间结果 </p>
 * <p>Date: 2020/11/17 8:42 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author somebody
 * @version v1.0
 */
@Data
@ToString
@Sharding(fix = Affix.FORMAT)
@Document("et_{}_live_act_register")
public class LiveGActRegisterRo {

    private Key id;
    private Value value;

    @Data
    public static class Key {
        private String gameId;
    }

    @Data
    public static class Value {
        private Long uniqueVisitor;
    }
}
