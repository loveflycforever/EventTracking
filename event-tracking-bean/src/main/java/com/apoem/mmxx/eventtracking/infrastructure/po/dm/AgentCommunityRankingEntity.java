package com.apoem.mmxx.eventtracking.infrastructure.po.dm;

import com.apoem.mmxx.eventtracking.infrastructure.po.support.BasicEntity;
import lombok.*;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: AgentCommunityRankingEntity </p>
 * <p>Description: 效果分析-经纪人小区 </p>
 * <p>Date: 2020/8/4 15:28 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
@Document(collection="et_dm_agent_community_ranking")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@CompoundIndexes({
        @CompoundIndex(name = "idx", def = "{'city' : 1, 'agent_id': 1, 'house_type' : 1, 'date_day' : 1}")
})
public class AgentCommunityRankingEntity extends BasicEntity {

    /**
     * 日期，yyyyMMdd
     * 数据类型，昨日、七日，三十日
     */
    private Integer dateDay;
    private String periodType;

    // 维度定义

    private String city;
    private String agentId;
    private String communityId;
    private String houseType;

    // 维度数据

    private String communityName;
    private String plateId;
    private String plateName;

    // 统计数据

    /**
     * 访问次数、访问用户数、收藏次数、转发次数
     */
    private Long pageView;
    private Long uniqueVisitor;
    private Long collected;
    private Long reposted;

    /**
     * 总访问次数、总访问用户数、总收藏次数、总转发次数
     */
    private Long totalPageView;
    private Long totalUniqueVisitor;
    private Long totalCollected;
    private Long totalReposted;

    public AgentCommunityRankingEntity init() {
        this.pageView = 0L;
        this.uniqueVisitor = 0L;
        this.collected = 0L;
        this.reposted = 0L;
        this.totalPageView = 0L;
        this.totalUniqueVisitor = 0L;
        this.totalCollected = 0L;
        this.totalReposted = 0L;
        return this;
    }
}
