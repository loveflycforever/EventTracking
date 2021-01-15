package com.apoem.mmxx.eventtracking.infrastructure.po.ro;

import com.apoem.mmxx.eventtracking.infrastructure.dao.support.Affix;
import com.apoem.mmxx.eventtracking.infrastructure.dao.support.Sharding;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: MrAgentHouseRankingRo </p>
 * <p>Description: 经纪人房源排名MR临时结果 </p>
 * <p>Date: 2020/8/21 9:44 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Data
@ToString
@Sharding(fix = Affix.FORMAT)
@Document("et_{}_house_ranking_result")
public class MrHouseRankingRo {

  private Key id;
  private Value value;

  @Data
  public static class Key {
    private String houseId;
    // 因为相同的houseId属于不同houseType
    private String houseType;
    private String city;
  }

  @Data
  public static class Value {
    private Long pageView;
    private Long uniqueVisitor;
    private Long collected;
    private Long reposted;
    private Long imConnected;
    private Long phoneConnected;

    private String houseName;
    private Long houseArea;
    private Long houseTotalPrice;
    private Long houseAveragePrice;
    private Long houseAreaLower;
    private Long houseAreaUpper;
    private Integer houseLivingRoom;
    private Integer houseBedroom;
    private Integer houseBathroom;
    private String communityId;
    private String communityName;
    private String plateId;
    private String plateName;
    private String agentId;
    private String storeId;
  }
}