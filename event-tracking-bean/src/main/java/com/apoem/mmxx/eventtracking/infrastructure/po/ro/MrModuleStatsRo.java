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
 * <p>Name: MrModuleStatsRo </p>
 * <p>Description:  </p>
 * <p>Date: 2020/9/7 11:33 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Data
@ToString
@Sharding(fix = Affix.FORMAT)
@Document("et_{}_module_stats_result")
@UvFieldTrigger(value = 0)
public class MrModuleStatsRo {

    private Key id;
    private MrStandardValue value;

    @EqualsAndHashCode
    @Data
    @MrSignedKey
    public static class Key {
        private String moduleId;
        private String moduleName;
        private String city;
    }
}
