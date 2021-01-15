package com.apoem.mmxx.eventtracking.infrastructure.dao.mongo;

import com.apoem.mmxx.eventtracking.infrastructure.po.entity.ModuleEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: ModuleDao </p>
 * <p>Description: 模块配置 </p>
 * <p>Date: 2020/8/12 16:57 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
public interface ModuleDao extends MongoRepository<ModuleEntity, Long> {

    /**
     * 条件查询
     * @param name 名称
     * @return 实体
     */
    ModuleEntity findByName(String name);
}
