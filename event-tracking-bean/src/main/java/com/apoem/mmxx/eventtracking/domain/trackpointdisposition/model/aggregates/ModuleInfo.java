package com.apoem.mmxx.eventtracking.domain.trackpointdisposition.model.aggregates;

import lombok.Data;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: ModuleInfo </p>
 * <p>Description: 模块信息 </p>
 * <p>Date: 2020/8/11 16:20 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Data
public class ModuleInfo {

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
}
