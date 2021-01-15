package com.apoem.mmxx.eventtracking.infrastructure.repository;

import com.apoem.mmxx.eventtracking.NumberUtils;
import com.apoem.mmxx.eventtracking.infrastructure.po.query.AcqTrackPointQuery;
import com.apoem.mmxx.eventtracking.infrastructure.dao.mongo.BizLogDao;
import com.apoem.mmxx.eventtracking.infrastructure.po.entity.BizLogEntity;
import com.apoem.mmxx.eventtracking.infrastructure.po.ods.BusinessAcquisitionOdsEntity;
import com.apoem.mmxx.eventtracking.infrastructure.po.ods.ConsumerAcquisitionOdsEntity;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Repository;

import java.util.Objects;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: BizLogRepository </p>
 * <p>Description:  </p>
 * <p>Date: 2020/9/9 13:08 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Repository
public class BizLogRepository {

    @Autowired
    private BizLogDao bizLogDao;

    public void nonMappedTrackPointVo(ConsumerAcquisitionOdsEntity entity) {
        String desc = "[Consumer]NonMappedTrackPointVo";
        String target = StringUtils.EMPTY;
        String source = StringUtils.EMPTY;
        BizLogEntity bizLogEntity = getBizLogEntity(entity, desc, source, target);
        bizLogDao.save(bizLogEntity);
    }

    private <T> String getTableName(T entity) {
        Class<?> aClass = entity.getClass();
        String collection = aClass.getCanonicalName();
        Document annotation = aClass.getAnnotation(Document.class);
        if(annotation != null) {
            collection = annotation.collection();
        }
        return collection;
    }

    public void nonMappedTrackPointVo(BusinessAcquisitionOdsEntity entity) {
        String desc = "[Business]NonMappedTrackPointVo";
        String target = StringUtils.EMPTY;
        String source = StringUtils.EMPTY;
        BizLogEntity bizLogEntity = getBizLogEntity(entity, desc, target, source);
        bizLogDao.save(bizLogEntity);
    }

    public void nonExtraInfo(ConsumerAcquisitionOdsEntity entity) {
        String desc = "[Consumer]NonExtraInfo";
        String target = StringUtils.EMPTY;
        String source = StringUtils.EMPTY;
        BizLogEntity bizLogEntity = getBizLogEntity(entity, desc, target, source);
        bizLogDao.save(bizLogEntity);
    }

    public void allValidError(BusinessAcquisitionOdsEntity entity, String[] actionDefinition, String[] valiant) {
        String desc = "[Business]AllValidError";
        String target = ArrayUtils.toString(valiant);
        String source = ArrayUtils.toString(actionDefinition);
        BizLogEntity bizLogEntity = getBizLogEntity(entity, desc, source, target);
        bizLogDao.save(bizLogEntity);
    }

    public void partValidError(BusinessAcquisitionOdsEntity entity, String[] actionDefinition, String[] valiant) {
        String desc = "[Business]PartValidError";
        String target = ArrayUtils.toString(valiant);
        String source = ArrayUtils.toString(actionDefinition);
        BizLogEntity bizLogEntity = getBizLogEntity(entity, desc, source, target);
        bizLogDao.save(bizLogEntity);
    }

    public void allValidError(ConsumerAcquisitionOdsEntity entity, String[] actionDefinition, String[] valiant) {
        String desc = "[Consumer]AllValidError";
        String source = ArrayUtils.toString(actionDefinition);
        String target = ArrayUtils.toString(valiant);
        BizLogEntity bizLogEntity = getBizLogEntity(entity, desc, source, target);
        bizLogDao.save(bizLogEntity);
    }

    public void partValidError(ConsumerAcquisitionOdsEntity entity, String[] actionDefinition, String[] valiant) {
//        String desc = "[Consumer]PartValidError";
//        String target = ArrayUtils.toString(valiant);
//        String source = ArrayUtils.toString(actionDefinition);
//        BizLogEntity bizLogEntity = getBizLogEntity(entity, desc, source, target);
//        bizLogDao.save(bizLogEntity);
    }

    private BizLogEntity getBizLogEntity(ConsumerAcquisitionOdsEntity entity, String desc, String source, String target) {
        BizLogEntity bizLogEntity = new BizLogEntity();
        AcqTrackPointQuery queryField = new AcqTrackPointQuery(entity);
        bizLogEntity.setTableName(getTableName(entity));
        bizLogEntity.setDesc(desc);
        bizLogEntity.setKeywords(NumberUtils.toString(entity.getId()));
        bizLogEntity.setPageName(StringUtils.trimToEmpty(queryField.getPageName()));
        bizLogEntity.setMethodName(StringUtils.trimToEmpty(queryField.getMethodName()));
        bizLogEntity.setEventType(StringUtils.trimToEmpty(queryField.getEventType()));
        bizLogEntity.setAvenue(StringUtils.trimToEmpty(queryField.getAvenue()));
        bizLogEntity.setEndpoint(StringUtils.trimToEmpty(queryField.getEndpoint()));
        bizLogEntity.setSource(source);
        bizLogEntity.setTarget(target);
        bizLogEntity.setContent(Objects.toString(entity));
        return bizLogEntity;
    }


    private BizLogEntity getBizLogEntity(BusinessAcquisitionOdsEntity entity, String desc, String source, String target) {
        AcqTrackPointQuery queryField = new AcqTrackPointQuery(entity);
        BizLogEntity bizLogEntity = new BizLogEntity();
        bizLogEntity.setTableName(getTableName(entity));
        bizLogEntity.setDesc(desc);
        bizLogEntity.setKeywords(NumberUtils.toString(entity.getId()));
        bizLogEntity.setPageName(StringUtils.trimToEmpty(queryField.getPageName()));
        bizLogEntity.setMethodName(StringUtils.trimToEmpty(queryField.getMethodName()));
        bizLogEntity.setEventType(StringUtils.trimToEmpty(queryField.getEventType()));
        bizLogEntity.setAvenue(StringUtils.trimToEmpty(queryField.getAvenue()));
        bizLogEntity.setEndpoint(StringUtils.trimToEmpty(queryField.getEndpoint()));
        bizLogEntity.setSource(source);
        bizLogEntity.setTarget(target);
        bizLogEntity.setContent(Objects.toString(entity));
        return bizLogEntity;
    }
}
