package com.apoem.mmxx.eventtracking.infrastructure.po.dm;

import com.apoem.mmxx.eventtracking.infrastructure.po.support.BasicEntity;
import lombok.*;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: CustomerTrailEntity </p>
 * <p>Description: 180天，用户轨迹 </p>
 * <p>Date: 2020/8/5 15:59 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection="et_180_customer_trail")
@CompoundIndexes({
        @CompoundIndex(name = "idx1", def = "{'city' : 1, 'agent_id' : 1, 'customer_id' : 1, 'id_key' : 1, 'action_type' : 1}"),
        @CompoundIndex(name = "idx3", def = "{'city' : 1, 'house_id' : 1, 'customer_id' : 1, 'id_key' : 1, 'action_definition' : 1}")
})
public class CustomerTrailEntity extends BasicEntity {

    /**
     * 具体时间
     */
    private Integer dateDay;

    /**
     * 客源、经纪人、城市
     */
    private String city;
    private String customerId;
    private String agentId;
    private Date opTime;

    // 龌龊
    private Date originOpTime;
    private Long odsId;

    /**
     * 浏览、分享、收藏
     */
    private String actionType;

    /**
     * 原文，客户端上报内容要具备自身能区分数据类型的能力
     */
    private String content;

    /**
     * 浏览此时，仅浏览时计数
     */
    private Long viewTimes;

    /**
     * 持续时间
     */
    private Long duration;

    /**
     * 操作定义，用于进一步抽取数据用，比如客户访问房源统计
     */
    private String actionDefinition;

    /**
     * 如果为 VIS_HUS 则必填 房源
     */
    private String houseId;
    private String houseType;

    private String pageName;
    private String methodName;
    private String eventType;

    private Long idKey;
}
