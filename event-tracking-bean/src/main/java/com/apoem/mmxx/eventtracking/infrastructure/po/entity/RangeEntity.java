package com.apoem.mmxx.eventtracking.infrastructure.po.entity;

import com.apoem.mmxx.eventtracking.infrastructure.po.support.BasicEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: RangeEntity </p>
 * <p>Description:  </p>
 * <p>Date: 2020/9/17 10:36 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
@Document(collection="et_comm_range")
public class RangeEntity extends BasicEntity {
    private Integer dateDay;
    private String city;
    private String cityName;
    private String rangeTypeName;
    private Integer lower;
    private Integer upper;
    private String type;
    private String label;
    private Integer orderNumber;


    public RangeEntity init() {
        this.label = StringUtils.EMPTY;
        this.orderNumber = 0;
        return this;
    }
}
