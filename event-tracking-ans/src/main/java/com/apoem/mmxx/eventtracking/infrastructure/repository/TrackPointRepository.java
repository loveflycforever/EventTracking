package com.apoem.mmxx.eventtracking.infrastructure.repository;

import com.apoem.mmxx.eventtracking.BeanCopierUtils;
import com.apoem.mmxx.eventtracking.ListIs;
import com.apoem.mmxx.eventtracking.domain.trackpointdisposition.model.aggregates.TrackPoint;
import com.apoem.mmxx.eventtracking.domain.trackpointdisposition.model.vo.TrackPointVo;
import com.apoem.mmxx.eventtracking.domain.trackpointdisposition.repository.ITrackPointRepository;
import com.apoem.mmxx.eventtracking.infrastructure.convertor.TrackPointConverter;
import com.apoem.mmxx.eventtracking.infrastructure.convertor.TrackPointVoConverter;
import com.apoem.mmxx.eventtracking.infrastructure.dao.mongo.TrackPointDao;
import com.apoem.mmxx.eventtracking.infrastructure.dao.support.MongoGeneratedKeyHandler;
import com.apoem.mmxx.eventtracking.infrastructure.po.entity.TrackPointEntity;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: TrackPointRepository </p>
 * <p>Description: 埋点配置Repository </p>
 * <p>Date: 2020/7/14 12:47 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Repository
@CacheConfig(cacheNames = {"track_point"})
public class TrackPointRepository implements ITrackPointRepository {

    @Resource
    private TrackPointDao trackPointDao;

    @Resource
    private MongoGeneratedKeyHandler mongoGeneratedKeyHandler;

    @Override
    @CachePut(key = "#result.endpoint + '_' + #result.avenue + '_' + #result.methodName", condition = "#result != null")
    public TrackPointVo insert(TrackPoint trackPoint) {

        TrackPointEntity trackPointEntity = TrackPointConverter.serialize(trackPoint);
        trackPointEntity.setId(mongoGeneratedKeyHandler.getNextId(TrackPointEntity.class));
        trackPointEntity.setMark(0);
        TrackPointEntity result = trackPointDao.save(trackPointEntity);
        return TrackPointVoConverter.deserialize(result);
    }

    @Override
    @CachePut(key = "#result.endpoint + '_' + #result.avenue + '_' + #result.methodName", condition = "#result != null")
    public TrackPointVo update(TrackPoint trackPoint) {

        TrackPointEntity trackPointEntity = TrackPointConverter.serialize(trackPoint);

        Optional<TrackPointEntity> optionalEntity = trackPointDao.findById(trackPointEntity.getId());
        if (optionalEntity.isPresent()) {
            String[] ignoreFields = BeanCopierUtils.nonNullField(trackPointEntity);
            BeanUtils.copyProperties(optionalEntity, trackPointEntity, ignoreFields);
        } else {
            throw new RuntimeException("[埋点] must be non null.");
        }

        trackPointEntity.setMark(0);
        TrackPointEntity result = trackPointDao.save(trackPointEntity);
        return TrackPointVoConverter.deserialize(result);
    }

    @Override
    @CacheEvict(key = "#result.endpoint + '_' + #result.avenue + '_' + #result.methodName", condition = "#result != null")
    public TrackPointVo delete(Long id) {
        TrackPointVo result = null;
        Optional<TrackPointEntity> optionalEntity = trackPointDao.findById(id);
        if (optionalEntity.isPresent()) {
            TrackPointEntity entity = optionalEntity.get();
            entity.setMark(1);
            result = TrackPointVoConverter.deserialize(trackPointDao.save(entity));
        }
        return result;
    }

    @Override
    public TrackPointVo selectOne(Long id) {
        return trackPointDao.findById(id)
                .map(TrackPointVoConverter::deserialize)
                .orElse(null);
    }

    @Override
    public String getPageName(String pageName) {
        PageRequest of = PageRequest.of(0, 1, Sort.Direction.ASC, "createTime");

        List<TrackPointEntity> byPageNameAndMark = trackPointDao.findByPageName(pageName, of);

        if (CollectionUtils.isNotEmpty(byPageNameAndMark)) {
            return byPageNameAndMark.stream()
                    .map(TrackPointEntity::getPageNameCn)
                    .filter(StringUtils::isNotBlank).findFirst().orElse("");
        }
        return "";
    }

