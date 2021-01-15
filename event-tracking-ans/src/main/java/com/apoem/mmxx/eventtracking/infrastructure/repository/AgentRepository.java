package com.apoem.mmxx.eventtracking.infrastructure.repository;

import com.google.common.collect.Lists;
import com.apoem.mmxx.eventtracking.BaseConstants;
import com.apoem.mmxx.eventtracking.DateUtils;
import com.apoem.mmxx.eventtracking.infrastructure.enums.FieldEnum;
import com.apoem.mmxx.eventtracking.infrastructure.enums.PeriodTypeEnum;
import com.apoem.mmxx.eventtracking.infrastructure.dao.support.WonderfulPage;
import com.apoem.mmxx.eventtracking.infrastructure.enums.HouseTypeEnum;
import com.apoem.mmxx.eventtracking.exception.Junction;
import com.apoem.mmxx.eventtracking.exception.Suppose;
import com.apoem.mmxx.eventtracking.domain.analysis.model.vo.AgentCommunityRankingVo;
import com.apoem.mmxx.eventtracking.domain.analysis.model.vo.AgentHouseRankingVo;
import com.apoem.mmxx.eventtracking.domain.analysis.model.vo.AgentVisitTrendVo;
import com.apoem.mmxx.eventtracking.domain.analysis.model.vo.BaseStatsVo;
import com.apoem.mmxx.eventtracking.infrastructure.convertor.AgentCommunityRankingVoConverter;
import com.apoem.mmxx.eventtracking.infrastructure.convertor.AgentHouseRankingVoConverter;
import com.apoem.mmxx.eventtracking.infrastructure.convertor.AgentVisitTrendVoConverter;
import com.apoem.mmxx.eventtracking.infrastructure.convertor.BaseStatsVoConverter;
import com.apoem.mmxx.eventtracking.infrastructure.dao.mongo.*;
import com.apoem.mmxx.eventtracking.infrastructure.po.dm.*;
import com.apoem.mmxx.eventtracking.interfaces.assembler.AgentRankingCommunityCmd;
import com.apoem.mmxx.eventtracking.interfaces.assembler.AgentVisitTrendCmd;
import com.apoem.mmxx.eventtracking.interfaces.assembler.RankingHouseCmd;
import com.apoem.mmxx.eventtracking.interfaces.facade.CachedData;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: AgentRepository </p>
 * <p>Description: 经纪人数仓 </p>
 * <p>Date: 2020/7/15 11:30 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Repository
public class AgentRepository {

    @Resource
    private AgentVisitTrendDao agentVisitTrendDao;

    @Resource
    private AgentCommunityRankingDao agentCommunityRankingDao2;

    @Resource
    private AgentCommunityRankingAggregateDao agentCommunityRankingAggregateDao;

    @Resource
    private HouseRankingDao2 houseRankingDao2;

    @Resource
    private HouseRankingAggregateDao houseRankingAggregateDao;

    @Resource
    private AgentH5StatsDao agentH5StatsDao;

    @CachedData(unit= TimeUnit.HOURS, duration = 1)
    public List<AgentVisitTrendVo> visitTrend(AgentVisitTrendCmd agentVisitTrendCmd) {
        // 30天数据
        Pageable page = PageRequest.of(0, 30, Sort.Direction.DESC, "dateDay");
        List<AgentVisitTrendEntity> entities = agentVisitTrendDao
                .findByCityAndAgentIdAndHouseType(agentVisitTrendCmd.getCity(), agentVisitTrendCmd.getAgentId(), agentVisitTrendCmd.getHouseType(), page);
        return AgentVisitTrendVoConverter.deserialize(entities, agentVisitTrendCmd.getLocalDateTime());
    }

    @CachedData(unit= TimeUnit.HOURS, duration = 1)
    public List<AgentCommunityRankingVo> rankingCommunity(AgentRankingCommunityCmd cmd) {

//        isText(agentId, communityId);
//        isCity(city);
//        isScope(field);
//        isPeriod(field);
//        isField(field);

        Junction<String, Integer> junction = new Junction<>(Integer::valueOf, cmd.getNextKey());
        Suppose.PARAMETER_VALIDATION.apply(junction, "nextKey");

        Integer idKey = junction.getOut();

        if (idKey < 0L) {
            return Collections.emptyList();
        }

        // wonderful design，首页24，下一页25
        WonderfulPage page = new WonderfulPage(cmd.getField(), idKey);

        List<AgentCommunityRankingEntity> entities = Collections.emptyList();

        PeriodTypeEnum periodTypeEnum = PeriodTypeEnum.find(cmd.getPeriod());
        switch (periodTypeEnum) {
            case DAY1:
                entities = getAgentCommunityRankingEntities(cmd, page);
                break;
            case DAY7:
            case DAY30:
                entities = periodCommunityRanking(cmd, page, periodTypeEnum);
                break;
            default:
                break;
        }

        List<AgentCommunityRankingVo> deserialize = AgentCommunityRankingVoConverter.deserialize(entities);

        if (deserialize.size() < page.getPageSize()) {
            deserialize.forEach(o -> o.setNextKey(BaseConstants.LAST_PAGE_KEY));
        } else {
            deserialize.forEach(o -> o.setNextKey(String.valueOf(idKey + 1)));
        }
        return deserialize;
    }

    private List<AgentCommunityRankingEntity> getAgentCommunityRankingEntities(AgentRankingCommunityCmd cmd, WonderfulPage page) {
        Integer numericalAt = DateUtils.numericalYyyyMmDd(cmd.getDate());
        return agentCommunityRankingDao2.findByHouseType(cmd.getCity(), cmd.getAgentId(), numericalAt, cmd.getScope(), cmd.getPeriod(), page);
    }

