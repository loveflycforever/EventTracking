package com.apoem.mmxx.eventtracking.infrastructure.repository;

import com.google.common.collect.Lists;
import com.apoem.mmxx.eventtracking.DateUtils;
import com.apoem.mmxx.eventtracking.domain.analysis.model.vo.*;
import com.apoem.mmxx.eventtracking.exception.Junction;
import com.apoem.mmxx.eventtracking.exception.Suppose;
import com.apoem.mmxx.eventtracking.infrastructure.convertor.*;
import com.apoem.mmxx.eventtracking.infrastructure.dao.mongo.*;
import com.apoem.mmxx.eventtracking.infrastructure.enums.*;
import com.apoem.mmxx.eventtracking.infrastructure.po.dm.*;
import com.apoem.mmxx.eventtracking.infrastructure.po.entity.RelationshipEntity;
import com.apoem.mmxx.eventtracking.infrastructure.po.support.HourView;
import com.apoem.mmxx.eventtracking.interfaces.assembler.*;
import com.apoem.mmxx.eventtracking.interfaces.facade.CachedData;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: CustomerRepository </p>
 * <p>Description: 客源数仓 </p>
 * <p>Date: 2020/8/28 10:07 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Repository
public class CustomerRepository {

    @Resource
    private CustomerTrailDao2 customerTrailDao2;

    @Resource
    private CustomerHouseRankingAggregateDao customerHouseRankingAggregateDao;

    @Resource
    private CustomerHouseActionStatsDao customerHouseActionStatsDao;

    @Resource
    private CustomerInfoActionStatsDao customerInfoActionStatsDao;

    @Resource
    private IRelationshipRepository relationshipRepository;

    @Resource
    private RangeDao2 rangeDao2;

    @CachedData(duration = 20)
    public List<CustomerTrailVo> trail(CustomerTrailCmd customerTrailCmd) {

        List<RelationshipEntity> byUniqueId = relationshipRepository.selectWith(customerTrailCmd.getCustomerId());

        if (CollectionUtils.isEmpty(byUniqueId)) {
            return Collections.emptyList();
        }

        Object[] objects = byUniqueId.stream().map(RelationshipEntity::getOpenId).toArray(Object[]::new);

        Junction<String, Long> junction = new Junction<>(Long::parseLong, customerTrailCmd.getNextKey());
        Suppose.PARAMETER_VALIDATION.apply(junction, "nextKey");

        Long idKey = junction.getOut();
        if (idKey < 0L) {
            return Collections.emptyList();
        }

        if (idKey == 0L) {
            idKey = DateUtils.numericalYyMmDdHhMmSsSssToLong(LocalDateTime.now());
        }

        List<CustomerTrailEntity> entities;
        if (ActionTypeEnum.WHOLE.getName().equals(customerTrailCmd.getActionType())) {
            entities = customerTrailDao2
                    .findTrailWhole(customerTrailCmd.getCity(), customerTrailCmd.getAgentId(), idKey, objects);
        } else {
            entities = customerTrailDao2
                    .findTrail(customerTrailCmd.getCity(), customerTrailCmd.getActionType(), customerTrailCmd.getAgentId(), idKey, objects);
        }

        // 防止无效查询
        if (entities.size() < 100) {
            entities.forEach(o -> o.setIdKey(-1L));
        }

        return CustomerTrailVoConverter.deserialize(entities);
    }

    @CachedData(duration = 20)
    public CustomerHouseTrailVo houseTrail(CustomerHouseTrailCmd customerHouseTrailCmd) {
        Junction<String, Long> junction = new Junction<>(Long::parseLong, customerHouseTrailCmd.getNextKey());
        Suppose.PARAMETER_VALIDATION.apply(junction, "nextKey");

        List<RelationshipEntity> byUniqueId = relationshipRepository.selectWith(customerHouseTrailCmd.getCustomerId());
        if (CollectionUtils.isEmpty(byUniqueId)) {
            return new CustomerHouseTrailVo();
        }


        Object[] objects = byUniqueId.stream().map(RelationshipEntity::getOpenId).toArray(Object[]::new);

        Long idKey = junction.getOut();
        if (idKey < 0L) {
            return new CustomerHouseTrailVo();
        }

        if (idKey == 0L) {
            idKey = DateUtils.numericalYyMmDdHhMmSsSssToLong(LocalDateTime.now());
        }
        // 最后一次访问记录
        List<CustomerTrailEntity> lastVis = customerTrailDao2
                .ddd(customerHouseTrailCmd.getCity(), ActionDefinitionEnum.VIS_HUS.getName(), customerHouseTrailCmd.getHouseId(), customerHouseTrailCmd.getHouseType(), DateUtils.numericalYyMmDdHhMmSsSssToLong(LocalDateTime.now()),objects);

        // 访问VIS_HUS、收藏COL_HUS、转发RPS_HUS记录
        List<CustomerTrailEntity> entities = customerTrailDao2
                .findHouseTrail(customerHouseTrailCmd.getCity(), customerHouseTrailCmd.getHouseId(), customerHouseTrailCmd.getHouseType(), idKey, objects);
        return CustomerHouseTrailVoConverter.deserialize(lastVis, entities);
    }

