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
 * <p>Name: MrInformationStatsRo </p>
 * <p>Description: 资讯MR临时结果 </p>
 * <p>Date: 2020/8/21 9:44 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Data
@ToString
@Sharding(fix = Affix.FORMAT)
@Document("et_{}_information_stats_result")
@UvFieldTrigger(value = 0)
public class MrInformationStatsRo {

  private Key id;
  private MrStandardValue value;

  @EqualsAndHashCode
  @Data
  @MrSignedKey
  public static class Key {
    private String informationId;
    private String city;
  }
}