    private List<AgentCommunityRankingEntity> periodCommunityRanking(AgentRankingCommunityCmd cmd, WonderfulPage page, PeriodTypeEnum periodType) {
        Pair<LocalDateTime, LocalDateTime> pair = DateUtils.getDateDatePairClosed(cmd.getDate(), periodType.getNumber());
        Integer beginDate = DateUtils.numericalYyyyMmDd(pair.getLeft());
        Integer endDate = DateUtils.numericalYyyyMmDd(pair.getRight());
        List<AgentCommunityRankingEntity> byHouseType = agentCommunityRankingAggregateDao.getByHouseType(cmd.getCity(), cmd.getAgentId(), cmd.getScope(), beginDate, endDate, page);
        Optional<AgentCommunityRankingEntity> byHouseTypeTotalOptional = agentCommunityRankingAggregateDao.getByHouseTypeTotal(cmd.getCity(), cmd.getAgentId(), cmd.getScope(), beginDate, endDate, page);

        AgentCommunityRankingEntity byHouseTypeTotal = byHouseTypeTotalOptional.orElseGet(() -> new AgentCommunityRankingEntity().init());

        for (AgentCommunityRankingEntity rankingEntity : byHouseType) {
            rankingEntity.setTotalPageView(byHouseTypeTotal.getPageView());
            rankingEntity.setTotalUniqueVisitor(byHouseTypeTotal.getUniqueVisitor());
            rankingEntity.setTotalCollected(byHouseTypeTotal.getCollected());
            rankingEntity.setTotalReposted(byHouseTypeTotal.getReposted());
        }
        return byHouseType;
    }

    @CachedData(unit= TimeUnit.HOURS, duration = 1)
    public List<AgentHouseRankingVo> rankingHouse(RankingHouseCmd cmd) {

        List<HouseRankingEntity> entities = Collections.emptyList();

//        isText(agentId, communityId);
//        isCity(city);
//        isScope(field);
//        isPeriod(field);
//        isField(field);

        // 仅作展示
        PeriodTypeEnum periodTypeEnum = PeriodTypeEnum.find(cmd.getPeriod());

        // 不做分页
        switch (periodTypeEnum) {
            case DAY1:
                entities = dailyHouseRanking(cmd);
                break;
            case DAY7:
            case DAY30:
                entities = periodHouseRanking(cmd, periodTypeEnum);
                break;
            default:
                break;
        }

        return AgentHouseRankingVoConverter.deserialize(entities);
    }

    private List<HouseRankingEntity> dailyHouseRanking(RankingHouseCmd cmd) {
        Integer numericalAt = DateUtils.numericalYyyyMmDd(cmd.getDate());

        HouseTypeEnum houseTypeEnum = HouseTypeEnum.find(cmd.getScope());
        FieldEnum fieldEnum = FieldEnum.find(cmd.getField());

        if (houseTypeEnum == HouseTypeEnum.WHOLE) {
            return houseRankingDao2.findWhole(cmd.getCity(), cmd.getAgentId(), cmd.getCommunityId(), numericalAt, cmd.getPeriod(), fieldEnum);
        }

        if (houseTypeEnum != HouseTypeEnum.EMPTY) {
            return houseRankingDao2.findByHouseType(cmd.getCity(), cmd.getAgentId(), cmd.getCommunityId(), numericalAt, cmd.getScope(), cmd.getPeriod(), fieldEnum);
        }
        return Collections.emptyList();
    }

    private List<HouseRankingEntity> periodHouseRanking(RankingHouseCmd cmd, PeriodTypeEnum periodTypeEnum) {
        Pair<LocalDateTime, LocalDateTime> pair = DateUtils.getDateDatePairClosed(cmd.getDate(), periodTypeEnum.getNumber());
        Integer beginDate = DateUtils.numericalYyyyMmDd(pair.getLeft());
        Integer endDate = DateUtils.numericalYyyyMmDd(pair.getRight());

        HouseTypeEnum houseTypeEnum = HouseTypeEnum.find(cmd.getScope());
        FieldEnum fieldEnum = FieldEnum.find(cmd.getField());

        if (houseTypeEnum == HouseTypeEnum.WHOLE) {
            return houseRankingAggregateDao.getWhole(cmd.getCity(), cmd.getAgentId(), cmd.getCommunityId(), beginDate, endDate, fieldEnum);
        }
        if (houseTypeEnum != HouseTypeEnum.EMPTY) {
            return houseRankingAggregateDao.getByHouseType(cmd.getCity(), cmd.getAgentId(), cmd.getCommunityId(), cmd.getScope(), beginDate, endDate, fieldEnum);
        }
        return Collections.emptyList();
    }

    public List<BaseStatsVo> checkStats(String city, List<String> idList, String houseType, LocalDateTime date) {
        Integer numericalAt = DateUtils.numericalYyyyMmDd(date);

        List<BaseStatsVo> result = new ArrayList<>(idList.size());
        Lists.partition(idList, 100).forEach(l -> {
            List<AgentVisitTrendEntity> list = agentVisitTrendDao.findByCityAndAgentIdInAndHouseTypeAndDateDay(city, l, houseType, numericalAt);
            List<AgentH5StatsEntity> list4 = agentH5StatsDao.findByCityAndAgentIdInAndHouseTypeAndDateDay(city, l, houseType, numericalAt);
            result.addAll(BaseStatsVoConverter.deserialize2(list, list4));
        });
        return result;
    }
}