    @CachedData(unit= TimeUnit.HOURS, duration = 1)
    public CustomerBriefVo brief(CustomerBriefCmd briefCmd) {

        Pair<LocalDateTime, LocalDateTime> pair = DateUtils.getDateDatePairClosed(briefCmd.getLocalDateTime(), 180);
        Integer beginDate = DateUtils.numericalYyyyMmDd(pair.getLeft());
        Integer endDate = DateUtils.numericalYyyyMmDd(pair.getRight());

        CustomerHouseRankingAgQuery query = new CustomerHouseRankingAgQuery(briefCmd.getCity(), briefCmd.getCustomerId(), briefCmd.getAgentId(), beginDate, endDate);

        // 房源-类型，按最多访问，最新时间排序
        List<CustomerHouseRankingEntity> byHouse = customerHouseRankingAggregateDao.mostViewHouse(query);
        // 取最多-最新访问
        Map<String, CustomerHouseRankingEntity> byHouseCollect = byHouse.stream()
                .collect(Collectors.groupingBy(this::getKey,
                        Collectors.collectingAndThen(Collectors.toList(), l -> l.get(0))));

        // 房源-类型，按价格 高-低 排序
        List<CustomerHouseRankingEntity> byHouseTotalPrice = customerHouseRankingAggregateDao.mostViewHouseTotalPrice(query);
        // 取中位数
        Map<String, CustomerHouseRankingEntity> byHouseTotalPriceCollect = byHouseTotalPrice.stream()
                .collect(Collectors.groupingBy(this::getKey,
                        Collectors.collectingAndThen(Collectors.toList(), l -> l.get(l.size() == 1 || l.size() % 2 == 0 ? l.size() / 2 : l.size() / 2 + 1))));

        // 房源-类型，按最多访问，最新时间排序
        List<CustomerHouseRankingEntity> byHouseLayout = customerHouseRankingAggregateDao.mostViewHouseLayout(query);
        // 取最多-最新访问
        Map<String, CustomerHouseRankingEntity> byHouseLayoutCollect = byHouseLayout.stream()
                .collect(Collectors.groupingBy(this::getKey,
                        Collectors.collectingAndThen(Collectors.toList(), l -> l.get(0))));

        // 房源-类型，按面积 高-低 排序
        List<CustomerHouseRankingEntity> byHouseArea = customerHouseRankingAggregateDao.mostViewHouseArea(query);
        // 取中位数
        Map<String, CustomerHouseRankingEntity> byHouseAreaCollect = byHouseArea.stream()
                .collect(Collectors.groupingBy(this::getKey,
                        Collectors.collectingAndThen(Collectors.toList(), l -> l.get(l.size() == 1 || l.size() % 2 == 0 ? l.size() / 2 : l.size() / 2 + 1))));

        List<CustomerBriefEntity> entities = toCustomerBriefEntities(byHouseCollect, byHouseTotalPriceCollect, byHouseLayoutCollect, byHouseAreaCollect);
        return CustomerBriefVoConverter.deserialize(entities);
    }

    private String getKey(CustomerHouseRankingEntity o) {
        return StringUtils.trimToEmpty(o.getHouseType());
    }

