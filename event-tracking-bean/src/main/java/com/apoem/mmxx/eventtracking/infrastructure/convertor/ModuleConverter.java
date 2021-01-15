package com.apoem.mmxx.eventtracking.infrastructure.convertor;

import com.apoem.mmxx.eventtracking.BeanCopierUtils;
import com.apoem.mmxx.eventtracking.domain.moduledeclaration.model.aggregates.Module;
import com.apoem.mmxx.eventtracking.domain.moduledeclaration.model.aggregates.ParentInfo;
import com.apoem.mmxx.eventtracking.infrastructure.po.entity.ModuleEntity;
import com.apoem.mmxx.eventtracking.interfaces.assembler.ModuleCmd;

import java.util.Objects;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: ModuleConverter </p>
 * <p>Description:  </p>
 * <p>Date: 2020/8/13 15:51 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
public class ModuleConverter {

    public static Module deserialize(ModuleCmd cmd) {
        Module result = new Module();

        BeanCopierUtils.copy(cmd, result);

        Long parentId = cmd.getParentId();
        if(Objects.nonNull(parentId)) {
            ParentInfo parentInfo = new ParentInfo();
            result.setParent(parentInfo);
        }
        return result;
    }

    public static ModuleEntity serialize(Module module) {
        ModuleEntity result = new ModuleEntity();
        BeanCopierUtils.copy(module, result);
        return result;
    }
}
