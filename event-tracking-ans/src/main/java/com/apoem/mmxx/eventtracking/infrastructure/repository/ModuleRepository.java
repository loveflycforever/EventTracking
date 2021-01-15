package com.apoem.mmxx.eventtracking.infrastructure.repository;

import com.apoem.mmxx.eventtracking.BeanCopierUtils;
import com.apoem.mmxx.eventtracking.ListIs;
import com.apoem.mmxx.eventtracking.domain.moduledeclaration.model.aggregates.Module;
import com.apoem.mmxx.eventtracking.domain.moduledeclaration.model.vo.ModuleVo;
import com.apoem.mmxx.eventtracking.domain.moduledeclaration.repository.IModuleRepository;
import com.apoem.mmxx.eventtracking.infrastructure.convertor.ModuleConverter;
import com.apoem.mmxx.eventtracking.infrastructure.convertor.ModuleVoConverter;
import com.apoem.mmxx.eventtracking.infrastructure.dao.mongo.ModuleDao;
import com.apoem.mmxx.eventtracking.infrastructure.dao.support.MongoGeneratedKeyHandler;
import com.apoem.mmxx.eventtracking.infrastructure.po.entity.ModuleEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: ModuleRepository </p>
 * <p>Description: 模块 Repository </p>
 * <p>Date: 2020/7/14 12:47 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Repository
@CacheConfig(cacheNames = {"module"})
public class ModuleRepository implements IModuleRepository {

    @Resource
    private ModuleDao moduleDao;

    @Resource
    private MongoGeneratedKeyHandler mongoGeneratedKeyHandler;

    @Override
    @CachePut(key = "#result.name", condition = "#result != null")
    public ModuleVo insert(Module module) {
        ModuleEntity moduleEntity = ModuleConverter.serialize(module);
        moduleEntity.setId(mongoGeneratedKeyHandler.getNextId(ModuleEntity.class));
        moduleEntity.setMark(0);
        ModuleEntity result = moduleDao.save(moduleEntity);
        return ModuleVoConverter.deserialize(result);
    }

    @Override
    @CachePut(key = "#result.name", condition = "#result != null")
    public ModuleVo update(Module module) {
        ModuleEntity moduleEntity = ModuleConverter.serialize(module);

        Optional<ModuleEntity> optionalEntity = moduleDao.findById(moduleEntity.getId());
        if (optionalEntity.isPresent()) {
            String[] ignoreFields = BeanCopierUtils.nonNullField(moduleEntity);
            BeanUtils.copyProperties(optionalEntity, moduleEntity, ignoreFields);
        } else {
            throw new RuntimeException("[模块] must be non null.");
        }

        moduleEntity.setMark(0);
        ModuleEntity result = moduleDao.save(moduleEntity);
        return ModuleVoConverter.deserialize(result);
    }

    @Override
    @CacheEvict(key = "#result.name", condition = "#result != null")
    public ModuleVo delete(Long id) {
        ModuleVo result = null;
        Optional<ModuleEntity> optionalEntity = moduleDao.findById(id);
        if (optionalEntity.isPresent()) {
            ModuleEntity entity = optionalEntity.get();
            entity.setMark(1);
            result = ModuleVoConverter.deserialize(moduleDao.save(entity));
        }
        return result;
    }

    @Override
    public ModuleVo selectOne(Long id) {
        return moduleDao.findById(id)
                .filter(o -> o.getMark() == 0)
                .map(ModuleVoConverter::deserialize)
                .orElse(null);
    }

    @Override
    @Cacheable(key="#name")
    public ModuleVo selectWith(String name) {
        return Optional.ofNullable(moduleDao.findByName(name))
                .filter(o -> o.getMark() == 0)
                .map(ModuleVoConverter::deserialize)
                .orElse(null);
    }

    @Override
    public List<ModuleVo> selectAll() {
        List<ModuleEntity> entityList = moduleDao.findAll();
        return ListIs.nonNull(entityList).stream()
                .filter(o -> o.getMark() == 0)
                .map(ModuleVoConverter::deserialize)
                .collect(Collectors.toList());
    }
}
