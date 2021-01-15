package com.apoem.mmxx.eventtracking.infrastructure.po.dm;

import com.apoem.mmxx.eventtracking.infrastructure.po.support.BasicEntity;
import lombok.*;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
@Document(collection="et_dm_live_act_normal_share")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@CompoundIndexes({
        @CompoundIndex(name = "idx", def = "{'game_id': 1, 'test' : 1, 'occurred': 1, 'date_day' : 1}")
})
public class LiveGActNormalShareEntity extends BasicEntity {

    /**
     * 日期，yyyyMMdd
     * 数据类型，昨日、七日，三十日
     */
    private Integer dateDay;
    private String periodType;

    // 维度定义

    /**
     * 活动
     */
    private String gameId;
    private String test;
    private String occurred;

    // 统计数据

    private Long pageView;
}
