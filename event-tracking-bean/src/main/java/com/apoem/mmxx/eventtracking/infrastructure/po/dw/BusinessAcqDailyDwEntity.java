package com.apoem.mmxx.eventtracking.infrastructure.po.dw;

import com.apoem.mmxx.eventtracking.infrastructure.po.ro.support.UvField;
import com.apoem.mmxx.eventtracking.infrastructure.po.support.BasicEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: BusinessAcqDailyDwEntity </p>
 * <p>Description: 每日数据 </p>
 * <p>Date: 2020/8/17 13:33 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@CompoundIndexes({
        @CompoundIndex(name = "idx", def = "{'city' : 1, 'actionDefinition' : 1}"),
})
@Document(collection = "et_dw_daily_business_acquisition")
public class BusinessAcqDailyDwEntity extends BasicEntity {

    private Date opTime;
    private String city;
    private String pageName;
    private String methodName;
    private String eventType;
    private String avenue;
    @UvField(trigger = 0)
    private String loginAccount;
    private String moduleName;
    private Long moduleId;
    private Long trackPointId;
    private String actionType;
    private String[] actionDefinition;

    /**
     * 广告ID
     */
    private String  adId;
    /**
     * 课程ID
     */
    private String courseId;

    /**
     *LiveG 活动新增字段
     */
    private String type;
    private String gameId;
    private String storeId;
    private String companyId;
    private String test;
}
