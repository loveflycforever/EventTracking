package com.apoem.mmxx.eventtracking.infrastructure.convertor;

import com.apoem.mmxx.eventtracking.NumberUtils;
import com.apoem.mmxx.eventtracking.domain.analysis.model.vo.*;
import com.apoem.mmxx.eventtracking.infrastructure.convertor.support.ZipMap;
import com.apoem.mmxx.eventtracking.infrastructure.enums.ActionTypeEnum;
import com.apoem.mmxx.eventtracking.infrastructure.po.dm.*;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: BaseStatsRowConverter </p>
 * <p>Description:  </p>
 * <p>Date: 2020/8/26 9:32 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
public class BaseStatsVoConverter {

    public static OverviewStatsVo deserialize(OverviewAgentStatsEntity agentStatsEntity,
                                              OverviewCustomerStatsEntity customerStatsEntity,
                                              OverviewHouseCollectedStatsEntity houseCollectedStatsEntity,
                                              OverviewCustomerH5StatsEntity overviewH5StatsEntity) {
        OverviewStatsVo result = new OverviewStatsVo();

        Optional.ofNullable(agentStatsEntity).ifPresent(o -> {
            result.setAgentPvAmount(o.getPageView());
            result.setAgentUvAmount(o.getUniqueVisitor());
        });


        Optional.ofNullable(customerStatsEntity).ifPresent(o -> {
            result.setCustomerPvAmount(o.getPageView());
            result.setCustomerUvAmount(o.getUniqueVisitor());
        });


        Optional.ofNullable(houseCollectedStatsEntity).ifPresent(o -> {
            result.setHouseColAmount(o.getPageView());
            result.setHouseUniqueColAmount(o.getUniqueVisitor());
        });

        Optional.ofNullable(overviewH5StatsEntity).ifPresent(o -> {
            result.setH5PvAmount(o.getPageView());
            result.setH5UvAmount(o.getUniqueVisitor());
        });
        return result;
    }


    public static List<CustomerStatsVo> deserialize(List<String> idList, List<CustomerHouseActionStatsEntity> totalHouse,
                                                    List<CustomerInfoActionStatsEntity> totalInfo) {
        HashMap<String, CustomerHouseActionStatsEntity> houseVisMap = totalHouse.stream().filter(o -> ActionTypeEnum.VIS.maybe(o.getActionType())).collect(Collectors.toMap(CustomerHouseActionStatsEntity::getCustomerId, o -> o, (o1, o2) -> o1, HashMap::new));
        HashMap<String, CustomerHouseActionStatsEntity> houseColMap = totalHouse.stream().filter(o -> ActionTypeEnum.COL.maybe(o.getActionType())).collect(Collectors.toMap(CustomerHouseActionStatsEntity::getCustomerId, o -> o, (o1, o2) -> o1, HashMap::new));
        HashMap<String, CustomerHouseActionStatsEntity> houseRpsMap = totalHouse.stream().filter(o -> ActionTypeEnum.RPS.maybe(o.getActionType())).collect(Collectors.toMap(CustomerHouseActionStatsEntity::getCustomerId, o -> o, (o1, o2) -> o1, HashMap::new));
        HashMap<String, CustomerInfoActionStatsEntity> infoVisMap = totalInfo.stream().filter(o -> ActionTypeEnum.VIS.maybe(o.getActionType())).collect(Collectors.toMap(CustomerInfoActionStatsEntity::getCustomerId, o -> o, (o1, o2) -> o1, HashMap::new));
        HashMap<String, CustomerInfoActionStatsEntity> infoRpsMap = totalInfo.stream().filter(o -> ActionTypeEnum.RPS.maybe(o.getActionType())).collect(Collectors.toMap(CustomerInfoActionStatsEntity::getCustomerId, o -> o, (o1, o2) -> o1, HashMap::new));

        return idList.stream().map(id -> {
            CustomerHouseActionStatsEntity hVis = houseVisMap.getOrDefault(id, new CustomerHouseActionStatsEntity().init());
            CustomerHouseActionStatsEntity hCol = houseColMap.getOrDefault(id, new CustomerHouseActionStatsEntity().init());
            CustomerHouseActionStatsEntity hRps = houseRpsMap.getOrDefault(id, new CustomerHouseActionStatsEntity().init());
            CustomerInfoActionStatsEntity iVis = infoVisMap.getOrDefault(id, new CustomerInfoActionStatsEntity().init());
            CustomerInfoActionStatsEntity iRps = infoRpsMap.getOrDefault(id, new CustomerInfoActionStatsEntity().init());


            CustomerStatsVo customerStatsVo = new CustomerStatsVo();
            customerStatsVo.setId(id);
            BaseStatsVo hv = new BaseStatsVo();
            hv.setId(id);
            hv.setPvAmount(hVis.getPageViewHouse());
            hv.setUvAmount(hVis.getUniqueVisitorHouse());
            hv.setColAmount(hCol.getCollectedUniqueHouse());
            hv.setRpsAmount(hRps.getRepostedHouse());
            customerStatsVo.setHouse(hv);

            BaseStatsVo iv = new BaseStatsVo();
            iv.setId(id);
            iv.setPvAmount(iVis.getPageViewInfo());
            iv.setUvAmount(iVis.getUniqueVisitorInfo());
            iv.setRpsAmount(iRps.getRepostedInfo());
            customerStatsVo.setInfo(iv);
            return customerStatsVo;
        }).collect(Collectors.toList());
    }

