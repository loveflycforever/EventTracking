package com.apoem.mmxx.eventtracking.application.event;

import com.apoem.mmxx.eventtracking.DateUtils;
import com.apoem.mmxx.eventtracking.domain.acquisition.model.aggregates.WrapperContentInfo;
import com.apoem.mmxx.eventtracking.domain.trackpointdisposition.model.vo.TrackPointVo;
import com.apoem.mmxx.eventtracking.infrastructure.common.holder.CurrentRequestHolder;
import com.apoem.mmxx.eventtracking.infrastructure.dao.mongo.*;
import com.apoem.mmxx.eventtracking.infrastructure.dao.support.LongIdPair;
import com.apoem.mmxx.eventtracking.infrastructure.dao.support.TaskDo;
import com.apoem.mmxx.eventtracking.infrastructure.dao.support.TaskLabel;
import com.apoem.mmxx.eventtracking.infrastructure.enums.ActionDefinitionEnum;
import com.apoem.mmxx.eventtracking.infrastructure.enums.ActionTypeEnum;
import com.apoem.mmxx.eventtracking.infrastructure.po.dm.CustomerTrailEntity;
import com.apoem.mmxx.eventtracking.infrastructure.po.entity.CustomerActionRecordEntity;
import com.apoem.mmxx.eventtracking.infrastructure.po.entity.TrailRecordEntity;
import com.apoem.mmxx.eventtracking.infrastructure.po.ods.ConsumerAcquisitionOdsEntity;
import com.apoem.mmxx.eventtracking.infrastructure.po.query.AcqTrackPointQuery;
import com.apoem.mmxx.eventtracking.infrastructure.repository.AcDefRecordRepository;
import com.apoem.mmxx.eventtracking.infrastructure.repository.AcquisitionOdsReadRepository;
import com.apoem.mmxx.eventtracking.infrastructure.repository.TrackPointRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: EtlConsumerListener </p>
 * <p>Description: C端数据抽取服务 </p>
 * <p>Date: 2020/8/27 8:39 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Service
@Slf4j
@TaskLabel(name = "C端埋点轨迹数据抽取", desc = "抽取C端埋点轨迹数据", order = 1)
public class EtlConsumerTrailListener extends TaskDo {

    @Autowired
    private AcquisitionOdsReadRepository acquisitionOdsRepository;

    @Autowired
    private RelationshipDao2 relateDao2;

    @Autowired
    private TrackPointRepository trackPointRepository;

    @Autowired
    private CustomerTrailDao customerTrailDao;

    @Autowired
    private TaskTableDao taskTableDao;

    @Autowired
    private TrailRecordDao trailRecordDao;

    @Autowired
    private TrailRecordDao2 trailRecordDao2;

    @Autowired
    private AcDefRecordRepository acDefRecordRepository;

    @Override
    public TaskTableDao taskTableDao() {
        return taskTableDao;
    }

    public void executeWholeDay(LocalDateTime localDateTime) {
        String serialNumber = CurrentRequestHolder.getSerialNumber();

        log.info("[" + serialNumber + "] execute trail whole [" + localDateTime + "] begin.");

        Integer dateDay = DateUtils.numericalYyyyMmDd(localDateTime);
        customerTrailDao.deleteByDateDay(dateDay);
        acDefRecordRepository.deleteByDateDay(dateDay);

        LongIdPair idPair = acquisitionOdsRepository.consumerAcqDailyMinMaxId(localDateTime);

        dsda(localDateTime, idPair);
        log.info("[" + serialNumber + "] execute trail whole [" + localDateTime + "] end.");
    }

