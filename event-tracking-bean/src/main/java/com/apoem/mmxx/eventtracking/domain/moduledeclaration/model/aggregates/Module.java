package com.apoem.mmxx.eventtracking.domain.moduledeclaration.model.aggregates;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: Module </p>
 * <p>Description: 聚合根，模块 </p>
 * <p>Date: 2020/8/11 16:20 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Data
public class Module {

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
     * 父节点ID
     */
    private Long parentId;

    /**
     * 父节点
     */
    private ParentInfo parent;
}
