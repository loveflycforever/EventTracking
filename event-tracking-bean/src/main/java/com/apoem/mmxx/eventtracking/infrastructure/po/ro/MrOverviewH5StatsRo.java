package com.apoem.mmxx.eventtracking.infrastructure.po.ro;

import com.apoem.mmxx.eventtracking.infrastructure.dao.support.Affix;
import com.apoem.mmxx.eventtracking.infrastructure.dao.support.Sharding;
import com.apoem.mmxx.eventtracking.infrastructure.po.ro.support.CValue;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: MrOverviewStatsRo </p>
 * <p>Description: 运营总览MR临时结果 </p>
 * <p>Date: 2020/8/21 9:44 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Data
@ToString
@Sharding(fix = Affix.FORMAT)
@Document("et_{}_overview_h5_stats_result")
public class MrOverviewH5StatsRo {

  private Key id;
  private CValue value;

  @Data
  public static class Key {
    private String city;
  }
}