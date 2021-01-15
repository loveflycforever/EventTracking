package com.apoem.mmxx.eventtracking.infrastructure.po.ro;

import com.apoem.mmxx.eventtracking.infrastructure.dao.support.Affix;
import com.apoem.mmxx.eventtracking.infrastructure.dao.support.Sharding;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: MrAgentCommunityRankingDayRo </p>
 * <p>Description: 经纪人社区排名MR临时结果 </p>
 * <p>Date: 2020/8/21 9:44 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Data
@ToString
@Sharding(fix = Affix.FORMAT)
@Document("et_{}_agent_community_ranking_day_result")
public class MrAgentCommunityRankingDayRo {

  private Key id;
  private Value value;

  @Data
  public static class Key {
    private String agentId;
    private String communityId;
    private String houseType;
    private String city;
  }

  @Data
  public static class Value {
    private Long pageView;
    private Long uniqueVisitor;
    private Long collected;
    private Long reposted;

    private String communityName;
    private String plateName;
    private String plateId;
  }
}