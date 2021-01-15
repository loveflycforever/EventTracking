package com.apoem.mmxx.eventtracking.infrastructure.po.entity;

import com.apoem.mmxx.eventtracking.infrastructure.po.support.BasicEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: BizLogRepository </p>
 * <p>Description:  </p>
 * <p>Date: 2020/9/9 13:10 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
@Document(collection="et_biz_log")
public class BizLogEntity extends BasicEntity {

    private Integer dateDay;
    private String tableName;
    private String keywords;
    private String desc;
    private String pageName;
    private String methodName;
    private String eventType;
    private String avenue;
    private String endpoint;
    private String source;
    private String target;
    private String content;
}
