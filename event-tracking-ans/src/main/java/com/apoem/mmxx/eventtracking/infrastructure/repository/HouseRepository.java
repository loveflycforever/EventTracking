package com.apoem.mmxx.eventtracking.infrastructure.repository;

import com.google.common.collect.Lists;
import com.apoem.mmxx.eventtracking.DateUtils;
import com.apoem.mmxx.eventtracking.domain.analysis.model.vo.BaseStatsVo;
import com.apoem.mmxx.eventtracking.domain.analysis.model.vo.HouseStatsVo;
import com.apoem.mmxx.eventtracking.domain.analysis.model.vo.HouseTrendVo;
import com.apoem.mmxx.eventtracking.domain.analysis.model.vo.PosterStatsVo;
import com.apoem.mmxx.eventtracking.infrastructure.convertor.BaseStatsVoConverter;
import com.apoem.mmxx.eventtracking.infrastructure.convertor.HouseImplicateVoConverter;
import com.apoem.mmxx.eventtracking.infrastructure.dao.mongo.*;
import com.apoem.mmxx.eventtracking.infrastructure.enums.PeriodTypeEnum;
import com.apoem.mmxx.eventtracking.infrastructure.po.dm.*;
import com.apoem.mmxx.eventtracking.infrastructure.po.dm.CustomerHouseRankingEntity;
import com.apoem.mmxx.eventtracking.infrastructure.po.dm.HouseRankingEntity;
import com.apoem.mmxx.eventtracking.infrastructure.po.dm.HouseRankingH5Entity;
import com.apoem.mmxx.eventtracking.infrastructure.po.dm.HouseTemplateEntity;
import com.apoem.mmxx.eventtracking.infrastructure.po.dm.PosterEntity;
import com.apoem.mmxx.eventtracking.interfaces.assembler.HouseTrendCmd;
import com.apoem.mmxx.eventtracking.interfaces.assembler.VisitorCmd;
import com.apoem.mmxx.eventtracking.interfaces.facade.CachedData;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: HouseStatisticsReportRepository </p>
 * <p>Description: 房源报表 </p>
 * <p>Date: 2020/7/15 11:28 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Repository
public class HouseRepository {

    @Autowired
    private HouseRankingDao houseRankingDao;

    @Autowired
    private HouseRankingH5Dao houseRankingH5Dao;

    @Autowired
    private HouseRankingAggregateDao houseRankingAggregateDao;

    @Autowired
    private CustomerHouseRankingDao2 customerHouseRankingDao2;

    @Autowired
    private HouseTemplateStatsDao houseTemplateStatsDao;
    @Autowired
    private HouseTemplateH5StatsDao houseTemplateH5StatsDao;

    @Autowired
    private CustomerHouseRankingAggregateDao customerHouseRankingAggregateDao;

    @Autowired
    private PosterStatsDao posterStatsDao;

    @Autowired
    private PosterTemplateStatsDao posterTemplateStatsDao;

