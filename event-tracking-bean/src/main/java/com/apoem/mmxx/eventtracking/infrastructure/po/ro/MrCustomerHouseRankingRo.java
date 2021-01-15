package com.apoem.mmxx.eventtracking.infrastructure.po.ro;

import com.apoem.mmxx.eventtracking.infrastructure.dao.support.Affix;
import com.apoem.mmxx.eventtracking.infrastructure.dao.support.Sharding;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: MrCustomerHouseRankingRo </p>
 * <p>Description: 客户房源排名MR临时结果 </p>
 * <p>Date: 2020/8/21 9:44 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Data
@ToString
@Sharding(fix = Affix.FORMAT)
@Document("et_{}_customer_house_ranking_result")
public class MrCustomerHouseRankingRo {

  private Key id;
  private Value value;

  @Data
  public static class Key {
    private String houseType;
    private String houseId;
    private String customerId;
    private String city;
  }

  @Data
  public static class Value {
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

    private Date lastOpTime;

    private Long pageView;
    private Long uniqueVisitor;
    private Long collected;
    private Long reposted;
  }
}