    public void executeStep(LocalDateTime localDateTime) {
        long serialNumber = System.currentTimeMillis();
        log.info("[" + serialNumber + "] execute trail step [" + localDateTime + "] begin.");

        LocalDateTime signDate = localDateTime;

        startTask(localDateTime);

        Integer dateDay = DateUtils.numericalYyyyMmDd(signDate);

        TrailRecordEntity byDayDate = trailRecordDao.findByDateDay(dateDay);

        if (byDayDate == null) {
            // 每天的第 1 次运行
            signDate = localDateTime.minusDays(1);
            TrailRecordEntity byDayDate1 = trailRecordDao.findByDateDay(DateUtils.numericalYyyyMmDd(signDate));
            if (byDayDate1 != null) {
                // 保证前一天的运行完整
                Long startId = byDayDate1.getLastId();

                LongIdPair s = acquisitionOdsRepository.consumerAcqDailyMinMaxId(signDate);

                Long endId = s.getMaxId();
                if (endId > startId) {
                    LongIdPair idPair = new LongIdPair(Pair.of(startId + 1, endId));
                    dsda(signDate, idPair);

                    // 初始化今日记录，为接下来的计算准备数据
                    TrailRecordEntity trailRecordEntity = new TrailRecordEntity();
                    trailRecordEntity.setDateDay(DateUtils.numericalYyyyMmDd(localDateTime));
                    trailRecordEntity.setLastId(0L);
                    trailRecordDao2.upsert(trailRecordEntity);
                } else {
                    // 开始今天的运行
                    signDate = localDateTime;
                    LongIdPair idPair = acquisitionOdsRepository.consumerAcqDailyMinMaxId(signDate);
                    dsda(signDate, idPair);

                    TrailRecordEntity trailRecordEntity = new TrailRecordEntity();
                    trailRecordEntity.setDateDay(DateUtils.numericalYyyyMmDd(signDate));
                    trailRecordEntity.setLastId(idPair.getMaxId());
                    trailRecordDao2.upsert(trailRecordEntity);
                }
            } else {
                // 开始今天的运行
                signDate = localDateTime;
                LongIdPair s = acquisitionOdsRepository.consumerAcqDailyMinMaxId(signDate);

                Long startId = s.getMinId();
                Long endId = s.getMaxId();
                LongIdPair idPair = new LongIdPair(Pair.of(startId, endId));
                dsda(signDate, idPair);

                TrailRecordEntity trailRecordEntity = new TrailRecordEntity();
                trailRecordEntity.setDateDay(DateUtils.numericalYyyyMmDd(signDate));
                trailRecordEntity.setLastId(endId);
                trailRecordDao2.upsert(trailRecordEntity);
            }
        } else {
            // 每天 2 -> n 次运行
            signDate = localDateTime;
            LongIdPair s = acquisitionOdsRepository.consumerAcqDailyMinMaxId(signDate);

            Long startId = byDayDate.getLastId();
            Long endId = s.getMaxId();
            LongIdPair idPair = new LongIdPair(Pair.of(startId + 1, endId));
            dsda(signDate, idPair);

            TrailRecordEntity trailRecordEntity = new TrailRecordEntity();
            trailRecordEntity.setDateDay(DateUtils.numericalYyyyMmDd(signDate));
            trailRecordEntity.setLastId(endId);
            trailRecordDao2.upsert(trailRecordEntity);
        }

        finishTask(localDateTime);

        log.info("[" + serialNumber + "] execute trail step [" + localDateTime + "] end.");
    }

    private void dsda(LocalDateTime localDateTime, LongIdPair idPair) {
        if (idPair.isAvailable()) {
            LongIdPair.Partition partition = idPair.partition(1000);
            for (LongIdPair.Partition p : partition) {

                long valueStart = p.getBeginId();
                long valueEnd = p.getEndId();

                List<ConsumerAcquisitionOdsEntity> entities = acquisitionOdsRepository.findConsumerAcq(valueStart, valueEnd, localDateTime);

                for (ConsumerAcquisitionOdsEntity entity : entities) {

                    // 处理 openId 和 uniqueId 映射
                    dealOpenIdAndUniqueId(entity);

                    // 用户轨迹表
                    exactTrail(localDateTime, entity);
                }
            }
        }
    }

