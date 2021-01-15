package com.apoem.mmxx.eventtracking.domain.moduledeclaration.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: ModuleVo </p>
 * <p>Description: 模块视图 </p>
 * <p>Date: 2020/8/12 17:17 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Data
public class ModuleVo {

    /**
     * ID
     */
    @ApiModelProperty(value = "ID", example = "1")
    private Long id;

    /**
     * 名称
     */
    @ApiModelProperty(value = "名称", example = "RegisterModule")
    private String name;

    /**
     * 中文名称
     */
    @ApiModelProperty(value = "中文名称", example = "注册模块")
    private String nameCn;

    /**
     * 排序
     */
    @ApiModelProperty(value = "排序", example = "1")
    private Integer orderNumber;

    /**
     * 父节点ID
     */
    @ApiModelProperty(value = "父节点ID", example = "22")
    private Long parentId;

    public static ModuleVo empty() {
        ModuleVo moduleVo = new ModuleVo();
        moduleVo.setId(-99L);
        moduleVo.setName(StringUtils.EMPTY);
        return moduleVo;
    }
}