    private List<CustomerBriefEntity> toCustomerBriefEntities(Map<String, CustomerHouseRankingEntity> byHouseCollect,
                                                              Map<String, CustomerHouseRankingEntity> byHouseTotalPriceCollect,
                                                              Map<String, CustomerHouseRankingEntity> byHouseLayoutCollect,
                                                              Map<String, CustomerHouseRankingEntity> byHouseAreaCollect) {

        return Lists.newArrayList(
                toCustomerBriefEntity(byHouseCollect, byHouseTotalPriceCollect, byHouseLayoutCollect, byHouseAreaCollect, HouseTypeEnum.NWHS),
                toCustomerBriefEntity(byHouseCollect, byHouseTotalPriceCollect, byHouseLayoutCollect, byHouseAreaCollect, HouseTypeEnum.SHHS),
                toCustomerBriefEntity(byHouseCollect, byHouseTotalPriceCollect, byHouseLayoutCollect, byHouseAreaCollect, HouseTypeEnum.RTHS)
        );
    }

    private CustomerBriefEntity toCustomerBriefEntity(Map<String, CustomerHouseRankingEntity> byHouseCollect, Map<String, CustomerHouseRankingEntity> byHouseTotalPriceCollect, Map<String, CustomerHouseRankingEntity> byHouseLayoutCollect, Map<String, CustomerHouseRankingEntity> byHouseAreaCollect, HouseTypeEnum houseTypeEnum) {
        CustomerBriefEntity entity = new CustomerBriefEntity().init();
        entity.setHouseType(houseTypeEnum.getName());
        if (byHouseCollect.containsKey(houseTypeEnum.getName())) {
            CustomerHouseRankingEntity e = byHouseCollect.get(houseTypeEnum.getName());
            entity.setHouseId(e.getHouseId());
            entity.setHouseName(e.getHouseName());
            entity.setCommunityId(e.getCommunityId());
            entity.setCommunityName(e.getCommunityName());
        }

        if (byHouseTotalPriceCollect.containsKey(houseTypeEnum.getName())) {
            CustomerHouseRankingEntity e = byHouseTotalPriceCollect.get(houseTypeEnum.getName());
            entity.setHouseTotalPrice(e.getHouseTotalPrice());
        }


        if (byHouseLayoutCollect.containsKey(houseTypeEnum.getName())) {
            CustomerHouseRankingEntity e = byHouseLayoutCollect.get(houseTypeEnum.getName());
            entity.setHouseBedroom(e.getHouseBedroom());
            entity.setHouseLivingRoom(e.getHouseLivingRoom());
            entity.setHouseBathroom(e.getHouseBathroom());
        }


        if (byHouseAreaCollect.containsKey(houseTypeEnum.getName())) {
            CustomerHouseRankingEntity e = byHouseAreaCollect.get(houseTypeEnum.getName());

            entity.setHouseArea(e.getHouseArea());
        }
        return entity;
    }

    @CachedData(unit= TimeUnit.HOURS, duration = 1)
    public List<CustomerCommunityRankingVo> communityRanking(CustomerCommunityRankingCmd cmd) {

        if (HouseTypeEnum.EMPTY.maybe(cmd.getScope()) || HouseTypeEnum.WHOLE.maybe(cmd.getScope())) {
            return Collections.emptyList();
        }

        Junction<String, Integer> junction = new Junction<>(Integer::valueOf, cmd.getNextKey());
        Suppose.PARAMETER_VALIDATION.apply(junction, "nextKey");

        Integer idKey = junction.getOut();

        if (idKey < 0L) {
            return Collections.emptyList();
        }

        Pageable page = PageRequest.of(idKey, 5);

        // 产品运营要求，统计三十天数据
        PeriodTypeEnum periodTypeEnum = PeriodTypeEnum.DAY30;

        Pair<LocalDateTime, LocalDateTime> pair = DateUtils.getDateDatePairClosed(cmd.getDate(), periodTypeEnum.getNumber());
        Integer beginDate = DateUtils.numericalYyyyMmDd(pair.getLeft());
        Integer endDate = DateUtils.numericalYyyyMmDd(pair.getRight());

        Optional<CustomerHouseRankingEntity> houseTypeWhole = customerHouseRankingAggregateDao.getCommunityTypeWhole(cmd.getCity(), cmd.getCustomerId(), cmd.getAgentId(), cmd.getScope(), beginDate, endDate);

        CustomerHouseRankingEntity entity = houseTypeWhole.orElse(new CustomerHouseRankingEntity().init());
        List<CustomerHouseRankingEntity> entities = customerHouseRankingAggregateDao.getCommunityTypePage(cmd.getCity(), cmd.getCustomerId(), cmd.getAgentId(), cmd.getScope(), beginDate, endDate, page);

        List<List<CustomerHouseRankingEntity>> zzz = new ArrayList<>();
        entities.forEach(o -> {
            String communityId = o.getCommunityId();
            List<CustomerHouseRankingEntity> houseRankingEntities = customerHouseRankingAggregateDao.getByHouseType(cmd.getCity(), cmd.getCustomerId(), cmd.getAgentId(), communityId, cmd.getScope(), beginDate, endDate, FieldEnum.PVA);
            if (CollectionUtils.isNotEmpty(houseRankingEntities)) {
                zzz.add(houseRankingEntities);
            } else {
                zzz.add(Collections.emptyList());
            }
        });

        List<CustomerCommunityRankingVo> deserialize = CustomerHouseRankingVoConverter.deserialize(entity, entities, zzz);

        if (deserialize.size() < page.getPageSize()) {
            deserialize.forEach(o -> o.setNextKey("-1"));
        } else {
            deserialize.forEach(o -> o.setNextKey(String.valueOf(idKey + 1)));
        }
        return deserialize;
    }