    private void exactTrail(LocalDateTime localDateTime, ConsumerAcquisitionOdsEntity entity) {



        // wonderful me
        Date createTime = entity.getCreateTime();
        Date originOpTime = entity.getOpTime();
        Long odsId = entity.getId();
        entity.setOpTime(createTime);


        Optional<TrackPointVo> optionalTrackPointVo = getTrackPointVo(entity);
        if (!optionalTrackPointVo.isPresent()) {
            return;
        }
        TrackPointVo trackPointVo = optionalTrackPointVo.get();

        // 轨迹上数据统计相关性弱、不强制校验数据
        Optional<WrapperContentInfo> optionalExtraInfo = EtlUtils.getExtraInfo(entity);
        WrapperContentInfo contentInfo = optionalExtraInfo.orElseGet(() -> new WrapperContentInfo().initForTrail());

        Integer dateDay = DateUtils.numericalYyyyMmDd(localDateTime);

        long duration = DateUtils.timeDis(entity.getEndTime(), entity.getStartTime());

        // VIS、COL、RPS 进入轨迹
        String actionType = trackPointVo.getActionType();
        if (!ActionTypeEnum.isTrail(actionType)) {
            return;
        }

        // 理论上只能配置一个埋点 actionType=VIS & actionDefinition=VIS_HUS & eventType = VIEW
        ActionDefinitionEnum actionDefinition = TrackPointVo.isVisitHouseDetailPage(trackPointVo) ? ActionDefinitionEnum.VIS_HUS : ActionDefinitionEnum.EMPTY;

        // 非正确房源过滤
        if (ActionDefinitionEnum.VIS_HUS.equals(actionDefinition)) {
            if (!WrapperContentInfo.isHouse(contentInfo)) {
                return;
            }
        }

        Long viewTimes = getViewTimes(localDateTime, trackPointVo, contentInfo);

        CustomerTrailEntity customerTrailEntity = CustomerTrailEntity.builder()
                .dateDay(dateDay)
                .city(StringUtils.trimToEmpty(entity.getCity()))
                // 轨迹存OpenId
                .customerId(StringUtils.trimToEmpty(entity.getOpenId()))
                .agentId((contentInfo.getAgentId()))
                .actionType(trackPointVo.getActionType())
                .content(entity.getContent())
                .viewTimes(viewTimes)
                .duration(duration)
                .actionDefinition(actionDefinition.getName())
                .houseId((contentInfo.getHouseId()))
                .houseType((contentInfo.getHouseType()))
                .pageName(StringUtils.trimToEmpty(entity.getPageName()))
                .methodName(StringUtils.trimToEmpty(entity.getMethodName()))
                .eventType(StringUtils.trimToEmpty(entity.getEventType()))
                .opTime(entity.getOpTime())
                .idKey(DateUtils.numericalYyMmDdHhMmSsSssToLongWithRandom(entity.getOpTime()))
                .odsId(odsId)
                .originOpTime(originOpTime)
                .build();
        customerTrailDao.insert(customerTrailEntity);
    }

    private synchronized Long getViewTimes(LocalDateTime localDateTime, TrackPointVo trackPointVo, WrapperContentInfo contentInfo) {
        long viewTimes = 0L;

        boolean openIdNotBlank = StringUtils.isNotBlank(contentInfo.getOpenId());

        boolean isVisHus = TrackPointVo.isVisitHouseDetailPage(trackPointVo);
        if (isVisHus && openIdNotBlank) {
            String actionDefinition = ActionDefinitionEnum.VIS_HUS.getName();

            int initTime = 1;
            // 最近一次的记录
            boolean isInit = acDefRecordRepository.initToday(localDateTime, actionDefinition,
                    (contentInfo.getCity()),
                    (contentInfo.getOpenId()),
                    (contentInfo.getHouseId()),
                    (contentInfo.getHouseType()));

            if (!isInit) {
                acDefRecordRepository.initDateDay(localDateTime, actionDefinition,
                        (contentInfo.getCity()),
                        (contentInfo.getOpenId()),
                        (contentInfo.getHouseId()),
                        (contentInfo.getHouseType()));
            }


            CustomerActionRecordEntity customerActionRecordEntity = acDefRecordRepository.incToday(localDateTime, actionDefinition,
                    (contentInfo.getCity()),
                    (contentInfo.getOpenId()),
                    (contentInfo.getHouseId()),
                    (contentInfo.getHouseType()));
            viewTimes = customerActionRecordEntity.getNumber() + customerActionRecordEntity.getPrevDayMaxNumber();
        }
        return viewTimes;
    }

    private void dealOpenIdAndUniqueId(ConsumerAcquisitionOdsEntity entity) {
        String uniqueId = entity.getUniqueId();
        String openId = entity.getOpenId();
        if (StringUtils.isNotBlank(uniqueId) && StringUtils.isNotBlank(openId)) {
            relateDao2.appendRecord(uniqueId, openId);
        }
    }

    private Optional<TrackPointVo> getTrackPointVo(ConsumerAcquisitionOdsEntity entity) {
        AcqTrackPointQuery field = new AcqTrackPointQuery(entity);

        return Optional.ofNullable(trackPointRepository.selectWith(
                field.getEndpoint(),
                field.getPageName(),
                field.getMethodName(),
                field.getEventType(),
                field.getAvenue()));
    }
}
