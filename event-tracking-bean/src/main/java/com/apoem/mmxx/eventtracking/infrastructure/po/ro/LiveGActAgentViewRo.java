package com.apoem.mmxx.eventtracking.infrastructure.po.ro;

import com.apoem.mmxx.eventtracking.infrastructure.dao.support.Affix;
import com.apoem.mmxx.eventtracking.infrastructure.dao.support.Sharding;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@ToString
@Sharding(fix = Affix.FORMAT)
@Document("et_{}_live_act_agent_view")
public class LiveGActAgentViewRo {

    private Key id;
    private Value value;

    @Data
    public static class Key {
        private String gameId;
        private String agentId;
    }

    @Data
    public static class Value {
        private Long pageView;
    }
}
