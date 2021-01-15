package com.apoem.mmxx.eventtracking.infrastructure.po.ro.support;

import com.apoem.mmxx.eventtracking.infrastructure.dao.support.Affix;
import com.apoem.mmxx.eventtracking.infrastructure.dao.support.Sharding;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: MrStoreStatsRo </p>
 * <p>Description: 门店MR临时结果 </p>
 * <p>Date: 2020/8/21 9:44 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Data
@ToString
@Sharding(fix = Affix.FORMAT)
@Document("et_{}_aa_result")
@UvFieldTrigger(value = 0)
public class MrExampleRo {

  private Key id;
  private Value value;

  @EqualsAndHashCode
  @Data
  @MrSignedKey
  public static class Key {
    private String storeId;
    private String houseType;
    private String city;
  }

  @EqualsAndHashCode(callSuper = true)
  @Data
  @MrSignedValue
  public static class Value extends MrStandardValue {
    @MrSignedValueFieldKeepLast
    private String storeId;
    @MrSignedValueFieldKeepLast
    private String houseType;
    @MrSignedValueFieldKeepLast
    private String city;
  }
}