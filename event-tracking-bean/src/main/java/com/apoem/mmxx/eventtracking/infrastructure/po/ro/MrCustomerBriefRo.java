package com.apoem.mmxx.eventtracking.infrastructure.po.ro;

import com.apoem.mmxx.eventtracking.infrastructure.dao.support.Affix;
import com.apoem.mmxx.eventtracking.infrastructure.dao.support.Sharding;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: MrCustomerBriefRo </p>
 * <p>Description: 客源总览MR临时结果 </p>
 * <p>Date: 2020/8/21 9:44 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Data
@ToString
@Sharding(fix = Affix.FORMAT)
@Document("et_{}_customer_brief_result")
public class MrCustomerBriefRo {

  private Key id;
  private Value value;

  @Data
  public static class Key {
    private String customerId;
    private String houseType;
    private String city;
  }

  @Data
  public static class Value {
    private List<String> communityIdList;
    private Long housePriceTotal;
    private Long housePriceAmount;
    private Long houseAreaTotal;
    private Long houseAreaAmount;
    private List<String> houseLayoutList;
  }
}