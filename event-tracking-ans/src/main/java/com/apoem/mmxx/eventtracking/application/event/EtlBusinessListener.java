package com.apoem.mmxx.eventtracking.application.event;

import com.apoem.mmxx.eventtracking.NumberUtils;
import com.apoem.mmxx.eventtracking.domain.acquisition.model.aggregates.ParamContentInfo;
import com.apoem.mmxx.eventtracking.domain.moduledeclaration.model.vo.ModuleVo;
import com.apoem.mmxx.eventtracking.domain.trackpointdisposition.model.vo.TrackPointVo;
import com.apoem.mmxx.eventtracking.infrastructure.dao.mongo.TaskTableDao;
import com.apoem.mmxx.eventtracking.infrastructure.dao.mongo.dw.BusinessAcqDailyDwDao;
import com.apoem.mmxx.eventtracking.infrastructure.dao.support.LongIdPair;
import com.apoem.mmxx.eventtracking.infrastructure.dao.support.TaskDo;
import com.apoem.mmxx.eventtracking.infrastructure.po.dw.BusinessAcqDailyDwEntity;
import com.apoem.mmxx.eventtracking.infrastructure.po.ods.BusinessAcquisitionOdsEntity;
import com.apoem.mmxx.eventtracking.infrastructure.po.query.AcqTrackPointQuery;
import com.apoem.mmxx.eventtracking.infrastructure.repository.AcquisitionOdsReadRepository;
import com.apoem.mmxx.eventtracking.infrastructure.repository.BizLogRepository;
import com.apoem.mmxx.eventtracking.infrastructure.repository.ModuleRepository;
import com.apoem.mmxx.eventtracking.infrastructure.repository.TrackPointRepository;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: EtlListener </p>
 * <p>Description: 数据抽取服务 </p>
 * <p>Date: 2020/8/27 8:39 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Service
public class EtlBusinessListener extends TaskDo {

    @Autowired
    private AcquisitionOdsReadRepository acquisitionOdsRepository;

    @Autowired
    private TrackPointRepository trackPointRepository;

    @Autowired
    private ModuleRepository moduleRepository;

    @Autowired
    private BusinessAcqDailyDwDao businessAcqDailyDwDao;

    @Autowired
    private BizLogRepository bizLogRepository;

    @Autowired
    private TaskTableDao taskTableDao;

    @Override
    public TaskTableDao taskTableDao() {
        return taskTableDao;
    }

    public void execute(LocalDateTime localDateTime) {
        startTask(localDateTime);

        LongIdPair idPair = acquisitionOdsRepository.businessAcqDailyMinMaxId(localDateTime);

        // 清除数据
        businessAcqDailyDwDao.deleteAll();

        if (idPair.isAvailable()) {

            LongIdPair.Partition partition = idPair.partition(1000);
            for (LongIdPair.Partition p : partition) {
                long valueStart = p.getBeginId();
                long valueEnd = p.getEndId();

                List<BusinessAcquisitionOdsEntity> entities = acquisitionOdsRepository.findBusinessAcq(valueStart, valueEnd, localDateTime);

                for (BusinessAcquisitionOdsEntity entity : entities) {

                    // 1 Android 2 iOS

                    AcqTrackPointQuery queryField = new AcqTrackPointQuery(entity);
                    Optional<TrackPointVo> optionalTrackPointVo = getTrackPointVo(queryField);
                    if (!optionalTrackPointVo.isPresent()) {
                        bizLogRepository.nonMappedTrackPointVo(entity);
                        continue;
                    }
                    TrackPointVo trackPointVo = optionalTrackPointVo.get();

                    if (TrackPointVo.norCheckActionDef(trackPointVo)) {
                        continue;
                    }

                    ParamContentInfo extraInfo = EtlUtils.getExtraInfo(entity);

                    String[] actionDefinition = trackPointVo.getActionDefinition();
                    String[] valiant = EtlUtils.checkExtraInfo(trackPointVo, entity, extraInfo);

                    if(ArrayUtils.isEmpty(valiant)) {
                        bizLogRepository.allValidError(entity, actionDefinition, valiant);
                        continue;
                    }
                    if(ArrayUtils.getLength(valiant) < ArrayUtils.getLength(actionDefinition)) {
                        bizLogRepository.partValidError(entity, actionDefinition, valiant);
                    }

                    ModuleVo moduleVo = Optional.ofNullable(moduleRepository.selectOne(trackPointVo.getModuleId()))
                            .orElse(ModuleVo.empty());

                    BusinessAcqDailyDwEntity dwEntity = new BusinessAcqDailyDwEntity();
                    dwEntity.setOpTime(new Date(NumberUtils.toLong(entity.getParamOpTime())));
                    dwEntity.setCity(StringUtils.trimToEmpty(entity.getCity()));
                    dwEntity.setPageName(StringUtils.trimToEmpty(entity.getParamPageId()));
                    dwEntity.setMethodName(StringUtils.trimToEmpty(entity.getParamObjectId()));
                    dwEntity.setEventType(StringUtils.trimToEmpty(entity.getParamKey()));
                    dwEntity.setAvenue(StringUtils.trimToEmpty(queryField.getAvenue()));
                    dwEntity.setLoginAccount(StringUtils.trimToEmpty(entity.getLoginAccount()));
                    dwEntity.setModuleName(StringUtils.trimToEmpty(moduleVo.getName()));
                    dwEntity.setModuleId(moduleVo.getId());
                    dwEntity.setTrackPointId(trackPointVo.getId());
                    dwEntity.setActionType(StringUtils.trimToEmpty(trackPointVo.getActionType()));
                    dwEntity.setActionDefinition(actionDefinition);

                    dwEntity.setAdId(extraInfo.getAdvId());
                    dwEntity.setCourseId(extraInfo.getCourseId());
                    dwEntity.setType(extraInfo.getType());
                    dwEntity.setGameId(extraInfo.getActId());
                    dwEntity.setStoreId(extraInfo.getStoreId());
                    dwEntity.setCompanyId(extraInfo.getCompanyId());
                    dwEntity.setTest(extraInfo.getTest());

                    businessAcqDailyDwDao.insert(dwEntity);
                }
            }
        }

        finishTask(localDateTime);
    }

    private Optional<TrackPointVo> getTrackPointVo(AcqTrackPointQuery queryField) {
        return Optional.ofNullable(trackPointRepository.selectWith(
                queryField.getEndpoint(),
                queryField.getPageName(),
                queryField.getMethodName(),
                queryField.getEventType(),
                queryField.getAvenue()));
    }
}
