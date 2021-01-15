package com.apoem.mmxx.eventtracking.domain.moduledeclaration.service.engine;

import com.apoem.mmxx.eventtracking.domain.moduledeclaration.model.aggregates.Module;
import com.apoem.mmxx.eventtracking.domain.moduledeclaration.model.vo.ModuleVo;
import com.apoem.mmxx.eventtracking.domain.moduledeclaration.repository.IModuleRepository;
import com.apoem.mmxx.eventtracking.infrastructure.convertor.ModuleConverter;
import com.apoem.mmxx.eventtracking.interfaces.assembler.ModuleCmd;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: ModuleManager </p>
 * <p>Description: 模块管理器 </p>
 * <p>Date: 2020/8/3 9:58 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Slf4j
@Service
public class ModuleManager {

    @Resource
    private IModuleRepository moduleRepository;

    public void checksum(ModuleCmd cmd, boolean remove) {
        if (Objects.isNull(cmd)) {
            throw new RuntimeException("[模块] must be non null.");
        }

        if (!remove) {
            String name = cmd.getName();
            if (Objects.nonNull(moduleRepository.selectWith(name))) {
                throw new RuntimeException("[name 名称] must be unique.");
            }
        }

        if (Objects.nonNull(cmd.getId()) && Objects.isNull(moduleRepository.selectOne(cmd.getId()))) {
            throw new RuntimeException("[id] must be useful.");
        }
    }

    public void save(ModuleCmd cmd, boolean update) {
        Module module = ModuleConverter.deserialize(cmd);
        if(update) {
            moduleRepository.update(module);
        } else {
            moduleRepository.insert(module);
        }
    }

    public void remove(ModuleCmd cmd) {
        moduleRepository.delete(cmd.getId());
    }

    public List<ModuleVo> list() {
        return moduleRepository.selectAll();
    }
}
