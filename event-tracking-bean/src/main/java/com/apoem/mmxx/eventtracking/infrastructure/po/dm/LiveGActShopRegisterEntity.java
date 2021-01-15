package com.apoem.mmxx.eventtracking.infrastructure.po.dm;

import com.apoem.mmxx.eventtracking.infrastructure.po.support.BasicEntity;
import lombok.*;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
@Document(collection="et_dm_live_act_shop_register")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@CompoundIndexes({
        @CompoundIndex(name = "idx", def = "{'game_id': 1, 'store_id': 1, 'date_day' : 1}")
})
public class LiveGActShopRegisterEntity extends BasicEntity {

    /**
     * 日期，yyyyMMdd
     * 数据类型，昨日、七日，三十日
     */
    private Integer dateDay;
    private String periodType;

    // 维度定义

    /**
     * 活动、门店
     */
    private String gameId;
    private String storeId;

    // 统计数据

    private Long uniqueVisitor;
}
