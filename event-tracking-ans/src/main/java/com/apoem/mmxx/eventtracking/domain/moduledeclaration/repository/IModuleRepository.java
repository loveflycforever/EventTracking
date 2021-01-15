package com.apoem.mmxx.eventtracking.domain.moduledeclaration.repository;

import com.apoem.mmxx.eventtracking.domain.moduledeclaration.model.aggregates.Module;
import com.apoem.mmxx.eventtracking.domain.moduledeclaration.model.vo.ModuleVo;

import java.util.List;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: IModuleRepository </p>
 * <p>Description: 模块 Repository 接口 </p>
 * <p>Date: 2020/8/3 9:58 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
public interface IModuleRepository {

    /**
     * 增
     *
     * @param module 请求
     * @return 结果
     */
    ModuleVo insert(Module module);

    /**
     * 改
     *
     * @param module 请求
     * @return 结果
     */
    ModuleVo update(Module module);

    /**
     * 删
     *
     * @param id 唯一键
     * @return 结果
     */
    ModuleVo delete(Long id);

    /**
     * 查
     *
     * @param id 唯一键
     * @return 结果
     */
    ModuleVo selectOne(Long id);

    /**
     * 条件查
     *
     * @param name 参数
     * @return 结果
     */
    ModuleVo selectWith(String name);

    /**
     * 全查
     *
     * @return 结果
     */
    List<ModuleVo> selectAll();
}
