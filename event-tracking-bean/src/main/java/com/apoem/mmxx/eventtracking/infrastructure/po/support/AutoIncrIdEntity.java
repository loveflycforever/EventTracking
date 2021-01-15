package com.apoem.mmxx.eventtracking.infrastructure.po.support;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.function.Supplier;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: AutoIncrEntity </p>
 * <p>Description: 自增实体类 </p>
 * <p>Date: 2020/7/16 12:13 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
public class AutoIncrIdEntity extends BasicEntity {

    /**
     * 默认主键
     */
    @Id
    private Long id;

    public void completeAutoIncrField(Long id) {
        this.id = id;
        this.setCreateTime(new Date());
    }

    public void completeAutoIncrFieldIf(boolean isTrue, Supplier<Long> supplier) {
        if (isTrue) {
            completeAutoIncrField(supplier.get());
        }
    }
}
