package com.apoem.mmxx.eventtracking.infrastructure.repository;

import com.google.common.collect.Lists;
import com.apoem.mmxx.eventtracking.DateUtils;
import com.apoem.mmxx.eventtracking.domain.analysis.model.vo.BaseStatsVo;
import com.apoem.mmxx.eventtracking.infrastructure.convertor.BaseStatsVoConverter;
import com.apoem.mmxx.eventtracking.infrastructure.dao.mongo.StoreH5StatsDao;
import com.apoem.mmxx.eventtracking.infrastructure.dao.mongo.StoreStatsDao;
import com.apoem.mmxx.eventtracking.infrastructure.po.dm.StoreH5StatsEntity;
import com.apoem.mmxx.eventtracking.infrastructure.po.dm.StoreStatsEntity;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: StoreRepository </p>
 * <p>Description: 门店数仓 </p>
 * <p>Date: 2020/8/26 9:24 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Repository
public class StoreRepository {

    @Autowired
    private StoreStatsDao storeTrendDao;

    @Autowired
    private StoreH5StatsDao storeH5StatsDao;

    public List<BaseStatsVo> checkStats(StoreStatsCmd storeStatsCmd) {
        Integer dateDay = DateUtils.numericalYyyyMmDd(storeStatsCmd.getLocalDateTime());

        List<StoreStatsEntity> list1 = new ArrayList<>();
        Lists.partition(storeStatsCmd.getStoreIds(), 100).forEach(l -> {
            List<StoreStatsEntity> e = storeTrendDao.findByCityAndStoreIdInAndHouseTypeAndDateDay(
                    storeStatsCmd.getCity(), l, storeStatsCmd.getHouseType(), dateDay);
            if (CollectionUtils.isNotEmpty(e)) {
                list1.addAll(e);
            }
        });

        List<StoreH5StatsEntity> list2 = new ArrayList<>();
        Lists.partition(storeStatsCmd.getStoreIds(), 100).forEach(l -> {
            List<StoreH5StatsEntity> e = storeH5StatsDao.findByCityAndStoreIdInAndHouseTypeAndDateDay(
                    storeStatsCmd.getCity(), l, storeStatsCmd.getHouseType(), dateDay);
            if (CollectionUtils.isNotEmpty(e)) {
                list2.addAll(e);
            }
        });
        return BaseStatsVoConverter.deserialize8(list1, list2);
    }
}
