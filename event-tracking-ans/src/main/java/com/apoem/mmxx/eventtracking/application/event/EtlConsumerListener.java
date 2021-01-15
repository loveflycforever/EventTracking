package com.apoem.mmxx.eventtracking.application.event;

import com.apoem.mmxx.eventtracking.DateUtils;
import com.apoem.mmxx.eventtracking.domain.acquisition.model.aggregates.WrapperContentInfo;
import com.apoem.mmxx.eventtracking.domain.analysis.model.vo.ContentInfoVo;
import com.apoem.mmxx.eventtracking.domain.moduledeclaration.model.vo.ModuleVo;
import com.apoem.mmxx.eventtracking.domain.trackpointdisposition.model.vo.TrackPointVo;
import com.apoem.mmxx.eventtracking.infrastructure.convertor.ContentInfoVoConverter;
import com.apoem.mmxx.eventtracking.infrastructure.dao.mongo.*;
import com.apoem.mmxx.eventtracking.infrastructure.dao.mongo.dw.ConsumerAcqDailyDwDao;
import com.apoem.mmxx.eventtracking.infrastructure.dao.support.LongIdPair;
import com.apoem.mmxx.eventtracking.infrastructure.dao.support.TaskDo;
import com.apoem.mmxx.eventtracking.infrastructure.dao.support.TaskLabel;
import com.apoem.mmxx.eventtracking.infrastructure.enums.ActionDefinitionEnum;
import com.apoem.mmxx.eventtracking.infrastructure.enums.HouseTypeEnum;
import com.apoem.mmxx.eventtracking.infrastructure.enums.PeriodTypeEnum;
import com.apoem.mmxx.eventtracking.infrastructure.po.dm.CustomerHouseRankingEntity;
import com.apoem.mmxx.eventtracking.infrastructure.po.dw.ConsumerAcqDailyDwEntity;
import com.apoem.mmxx.eventtracking.infrastructure.po.ods.ConsumerAcquisitionOdsEntity;
import com.apoem.mmxx.eventtracking.infrastructure.po.query.AcqTrackPointQuery;
import com.apoem.mmxx.eventtracking.infrastructure.po.support.HourView;
import com.apoem.mmxx.eventtracking.infrastructure.repository.*;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

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
@TaskLabel(name = "C端埋点数据抽取", desc = "抽取C端埋点数据", order = 1)
public class EtlConsumerListener extends TaskDo {

    @Autowired
    private AcquisitionOdsReadRepository acquisitionOdsRepository;

    @Autowired
    private RelationshipDao2 relateDao2;

    @Autowired
    private RangeDao2 rangeDao2;

    @Autowired
    private TrackPointRepository trackPointRepository;

    @Autowired
    private ModuleRepository moduleRepository;

    @Autowired
    private ConsumerAcqDailyDwDao consumerAcqDailyDwDao;

    @Autowired
    private BizLogRepository bizLogRepository;

    @Autowired
    private TaskTableDao taskTableDao;

    @Autowired
    private CustomerHouseRankingDao2 customerHouseRankingDao2;

    @Autowired
    private CustomerHouseRankingDao customerHouseRankingDao;

    @Autowired
    private AcDefRecordRepository acDefRecordRepository;

    @Override
    public TaskTableDao taskTableDao() {
        return taskTableDao;
    }

    public void execute(LocalDateTime localDateTime) {
        startTask(localDateTime);

        LongIdPair idPair = acquisitionOdsRepository.consumerAcqDailyMinMaxId(localDateTime);

        Integer dateDay = DateUtils.numericalYyyyMmDd(localDateTime);

        // 清除数据
        // 日统计表一日一清除
        consumerAcqDailyDwDao.deleteAll();
        // 清除指定期间客户房源关系表
        customerHouseRankingDao.deleteByDateDay(dateDay);

        if (idPair.isAvailable()) {
            LongIdPair.Partition partition = idPair.partition(1000);
            for (LongIdPair.Partition p : partition) {
                long valueStart = p.getBeginId();
                long valueEnd = p.getEndId();

                List<ConsumerAcquisitionOdsEntity> entities = acquisitionOdsRepository.findConsumerAcq(valueStart, valueEnd, localDateTime);

                for (ConsumerAcquisitionOdsEntity entity : entities) {
                    // 处理 openId 和 uniqueId 映射
                    dealOpenIdAndUniqueId(entity);

                    // 生成日统计表
                    exactDaily(entity);
                    // 用户轨迹表
//                    exactTrail(localDateTime, entity);
                    // 客户房源关系表
                    exactCustomerHouse(localDateTime, entity);
                }
            }
        }

        finishTask(localDateTime);
    }

