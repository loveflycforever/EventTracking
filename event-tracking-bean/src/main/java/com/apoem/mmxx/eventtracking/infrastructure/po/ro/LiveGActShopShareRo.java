package com.apoem.mmxx.eventtracking.infrastructure.po.ro;

import com.apoem.mmxx.eventtracking.infrastructure.dao.support.Affix;
import com.apoem.mmxx.eventtracking.infrastructure.dao.support.Sharding;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@ToString
@Sharding(fix = Affix.FORMAT)
@Document("et_{}_live_act_shop_share")
public class LiveGActShopShareRo {

    private Key id;
    private Value value;

    @Data
    public static class Key {
        private String gameId;
        private String storeId;
    }

    @Data
    public static class Value {
        private Long pageView;
    }
}
