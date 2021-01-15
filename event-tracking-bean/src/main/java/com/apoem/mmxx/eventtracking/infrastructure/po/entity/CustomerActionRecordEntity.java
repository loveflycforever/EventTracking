package com.apoem.mmxx.eventtracking.infrastructure.po.entity;

import com.apoem.mmxx.eventtracking.infrastructure.po.support.BasicEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: CustomerActionRecordEntity </p>
 * <p>Description: 客户访问记录实体 </p>
 * <p>Date: 2020/7/29 9:14 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@EqualsAndHashCode(callSuper = true)
@Document(collection="et_customer_action_record")
@Data
@CompoundIndexes({
        @CompoundIndex(name = "idx", def = "{'date_day' : 1, 'action_definition' : 1, 'city' : 1, 'customer_id' : 1, 'house_id' : 1, 'house_type' : 1}"),
})
public class CustomerActionRecordEntity extends BasicEntity {

    private Integer dateDay;
    private String actionDefinition;
    private String customerId;
    private String city;
    private String houseId;
    private String houseType;
    private Long prevDayMaxNumber;
    private Long number;
}
