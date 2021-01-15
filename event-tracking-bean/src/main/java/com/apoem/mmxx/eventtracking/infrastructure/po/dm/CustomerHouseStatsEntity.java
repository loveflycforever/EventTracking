package com.apoem.mmxx.eventtracking.infrastructure.po.dm;

import com.apoem.mmxx.eventtracking.infrastructure.po.support.BasicEntity;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: AgentStatsEntity </p>
 * <p>Description: 运营后台-经纪人 </p>
 * <p>Date: 2020/8/26 13:32 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
@Document(collection = "et_dm_customer_house_stats")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerHouseStatsEntity extends BasicEntity {

    /**
     * 日期，yyyyMMdd
     * 数据类型，昨日、七日，三十日
     */
    private Integer dateDay;
    private String periodType;

    // 维度定义

    private String city;
    private String customerId;
    private String houseType;


    // 统计数据

    /**
     * 访问次数、访问用户数
     */
    private Long pageViewHouse;
    private Long uniqueVisitorHouse;
    private Long collectedUniqueHouse;
    private Long repostedHouse;

    public CustomerHouseStatsEntity init() {
        this.pageViewHouse = 0L;
        this.uniqueVisitorHouse = 0L;
        this.collectedUniqueHouse = 0L;
        this.repostedHouse = 0L;
        return this;
    }
}
