package com.apoem.mmxx.eventtracking.infrastructure.po.ro;

import com.apoem.mmxx.eventtracking.infrastructure.dao.support.Affix;
import com.apoem.mmxx.eventtracking.infrastructure.dao.support.Sharding;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: MrCustomerRangeGlanceRo </p>
 * <p>Description: 客户点击MR临时结果 </p>
 * <p>Date: 2020/8/21 9:44 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Data
@ToString
@Sharding(fix = Affix.FORMAT)
@Document("et_{}_customer_range_glance_result")
public class MrCustomerRangeGlanceRo {

  private Key id;
  private Value value;

  @Data
  public static class Key {
    private String city;
    private String customerId;
    private String houseType;
    private String rangeType;
  }

  @Data
  public static class Value {
    private String rangeLabel;
    private Long amount;
  }
}