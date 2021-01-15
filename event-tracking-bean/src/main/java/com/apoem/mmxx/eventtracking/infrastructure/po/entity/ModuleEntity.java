package com.apoem.mmxx.eventtracking.infrastructure.po.entity;

import com.apoem.mmxx.eventtracking.infrastructure.po.support.AutoIncrIdEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: ModuleEntity </p>
 * <p>Description: 模块分类实体 </p>
 * <p>Date: 2020/7/29 9:14 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Document(collection="et_module")
public class ModuleEntity extends AutoIncrIdEntity {

    /**
     * 名称
     */
    @Indexed
    private String name;

    /**
     * 中文名称
     */
    private String nameCn;

    /**
     * 排序
     */
    private Integer orderNumber;

    /**
     * 父节点ID
     */
    private Long parentId;
}