    private void exactCustomerHouse(LocalDateTime localDateTime, ConsumerAcquisitionOdsEntity entity) {
        Optional<TrackPointVo> optionalTrackPointVo = getTrackPointVo(entity);

        if (!optionalTrackPointVo.isPresent()) {
            return;
        }

        TrackPointVo trackPointVo = optionalTrackPointVo.get();
        // 未绑定用户不统计
        if (StringUtils.isBlank(entity.getUniqueId())) {
            return;
        }

        // COL_HUS、RPS_HUS也算VIS_HUS
        Predicate<String> visHus = ActionDefinitionEnum.VIS_HUS::maybe;
        Predicate<String> colHus = ActionDefinitionEnum.COL_HUS::maybe;
        Predicate<String> rpsHus = ActionDefinitionEnum.RPS_HUS::maybe;
        if (Arrays.stream(trackPointVo.getActionDefinition()).noneMatch(visHus.or(colHus).or(rpsHus))) {
            return;
        }

        Optional<WrapperContentInfo> optionalExtraInfo = EtlUtils.getExtraInfo(entity);
        if (!optionalExtraInfo.isPresent()) {
            return;
        }
        WrapperContentInfo contentInfo = optionalExtraInfo.get();
        // HUS 的参数校验必须通过
        if (!WrapperContentInfo.isHouse(contentInfo)) {
            return;
        }

        Integer dateDay = DateUtils.numericalYyyyMmDd(localDateTime);
        // 页面点击事件也算访问，但是收藏、转发需要单独统计
        boolean isVisHus = Arrays.stream(trackPointVo.getActionDefinition()).anyMatch(visHus);
        boolean isColHus = Arrays.stream(trackPointVo.getActionDefinition()).anyMatch(colHus);
        boolean isRpsHus = Arrays.stream(trackPointVo.getActionDefinition()).anyMatch(rpsHus);
        boolean isUnique = StringUtils.isNotBlank(entity.getUniqueId()) && isVisHus;
        // 2020-10-06 需求修改，收藏、转发事件不纳入访问量统计
        long pv = isVisHus ? 1 : 0;
        long uv = isUnique ? 1 : 0;
        long collected = isColHus ? 1 : 0;
        long reposted = isRpsHus ? 1 : 0;

        ContentInfoVo dos = ContentInfoVoConverter.deserialize(contentInfo, rangeDao2);

        HourView hourView = isUnique
                ? new HourView(dos.getDayOfWeek(), dos.getHourOfDay())
                : new HourView(dos.getDayOfWeek());
        CustomerHouseRankingEntity result = new CustomerHouseRankingEntity();
        result.setDateDay(dateDay);
        result.setPeriodType(PeriodTypeEnum.DAY1.getName());
        result.setCustomerId(entity.getUniqueId());
        result.setCity(entity.getCity());
        result.setHouseId(contentInfo.getHouseId());
        result.setHouseType(contentInfo.getHouseType());
        result.setHouseName(contentInfo.getHouseName());

        result.setHouseLivingRoom(contentInfo.getHouseLivingRoom());
        result.setHouseBedroom(contentInfo.getHouseBedroom());
        result.setHouseBathroom(contentInfo.getHouseBathroom());
        result.setCommunityId(contentInfo.getCommunityId());
        result.setCommunityName(contentInfo.getCommunityName());
        result.setPlateId(contentInfo.getPlateId());
        result.setPlateName(contentInfo.getPlateName());
        result.setAgentId(contentInfo.getAgentId());
        result.setStoreId(contentInfo.getStoreId());
        result.setStoreName(contentInfo.getStoreName());
        result.setCompanyId(contentInfo.getCompanyId());
        result.setCompanyName(contentInfo.getCompanyName());

        result.setHouseArea(dos.getResultHouseArea());
        result.setHouseAveragePrice(dos.getResultHouseAveragePrice());
        result.setHouseTotalPrice(dos.getResultHouseTotalPrice());
        result.setHouseAreaLower(dos.getResultHouseAreaLower());
        result.setHouseAreaUpper(dos.getResultHouseAreaUpper());

        result.setHouseBedroom(dos.getResultHouseBedroom());
        result.setAvgPriceRange(dos.getResultAvgPriceRange());
        result.setAvgPriceRangeOrder(dos.getResultAvgPriceRangeOrder());
        result.setTotalPriceRange(dos.getResultTotalPriceRange());
        result.setTotalPriceRangeOrder(dos.getResultTotalPriceRangeOrder());
        result.setAreaRange(dos.getResultAreaRange());
        result.setAreaRangeOrder(dos.getResultAreaRangeOrder());
        result.setLayoutRange(dos.getResultLayoutRange());
        result.setLayoutRangeOrder(dos.getResultLayoutRangeOrder());
        result.setDayOfWeek(dos.getDayOfWeek());

        result.setHourView(hourView);
        result.setLastOpTime(entity.getOpTime());
        result.setPageView(pv);
        result.setUniqueVisitor(uv);
        result.setCollected(collected);
        result.setReposted(reposted);

        customerHouseRankingDao2.insert(result);
    }

