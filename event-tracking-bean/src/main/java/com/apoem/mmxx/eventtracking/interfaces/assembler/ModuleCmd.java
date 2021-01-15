package com.apoem.mmxx.eventtracking.interfaces.assembler;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: ModuleCmd </p>
 * <p>Description: 模块配置领域DTO </p>
 * <p>Date: 2020/8/12 17:08 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Data
@ToString
@EqualsAndHashCode
public class ModuleCmd {

    /**
     * ID
     */
    private Long id;

    /**
     * 名称
     */
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
     * 父节点定位符
     */
    private Long parentId;
}