    @Override
    @Cacheable(key="#endpoint + '_' + #pageName + '_' + #methodName + '_' + #eventType + '_' + #avenue")
    public TrackPointVo selectWith(String endpoint, String pageName, String methodName, String eventType, String avenue) {
        return Optional.ofNullable(trackPointDao
                .findByEndpointAndPageNameAndMethodNameAndEventTypeAndAvenueAndMark(endpoint, pageName, methodName, eventType, avenue, 0))
                .map(TrackPointVoConverter::deserialize)
                .orElse(null);
    }

    @Override
    public TrackPointVo selectWithFromDb(String endpoint, String pageName, String methodName, String eventType, String avenue) {
        return Optional.ofNullable(trackPointDao
                .findByEndpointAndPageNameAndMethodNameAndEventTypeAndAvenue(endpoint, pageName, methodName, eventType, avenue))
                .map(TrackPointVoConverter::deserialize)
                .orElse(null);
    }

    @Override
    public List<TrackPointVo> selectAll(Integer page, Integer pageSize, String avenue, String pageName, String methodName) {

        int page1 = page - 1;

        if (page1 < 0) {
            throw new RuntimeException("[page] must be useful.");
        }

        int size = pageSize;
        if (size < 1) {
            throw new RuntimeException("[pageSize] must be useful.");
        }

        PageRequest of = PageRequest.of(page1, size, Sort.Direction.ASC, "createTime");

        List<TrackPointEntity> entityList;
        if (StringUtils.isNotBlank(avenue) && StringUtils.isNotBlank(pageName) && StringUtils.isNotBlank(pageName)) {
            entityList = trackPointDao.findByAvenueAndPageNameAndMethodName(avenue, pageName, methodName, of);
        } else if (StringUtils.isNotBlank(avenue) && StringUtils.isNotBlank(pageName)) {
            entityList = trackPointDao.findByAvenueAndPageName(avenue, pageName, of);
        } else if (StringUtils.isNotBlank(avenue) && StringUtils.isNotBlank(methodName)) {
            entityList = trackPointDao.findByAvenueAndMethodName(avenue, methodName, of);
        } else if (StringUtils.isNotBlank(pageName) && StringUtils.isNotBlank(methodName)) {
            entityList = trackPointDao.findByPageNameAndMethodName(pageName, methodName, of);
        } else if (StringUtils.isNotBlank(avenue)) {
            entityList = trackPointDao.findByAvenue(avenue, of);
        } else if (StringUtils.isNotBlank(pageName)) {
            entityList = trackPointDao.findByPageName(pageName, of);
        } else if (StringUtils.isNotBlank(methodName)) {
            entityList = trackPointDao.findByMethodName(methodName, of);
        } else {
            entityList = trackPointDao.findAll(of).getContent();
        }

        return ListIs.nonNull(entityList).stream()
                .map(TrackPointVoConverter::deserialize)
                .collect(Collectors.toList());
    }

    @Override
    public Integer selectSize(String avenue, String pageName, String methodName) {

        Integer count;
        if (StringUtils.isNotBlank(avenue) && StringUtils.isNotBlank(pageName) && StringUtils.isNotBlank(pageName)) {
            count = trackPointDao.countByAvenueAndPageNameAndMethodName(avenue, pageName, methodName);
        } else if (StringUtils.isNotBlank(avenue) && StringUtils.isNotBlank(pageName)) {
            count = trackPointDao.countByAvenueAndPageName(avenue, pageName);
        } else if (StringUtils.isNotBlank(avenue) && StringUtils.isNotBlank(methodName)) {
            count = trackPointDao.countByAvenueAndMethodName(avenue, methodName);
        } else if (StringUtils.isNotBlank(pageName) && StringUtils.isNotBlank(methodName)) {
            count = trackPointDao.countByPageNameAndMethodName(pageName, methodName);
        } else if (StringUtils.isNotBlank(avenue)) {
            count = trackPointDao.countByAvenue(avenue);
        } else if (StringUtils.isNotBlank(pageName)) {
            count = trackPointDao.countByPageName(pageName);
        } else if (StringUtils.isNotBlank(methodName)) {
            count = trackPointDao.countByMethodName(methodName);
        } else {
            count =Long.valueOf(trackPointDao.count()).intValue();
        }
        return count;
    }
}
