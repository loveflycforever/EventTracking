package com.apoem.mmxx.eventtracking.infrastructure.po.dm;

import com.apoem.mmxx.eventtracking.infrastructure.po.support.BasicEntity;
import lombok.*;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: ModuleStatsEntity </p>
 * <p>Description:  </p>
 * <p>Date: 2020/9/7 11:32 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
@Document(collection="et_dm_info_template")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@CompoundIndexes({
        @CompoundIndex(name = "idx", def = "{'city' : 1, 'information_id': 1, 'agent_id' : 1, 'date_day' : 1}")
})
public class InfoTemplateEntity extends BasicEntity {

    /**
     * 日期，yyyyMMdd
     * 数据类型，昨日、七日，三十日
     */
    private Integer dateDay;
    private String periodType;

    // 维度定义

    /**
     * 页面、事件类型
     */
    private String informationId;
    private String agentId;
    private String city;

    // 统计数据

    private Long pageView;
    private Long uniqueVisitor;

    public InfoTemplateEntity init() {
        this.pageView = 0L;
        this.uniqueVisitor = 0L;
        return this;
    }
}