    private void exactDaily(ConsumerAcquisitionOdsEntity entity) {
        Optional<TrackPointVo> optionalTrackPointVo = getTrackPointVo(entity);

        if (!optionalTrackPointVo.isPresent()) {
            bizLogRepository.nonMappedTrackPointVo(entity);
            return;
        }
        TrackPointVo trackPointVo = optionalTrackPointVo.get();

        if (TrackPointVo.norCheckActionDef(trackPointVo)) {
            return;
        }

        ModuleVo moduleVo = Optional.ofNullable(moduleRepository.selectOne(trackPointVo.getModuleId()))
                .orElse(ModuleVo.empty());

        Optional<WrapperContentInfo> optionalExtraInfo = EtlUtils.getExtraInfo(entity);
        if (!optionalExtraInfo.isPresent()) {
            bizLogRepository.nonExtraInfo(entity);
            return;
        }

        WrapperContentInfo contentInfo = optionalExtraInfo.get();

        String[] actionDefinition = trackPointVo.getActionDefinition();
        String[] valiant = EtlUtils.checkExtraInfo(trackPointVo, contentInfo);

        if(ArrayUtils.isEmpty(valiant)) {
            bizLogRepository.allValidError(entity, actionDefinition, valiant);
            return;
        }
        if(ArrayUtils.getLength(valiant) < ArrayUtils.getLength(actionDefinition)) {
            bizLogRepository.partValidError(entity, actionDefinition, valiant);
        }

        String houseType = HouseTypeEnum.valueX(contentInfo.getHouseType());
        long duration = DateUtils.timeDis(entity.getEndTime(), entity.getStartTime());

        ContentInfoVo dos = ContentInfoVoConverter.deserialize(contentInfo, rangeDao2);

        ConsumerAcqDailyDwEntity consumerAcqDailyDwEntity = new ConsumerAcqDailyDwEntity();
        consumerAcqDailyDwEntity.setOpTime(DateUtils.trimToZero(entity.getOpTime()));
        consumerAcqDailyDwEntity.setCity(StringUtils.trimToEmpty(entity.getCity()));
        consumerAcqDailyDwEntity.setContent(StringUtils.trimToEmpty(entity.getContent()));
        consumerAcqDailyDwEntity.setPageName(StringUtils.trimToEmpty(entity.getPageName()));
        consumerAcqDailyDwEntity.setMethodName(StringUtils.trimToEmpty(entity.getMethodName()));
        consumerAcqDailyDwEntity.setEventType(StringUtils.trimToEmpty(entity.getEventType()));
        consumerAcqDailyDwEntity.setAvenue(StringUtils.trimToEmpty(entity.getAvenue()));
        consumerAcqDailyDwEntity.setStartTime(DateUtils.trimToZero(entity.getStartTime()));
        consumerAcqDailyDwEntity.setEndTime(DateUtils.trimToZero(entity.getEndTime()));
        consumerAcqDailyDwEntity.setOpenId(StringUtils.trimToEmpty(entity.getOpenId()));
        consumerAcqDailyDwEntity.setUniqueId(StringUtils.trimToEmpty(entity.getUniqueId()));

        consumerAcqDailyDwEntity.setDuration(duration);
        consumerAcqDailyDwEntity.setModuleId(trackPointVo.getModuleId());
        consumerAcqDailyDwEntity.setModuleName(StringUtils.trimToEmpty(moduleVo.getName()));
        consumerAcqDailyDwEntity.setTrackPointId(trackPointVo.getId());
        consumerAcqDailyDwEntity.setActionType(trackPointVo.getActionType());
        consumerAcqDailyDwEntity.setOriginActionDefinition(actionDefinition);
        consumerAcqDailyDwEntity.setActionDefinition(valiant);

        consumerAcqDailyDwEntity.setAgentId((contentInfo.getAgentId()));
        consumerAcqDailyDwEntity.setHouseId((contentInfo.getHouseId()));
        consumerAcqDailyDwEntity.setHouseName((contentInfo.getHouseName()));
        consumerAcqDailyDwEntity.setHouseType((houseType));

        consumerAcqDailyDwEntity.setCommunityId((contentInfo.getCommunityId()));
        consumerAcqDailyDwEntity.setCommunityName((contentInfo.getCommunityName()));
        consumerAcqDailyDwEntity.setPlateId((contentInfo.getPlateId()));
        consumerAcqDailyDwEntity.setPlateName((contentInfo.getPlateName()));
        consumerAcqDailyDwEntity.setInformationId((contentInfo.getInformationId()));
        consumerAcqDailyDwEntity.setStoreId((contentInfo.getStoreId()));
        consumerAcqDailyDwEntity.setStoreName((contentInfo.getStoreName()));
        consumerAcqDailyDwEntity.setCompanyId((contentInfo.getCompanyId()));
        consumerAcqDailyDwEntity.setCompanyName((contentInfo.getCompanyName()));
        consumerAcqDailyDwEntity.setHouseLivingRoom((contentInfo.getHouseLivingRoom()));
        consumerAcqDailyDwEntity.setHouseBathroom((contentInfo.getHouseBathroom()));
        consumerAcqDailyDwEntity.setBlockId((contentInfo.getBlockId()));
        consumerAcqDailyDwEntity.setBlockName((contentInfo.getBlockName()));
        consumerAcqDailyDwEntity.setInputType((contentInfo.getInputType()));
        consumerAcqDailyDwEntity.setPosterCode((contentInfo.getPosterCode()));
        consumerAcqDailyDwEntity.setPosterId((contentInfo.getPosterId()));
        consumerAcqDailyDwEntity.setGameId((contentInfo.getGameId()));
        consumerAcqDailyDwEntity.setOccurred(contentInfo.getOccurred());
        consumerAcqDailyDwEntity.setLocalStatus(contentInfo.getLocalStatus());
        consumerAcqDailyDwEntity.setTest(contentInfo.getTest());

        consumerAcqDailyDwEntity.setHouseArea(dos.getResultHouseArea());
        consumerAcqDailyDwEntity.setHouseAveragePrice(dos.getResultHouseAveragePrice());
        consumerAcqDailyDwEntity.setHouseTotalPrice(dos.getResultHouseTotalPrice());
        consumerAcqDailyDwEntity.setHouseAreaLower(dos.getResultHouseAreaLower());
        consumerAcqDailyDwEntity.setHouseAreaUpper(dos.getResultHouseAreaUpper());
        consumerAcqDailyDwEntity.setHouseBedroom(dos.getResultHouseBedroom());

        consumerAcqDailyDwEntity.setAvgPriceRange(dos.getResultAvgPriceRange());
        consumerAcqDailyDwEntity.setTotalPriceRange(dos.getResultTotalPriceRange());
        consumerAcqDailyDwEntity.setTotalPriceRangeOrder(dos.getResultTotalPriceRangeOrder());
        consumerAcqDailyDwEntity.setAreaRange(dos.getResultAreaRange());
        consumerAcqDailyDwEntity.setLayoutRange(dos.getResultLayoutRange());

        consumerAcqDailyDwDao.insert(consumerAcqDailyDwEntity);
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
