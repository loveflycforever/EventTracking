package com.apoem.mmxx.eventtracking.application.service;

import com.apoem.mmxx.eventtracking.domain.moduledeclaration.model.vo.ModuleVo;
import com.apoem.mmxx.eventtracking.interfaces.assembler.ModuleCmd;

import java.util.List;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: ModuleDeclarationService </p>
 * <p>Description: 应用层服务：模块配置服务 </p>
 * <p>Date: 2020/7/14 12:53 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
public interface ModuleDeclarationService {

    /**
     * 配置模块信息
     * @param cmd 数据对象
     */
    void appendModule(ModuleCmd cmd);

    /**
     * 配置模块信息
     * @param cmd 数据对象
     */
    void modifyModule(ModuleCmd cmd);

    /**
     * 配置模块信息
     * @param cmd 数据对象
     */
    void removeModule(ModuleCmd cmd);

    /**
     * 配置模块信息
     * @return 列表
     */
    List<ModuleVo> listModule();

}