    public List<HouseStatsVo> checkStats(String city, List<String> idList, String type, LocalDateTime date, int isAll) {
        Integer numericalAt = DateUtils.numericalYyyyMmDd(date);

        List<HouseRankingEntity> list1 = new ArrayList<>(idList.size());
        List<HouseRankingH5Entity> list2 = new ArrayList<>(idList.size());

        // 全量返回
        if(isAll == 1) {
            List<HouseRankingEntity> list = houseRankingDao.findByCityAndHouseTypeAndDateDayAndPeriodType(city, type, numericalAt, PeriodTypeEnum.DAY1.getName());
            list1.addAll(list);
        } else {
            if (CollectionUtils.isNotEmpty(idList)) {
                Lists.partition(idList, 100).forEach(l -> {
                    List<HouseRankingEntity> list = houseRankingDao.findByCityAndHouseIdInAndHouseTypeAndDateDayAndPeriodType(city, l, type, numericalAt, PeriodTypeEnum.DAY1.getName());
                    if (CollectionUtils.isNotEmpty(list)) {
                        list1.addAll(list);
                    }
                });
            }
        }

        // 全量返回
        if(isAll == 1) {
            List<HouseRankingH5Entity> list = houseRankingH5Dao.findByCityAndHouseTypeAndDateDayAndPeriodType(city, type, numericalAt, PeriodTypeEnum.DAY1.getName());
            list2.addAll(list);
        } else {
            if (CollectionUtils.isNotEmpty(idList)) {
                Lists.partition(idList, 100).forEach(l -> {
                    List<HouseRankingH5Entity> list = houseRankingH5Dao.findByCityAndHouseIdInAndHouseTypeAndDateDayAndPeriodType(city, l, type, numericalAt, PeriodTypeEnum.DAY1.getName());
                    if (CollectionUtils.isNotEmpty(list)) {
                        list2.addAll(list);
                    }
                });
            }
        }

        return BaseStatsVoConverter.deserialize1(list1, list2);
    }

    @CachedData(unit= TimeUnit.HOURS, duration = 1)
    public HouseTrendVo trend(HouseTrendCmd houseTrendCmd) {
        HouseRankingEntity result = new HouseRankingEntity().init();
        if (PeriodTypeEnum.DAY1.getName().equalsIgnoreCase(houseTrendCmd.getPeriod())) {
            result = houseRankingDao
                    .findByCityAndHouseIdAndHouseTypeAndDateDayAndPeriodType(houseTrendCmd.getCity(), houseTrendCmd.getHouseId(), houseTrendCmd.getHouseType(), DateUtils.numericalYyyyMmDd(houseTrendCmd.getLocalDateTime()), houseTrendCmd.getPeriod());
        }

        if (PeriodTypeEnum.DAY7.getName().equalsIgnoreCase(houseTrendCmd.getPeriod())) {
            Pair<LocalDateTime, LocalDateTime> pair = DateUtils.getDateDatePairClosed(houseTrendCmd.getLocalDateTime(), 7);
            Integer beginDate = DateUtils.numericalYyyyMmDd(pair.getLeft());
            Integer endDate = DateUtils.numericalYyyyMmDd(pair.getRight());

            result = houseRankingAggregateDao
                    .getByX(houseTrendCmd.getCity(), houseTrendCmd.getHouseId(), houseTrendCmd.getHouseType(), beginDate, endDate);
        }

        if (PeriodTypeEnum.WHOLE.getName().equalsIgnoreCase(houseTrendCmd.getPeriod())) {
            // simple = intelligent
            Pair<LocalDateTime, LocalDateTime> pair = DateUtils.getDateDatePairClosed(houseTrendCmd.getLocalDateTime(), 365);
            Integer beginDate = DateUtils.numericalYyyyMmDd(pair.getLeft());
            Integer endDate = DateUtils.numericalYyyyMmDd(pair.getRight());
            result = houseRankingAggregateDao
                    .getByX(houseTrendCmd.getCity(), houseTrendCmd.getHouseId(), houseTrendCmd.getHouseType(), beginDate, endDate);
        }

        return HouseImplicateVoConverter.deserialize(result);
    }

    @CachedData(unit= TimeUnit.HOURS, duration = 1)
    public List<HouseTrendVo> visitor(VisitorCmd cmd) {
        // 内存分页，待优化
        Pageable page = PageRequest.of(0, 10, Sort.Direction.DESC, "lastOpTime");
        List<CustomerHouseRankingEntity> result = Collections.emptyList();

        PeriodTypeEnum periodTypeEnum = PeriodTypeEnum.find(cmd.getPeriod());

        switch (periodTypeEnum) {
            case DAY1:
                Integer dateDay = DateUtils.numericalYyyyMmDd(cmd.getLocalDateTime());
                result = customerHouseRankingDao2
                        .getBy(cmd.getCity(), cmd.getHouseId(), cmd.getHouseType(), cmd.getPeriod(), dateDay, page);
                break;
            case DAY7:
            case WHOLE:
                result = rangeHouseRanking(cmd, page, periodTypeEnum);
                break;
            default:
                break;
        }

        return HouseImplicateVoConverter.deserialize(result);
    }

