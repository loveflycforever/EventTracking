package com.apoem.mmxx.eventtracking.infrastructure.po.dm;

import com.apoem.mmxx.eventtracking.infrastructure.po.support.DmEntity;
import lombok.*;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: AgentVisitTrendEntity </p>
 * <p>Description: 经纪人图表Node，单独维度 </p>
 * <p>Date: 2020/8/5 9:15 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@CompoundIndexes({
        @CompoundIndex(name = "unq_date_day_agent_id", def = "{'city' : 1, 'agent_id': 1, 'house_type': 1, 'date_day' : 1}", unique = true)
})
@ToString(callSuper = true)
@Document("et_dm_agent_visit_trend")
@AllArgsConstructor
@NoArgsConstructor
public class AgentVisitTrendEntity extends DmEntity {

    // 维度定义

    /**
     * 经纪人；城市；新房、二手房、租房
     */
    private String agentId;
    private String city;
    private String houseType;

    // 维度数据

    /**
     * 初始化数据标志（0 是，1 非）
     */
    private Integer initial;

    // 统计数据

    /**
     * 访问次数、访问用户数
     * 总访问次数、总访问用户数
     * 访问次数上升率、访问用户数上升率
     */
    private Long totalPageView;
    private Long totalUniqueVisitor;
    private BigDecimal pageViewRiseRate;
    private BigDecimal uniqueVisitorRiseRate;

    /**
     * 上期访问次数（连续）、上期访问用户数（连续）
     * 上期总访问次数（连续）、上期总访问用户数（连续）
     * 上期访问次数上升率（连续）、上期访问用户数上升率（连续）
     */
    private Long prevPageView;
    private Long prevUniqueVisitor;
    private Long prevTotalPageView;
    private Long prevTotalUniqueVisitor;
    private BigDecimal prevPageViewRiseRate;
    private BigDecimal prevUniqueVisitorRiseRate;
}
