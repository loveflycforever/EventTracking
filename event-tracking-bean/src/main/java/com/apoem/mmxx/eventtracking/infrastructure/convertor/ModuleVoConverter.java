package com.apoem.mmxx.eventtracking.infrastructure.convertor;

import com.apoem.mmxx.eventtracking.BeanCopierUtils;
import com.apoem.mmxx.eventtracking.domain.moduledeclaration.model.vo.ModuleVo;
import com.apoem.mmxx.eventtracking.infrastructure.po.entity.ModuleEntity;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: ModuleVoConverter </p>
 * <p>Description:  </p>
 * <p>Date: 2020/8/13 15:51 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
public class ModuleVoConverter {
    public static ModuleVo deserialize(ModuleEntity entity) {
        ModuleVo result = new ModuleVo();
        BeanCopierUtils.copy(entity, result);

        return result;
    }
}