    private List<CustomerHouseRankingEntity> rangeHouseRanking(VisitorCmd cmd, Pageable page, PeriodTypeEnum periodTypeEnum) {
        Pair<LocalDateTime, LocalDateTime> pair = DateUtils.getDateDatePairClosed(cmd.getLocalDateTime(), periodTypeEnum.getNumber());
        Integer beginDate = DateUtils.numericalYyyyMmDd(pair.getLeft());
        Integer endDate = DateUtils.numericalYyyyMmDd(pair.getRight());
        return customerHouseRankingAggregateDao
                .getBy(cmd.getCity(), cmd.getHouseId(), cmd.getHouseType(), beginDate, endDate, page);
    }

    public List<BaseStatsVo> houseTemplateCheckStats(String city, List<String> ids, LocalDateTime localDateTime) {
        Integer numericalAt = DateUtils.numericalYyyyMmDd(localDateTime);

        List<HouseTemplateEntity> list1 = new ArrayList<>(ids.size());
        List<HouseTemplateH5Entity> list2 = new ArrayList<>(ids.size());
        Lists.partition(ids, 100).forEach(l -> {
            List<HouseTemplateEntity> list = houseTemplateStatsDao.findByCityAndHouseTemplateIdInAndDateDay(city, l, numericalAt);
            if (CollectionUtils.isNotEmpty(list)) {
                list1.addAll(list);
            }
        });

        Lists.partition(ids, 100).forEach(l -> {
            List<HouseTemplateH5Entity> list = houseTemplateH5StatsDao.findByCityAndHouseTemplateIdInAndDateDay(city, l, numericalAt);
            if (CollectionUtils.isNotEmpty(list)) {
                list2.addAll(list);
            }
        });

        return BaseStatsVoConverter.deserialize5(list1, list2);
    }

    public List<PosterStatsVo> posterTemplateCheckStats(String city, List<String> ids, LocalDateTime localDateTime) {
        Integer date = DateUtils.numericalYyyyMmDd(localDateTime);

        List<PosterStatsVo> vos = new ArrayList<>();

        for(String id: ids){
            PosterTemplateEntity e = posterTemplateStatsDao.findByCityAndPosterIdAndDateDay(city, id, date);
            PosterStatsVo vo = new PosterStatsVo();
            vo.setId(id);
            if(e != null){
                vo.setH5PvAmount(e.getPageView());
                vo.setH5UvAmount(e.getUniqueVisitor());
            } else{
                vo.setH5PvAmount(0L);
                vo.setH5UvAmount(0L);
            }
            vos.add(vo);
        }

        return vos;
    }

    public List<PosterStatsVo> posterCheckStats(String city, List<String> ids, LocalDateTime localDateTime) {
        Integer date = DateUtils.numericalYyyyMmDd(localDateTime);

        List<PosterStatsVo> vos = new ArrayList<>();

        ids.forEach(l -> {
            String infoId;
            String agentId;
            if(l.contains("_")){
                infoId = l.substring(0, l.indexOf("_"));
                agentId = l.substring(infoId.length() + 1);
            }else {
                infoId = l;
                agentId = "";
            }
            PosterEntity e = posterStatsDao.findByCityAndPosterIdAndDateDayAndAgentId(city, infoId, date, agentId);
            PosterStatsVo vo = new PosterStatsVo();
            vo.setId(l);
            if(e != null){
                vo.setH5PvAmount(e.getPageView());
                vo.setH5UvAmount(e.getUniqueVisitor());
            }else{
                vo.setH5PvAmount(0L);
                vo.setH5UvAmount(0L);
            }
            vos.add(vo);
                }
        );

        return vos;
    }
}
