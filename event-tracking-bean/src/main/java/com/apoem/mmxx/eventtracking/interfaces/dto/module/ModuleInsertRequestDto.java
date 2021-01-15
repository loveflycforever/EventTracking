package com.apoem.mmxx.eventtracking.interfaces.dto.module;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: DeclaredModuleInsertRequestDto </p>
 * <p>Description: 模块信息对象Dto </p>
 * <p>Date: 2020/7/31 10:26 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Data
@ApiModel(description="模块信息对象")
public class ModuleInsertRequestDto {

    /**
     * 名称
     */
    @ApiModelProperty(value = "名称", required = true, example = "newHouse")
    @NotBlank(message = "名称")
    private String name;

    /**
     * 中文名称
     */
    @ApiModelProperty(value = "中文名称", required = true, example = "新房")
    @NotBlank(message = "中文名称")
    private String nameCn;

    /**
     * 排序
     */
    @ApiModelProperty(value = "排序", example = "1")
    private Integer orderNumber;

    /**
     * 父节点ID
     */
    @ApiModelProperty(value = "父节点ID", example = "1")
    private Long parentId;
}
