package com.apoem.mmxx.eventtracking.infrastructure.po.dm;

import com.apoem.mmxx.eventtracking.infrastructure.po.support.DmEntity;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: ModuleStatsEntity </p>
 * <p>Description:  </p>
 * <p>Date: 2020/9/7 11:32 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
@Document(collection="et_dm_course")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourseEntity extends DmEntity {

    // 维度定义

    private String courseId;
    private String city;
}