    public static List<HouseStatsVo> deserialize1(List<HouseRankingEntity> list1, List<HouseRankingH5Entity> list2) {
        ZipMap<String, HouseRankingEntity, HouseRankingH5Entity> zipMap =
                new ZipMap<>(list1, list2, HouseRankingEntity::getHouseId, HouseRankingH5Entity::getHouseId);
        List<Pair<HouseRankingEntity, HouseRankingH5Entity>> entityList = zipMap.getList();

        List<HouseStatsVo> result = Collections.emptyList();
        if (CollectionUtils.isNotEmpty(entityList)) {
            result = entityList.stream().map(BaseStatsVoConverter::deserialize11).collect(Collectors.toList());
        }
        return result;
    }

    public static List<BaseStatsVo> deserialize2(List<AgentVisitTrendEntity> list1, List<AgentH5StatsEntity> list2) {
        ZipMap<String, AgentVisitTrendEntity, AgentH5StatsEntity> zipMap =
                new ZipMap<>(list1, list2, AgentVisitTrendEntity::getAgentId, AgentH5StatsEntity::getAgentId);
        List<Pair<AgentVisitTrendEntity, AgentH5StatsEntity>> entityList = zipMap.getList();

        List<BaseStatsVo> result = Collections.emptyList();
        if (CollectionUtils.isNotEmpty(entityList)) {
            result = entityList.stream().map(BaseStatsVoConverter::deserialize22).collect(Collectors.toList());
        }
        return result;
    }

    public static List<BaseStatsVo> deserialize5(List<HouseTemplateEntity> list1, List<HouseTemplateH5Entity> list2) {

        ZipMap<String, HouseTemplateEntity, HouseTemplateH5Entity> zipMap =
                new ZipMap<>(list1, list2, HouseTemplateEntity::getHouseTemplateId, HouseTemplateH5Entity::getHouseTemplateId);
        List<Pair<HouseTemplateEntity, HouseTemplateH5Entity>> entityList = zipMap.getList();


        List<BaseStatsVo> result = Collections.emptyList();
        if (CollectionUtils.isNotEmpty(entityList)) {
            result = entityList.stream().map(BaseStatsVoConverter::deserialize55).collect(Collectors.toList());
        }
        return result;
    }

    public static List<BaseStatsVo> deserialize6(List<AdvertisementEntity> entities) {

        List<BaseStatsVo> result = Collections.emptyList();
        if (CollectionUtils.isNotEmpty(entities)) {
            result = entities.stream().map(BaseStatsVoConverter::deserialize).collect(Collectors.toList());
        }
        return result;
    }

    public static List<BaseStatsVo> deserialize7(List<CourseEntity> entities) {

        List<BaseStatsVo> result = Collections.emptyList();
        if (CollectionUtils.isNotEmpty(entities)) {
            result = entities.stream().map(BaseStatsVoConverter::deserialize).collect(Collectors.toList());
        }
        return result;
    }

    public static List<BaseStatsVo> deserialize8(List<StoreStatsEntity> list1, List<StoreH5StatsEntity> list2) {
        ZipMap<String, StoreStatsEntity, StoreH5StatsEntity> zipMap =
                new ZipMap<>(list1, list2, StoreStatsEntity::getStoreId, StoreH5StatsEntity::getStoreId);
        List<Pair<StoreStatsEntity, StoreH5StatsEntity>> entityList = zipMap.getList();

        List<BaseStatsVo> result = Collections.emptyList();
        if (CollectionUtils.isNotEmpty(entityList)) {
            result = entityList.stream().map(BaseStatsVoConverter::deserialize88).collect(Collectors.toList());
        }
        return result;
    }