    @CachedData(unit= TimeUnit.HOURS, duration = 1)
    public CustomerRangeGlanceVo rangeGlance(CustomerRangeGlanceCmd customerRangeGlanceCmd) {

        PeriodTypeEnum periodTypeEnum = PeriodTypeEnum.DAY30;
        Pair<LocalDateTime, LocalDateTime> pair = DateUtils.getDateDatePairClosed(customerRangeGlanceCmd.getLocalDateTime(), periodTypeEnum.getNumber());
        Integer beginDate = DateUtils.numericalYyyyMmDd(pair.getLeft());
        Integer endDate = DateUtils.numericalYyyyMmDd(pair.getRight());
        CustomerRangeAgQuery query = new CustomerRangeAgQuery(customerRangeGlanceCmd.getCity(),
                customerRangeGlanceCmd.getCustomerId(), customerRangeGlanceCmd.getAgentId(), beginDate, endDate,
                customerRangeGlanceCmd.getScope());

        CustomerRangeGlanceVo result = null;
        if (RangeGlanceFieldEnum.AREA.getName().equalsIgnoreCase(customerRangeGlanceCmd.getField())) {
            List<CustomerHouseRankingEntity> range = customerHouseRankingAggregateDao.rangeHouseArea(query);
            result = CustomerRangeVoConverter.deserializeFake(customerRangeGlanceCmd.getCity(), customerRangeGlanceCmd.getScope(), customerRangeGlanceCmd.getField(), range, rangeDao2);
        }

        if (RangeGlanceFieldEnum.PRICE.getName().equalsIgnoreCase(customerRangeGlanceCmd.getField())) {
            List<CustomerHouseRankingEntity> range = customerHouseRankingAggregateDao.rangeHouseTotalMoney(query);
            result = CustomerRangeVoConverter.deserializeFake(customerRangeGlanceCmd.getCity(), customerRangeGlanceCmd.getScope(), customerRangeGlanceCmd.getField(), range, rangeDao2);
        }

        if (RangeGlanceFieldEnum.TIME.getName().equalsIgnoreCase(customerRangeGlanceCmd.getField())) {
            List<HourView> range = customerHouseRankingAggregateDao.rangeHouseViewHour(query);
            result = CustomerRangeVoConverter.deserializeFake2(customerRangeGlanceCmd.getField(), range);
        }

        return result;
    }

    public List<CustomerStatsVo> checkStats(String city, List<String> idList, String houseType, LocalDateTime localDateTime) {
        Integer dateDay = DateUtils.numericalYyyyMmDd(localDateTime);

        List<CustomerHouseActionStatsEntity> totalHouse = new ArrayList<>(idList.size());
        List<CustomerInfoActionStatsEntity> totalInfo = new ArrayList<>(idList.size());
        Lists.partition(idList, 100).forEach(l -> {
            List<CustomerHouseActionStatsEntity> turnHouse = customerHouseActionStatsDao.findByCityAndCustomerIdInAndHouseTypeAndDateDay(city, l, houseType, dateDay);
            List<CustomerInfoActionStatsEntity> turnInfo = customerInfoActionStatsDao.findByCityAndCustomerIdInAndDateDay(city, l, dateDay);

            if (CollectionUtils.isNotEmpty(turnHouse)) {
                totalHouse.addAll(turnHouse);
            }


            if (CollectionUtils.isNotEmpty(turnInfo)) {
                totalInfo.addAll(turnInfo);
            }
        });


        return BaseStatsVoConverter.deserialize(idList, totalHouse, totalInfo);
    }
}
