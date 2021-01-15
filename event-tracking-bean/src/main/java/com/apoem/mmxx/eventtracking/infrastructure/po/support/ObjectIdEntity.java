package com.apoem.mmxx.eventtracking.infrastructure.po.support;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.annotation.Id;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: ObjectIdEntity </p>
 * <p>Description:  </p>
 * <p>Date: 2020/8/12 18:29 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
public class ObjectIdEntity extends BasicEntity {

    /**
     * 默认主键
     */
    @Id
    private String id;

}