    private static BaseStatsVo deserialize88(Pair<StoreStatsEntity, StoreH5StatsEntity> entity) {
        BaseStatsVo result = new BaseStatsVo();
        Optional.ofNullable(entity).ifPresent(o -> {
            StoreStatsEntity left = o.getLeft();
            StoreH5StatsEntity right = o.getRight();

            if(Optional.ofNullable(left).isPresent()) {
                result.setId(left.getStoreId());
                result.setPvAmount(left.getPageView());
                result.setUvAmount(left.getUniqueVisitor());
            }

            if(Optional.ofNullable(right).isPresent()) {
                result.setId(right.getStoreId());
                result.setPvAmount(NumberUtils.trimToZero(result.getPvAmount()) + right.getPageView());
                result.setUvAmount(NumberUtils.trimToZero(result.getUvAmount()) + right.getUniqueVisitor());
            }

        });
        return result;
    }

    public static BaseStatsVo deserialize(InformationStatsEntity entity) {
        BaseStatsVo result = new BaseStatsVo();
        Optional.ofNullable(entity).ifPresent(o -> {
            result.setId(o.getInformationId());
            result.setPvAmount(o.getPageView());
            result.setUvAmount(o.getUniqueVisitor());
        });
        return result;
    }

    private static BaseH5StatsVo deserialize22(Pair<AgentVisitTrendEntity, AgentH5StatsEntity> entity) {
        BaseH5StatsVo result = new BaseH5StatsVo();
        Optional.ofNullable(entity).ifPresent(o -> {
            AgentVisitTrendEntity left = o.getLeft();
            AgentH5StatsEntity right = o.getRight();

            if(Optional.ofNullable(left).isPresent()) {
                result.setId(left.getAgentId());
                result.setPvAmount(left.getPageView());
                result.setUvAmount(left.getUniqueVisitor());
            }

            if(Optional.ofNullable(right).isPresent()) {
                result.setId(right.getAgentId());
                result.setH5PvAmount(right.getPageView());
                result.setH5UvAmount(right.getUniqueVisitor());
            }
        });
        return result;
    }

    public static HouseStatsVo deserialize11(Pair<HouseRankingEntity, HouseRankingH5Entity> entity) {
        HouseStatsVo result = new HouseStatsVo();
        Optional.ofNullable(entity).ifPresent(o -> {

            HouseRankingEntity left = o.getLeft();
            HouseRankingH5Entity right = o.getRight();

            if(Optional.ofNullable(left).isPresent()) {
                result.setId(left.getHouseId());
                result.setPvAmount(left.getPageView());
                result.setUvAmount(left.getUniqueVisitor());
                result.setImConnectedAmount(left.getImConnected());
                result.setPhoneConnectedAmount(left.getPhoneConnected());
            }

            if(Optional.ofNullable(right).isPresent()) {
                result.setId(right.getHouseId());
                result.setH5PvAmount(right.getPageView());
                result.setH5UvAmount(right.getUniqueVisitor());
            }
        });
        return result;
    }

    public static BaseH5StatsVo deserialize55(Pair<HouseTemplateEntity, HouseTemplateH5Entity> entity) {
        BaseH5StatsVo result = new BaseH5StatsVo();
        Optional.ofNullable(entity).ifPresent(o -> {

            HouseTemplateEntity left = o.getLeft();
            HouseTemplateH5Entity right = o.getRight();

            if(Optional.ofNullable(left).isPresent()) {
                result.setId(left.getHouseTemplateId());
                result.setPvAmount(left.getPageView());
                result.setUvAmount(left.getUniqueVisitor());
            }

            if(Optional.ofNullable(right).isPresent()) {
                result.setId(right.getHouseTemplateId());
                result.setH5PvAmount(right.getPageView());
                result.setH5UvAmount(right.getUniqueVisitor());
            }
        });
        return result;
    }

    private static BaseStatsVo deserialize(AdvertisementEntity entity) {
        BaseStatsVo result = new BaseStatsVo();
        Optional.ofNullable(entity).ifPresent(o -> {
            result.setId(o.getAdvId());
            result.setPvAmount(o.getPageView());
            result.setUvAmount(o.getUniqueVisitor());
        });
        return result;
    }

    private static BaseStatsVo deserialize(CourseEntity entity) {
        BaseStatsVo result = new BaseStatsVo();
        Optional.ofNullable(entity).ifPresent(o -> {
            result.setId(o.getCourseId());
            result.setPvAmount(o.getPageView());
            result.setUvAmount(o.getUniqueVisitor());
        });
        return result;
    }
}
