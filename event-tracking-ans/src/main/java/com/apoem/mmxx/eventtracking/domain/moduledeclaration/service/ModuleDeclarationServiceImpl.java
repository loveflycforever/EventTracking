package com.apoem.mmxx.eventtracking.domain.moduledeclaration.service;

import com.apoem.mmxx.eventtracking.application.service.ModuleDeclarationService;
import com.apoem.mmxx.eventtracking.domain.moduledeclaration.model.vo.ModuleVo;
import com.apoem.mmxx.eventtracking.domain.moduledeclaration.service.engine.ModuleManager;
import com.apoem.mmxx.eventtracking.interfaces.assembler.ModuleCmd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: ModuleDeclarationServiceImpl </p>
 * <p>Description: 业务层模块服务实现 </p>
 * <p>Date: 2020/8/13 15:45 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Service
public class ModuleDeclarationServiceImpl implements ModuleDeclarationService {

    private final ModuleManager moduleManager;

    @Autowired
    public ModuleDeclarationServiceImpl(ModuleManager moduleManager) {
        this.moduleManager = moduleManager;
    }

    @Override
    public void appendModule(ModuleCmd cmd) {
        configModule(cmd, false);
    }

    @Override
    public void modifyModule(ModuleCmd cmd) {
        configModule(cmd, false);
    }

    @Override
    public void removeModule(ModuleCmd cmd) {
        moduleManager.checksum(cmd, true);
        moduleManager.remove(cmd);
    }

    @Override
    public List<ModuleVo> listModule() {
        return moduleManager.list();
    }

    private void configModule(ModuleCmd cmd, boolean update) {
        moduleManager.checksum(cmd, false);
        moduleManager.save(cmd, update);
    }
}
