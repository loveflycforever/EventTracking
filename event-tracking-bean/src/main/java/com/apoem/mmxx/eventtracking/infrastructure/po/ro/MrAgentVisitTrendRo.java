package com.apoem.mmxx.eventtracking.infrastructure.po.ro;

import com.apoem.mmxx.eventtracking.infrastructure.dao.support.Affix;
import com.apoem.mmxx.eventtracking.infrastructure.dao.support.Sharding;
import com.apoem.mmxx.eventtracking.infrastructure.po.ro.support.MrSignedKey;
import com.apoem.mmxx.eventtracking.infrastructure.po.ro.support.MrStandardValue;
import com.apoem.mmxx.eventtracking.infrastructure.po.ro.support.UvFieldTrigger;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: MrAgentVisitTrendRo </p>
 * <p>Description: 经纪人访问趋势MR临时结果 </p>
 * <p>Date: 2020/8/21 9:44 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Data
@ToString
@Sharding(fix = Affix.FORMAT)
@Document("et_{}_agent_visit_trend_result")
@UvFieldTrigger(value = 0)
public class MrAgentVisitTrendRo {

  private Key id;
  private MrStandardValue value;

  @EqualsAndHashCode
  @Data
  @MrSignedKey
  public static class Key {
    private String agentId;
    private String houseType;
    private String city;
  }
}