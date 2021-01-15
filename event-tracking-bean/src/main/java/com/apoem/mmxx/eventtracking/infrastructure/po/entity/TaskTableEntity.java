package com.apoem.mmxx.eventtracking.infrastructure.po.entity;

import com.apoem.mmxx.eventtracking.infrastructure.po.support.BasicEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: TaskTableEntity </p>
 * <p>Description: 任务状态表 </p>
 * <p>Date: 2020/9/9 15:14 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
@Document(collection="et_task_table")
@CompoundIndexes({
        @CompoundIndex(name = "idx", def = "{'date_day' : 1, 'task_name' : 1}"),
})
public class TaskTableEntity extends BasicEntity {

    /**
     * 时间
     */
    private Integer dateDay;

    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 任务名称
     */
    private String taskDesc;

    /**
     * READY
     * RUNNING
     * FINISH
     */
    private String status;

    private Integer order;
}
