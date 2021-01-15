package com.apoem.mmxx.eventtracking.infrastructure.dao.mongo;

import com.apoem.mmxx.eventtracking.infrastructure.po.dm.HouseRankingEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: AgentCommunityRankingDao </p>
 * <p>Description: 效果分析-经纪人房源访问统计 </p>
 * <p>Date: 2020/8/24 16:59 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Repository
public interface HouseRankingDao extends MongoRepository<HouseRankingEntity, Object> {

    /**
     * 条件查询
     *
     * @param city       城市
     * @param houseId    房源
     * @param houseType  房源
     * @param dateDay    日
     * @param periodType 期间类型
     * @return 实体集合
     */
    HouseRankingEntity findByCityAndHouseIdAndHouseTypeAndDateDayAndPeriodType(String city,
                                                                               String houseId,
                                                                               String houseType,
                                                                               Integer dateDay,
                                                                               String periodType);

    /**
     * 条件查询
     *
     * @param city       城市
     * @param houseId    房源
     * @param houseType  房源
     * @param dateDay    日
     * @param periodType 期间类型
     * @return 实体集合
     */
    List<HouseRankingEntity> findByCityAndHouseIdInAndHouseTypeAndDateDayAndPeriodType(String city,
                                                                                       List<String> houseId,
                                                                                       String houseType,
                                                                                       Integer dateDay,
                                                                                       String periodType);


    List<HouseRankingEntity> findByCityAndHouseTypeAndDateDayAndPeriodType(String city, String houseType, Integer dateDay, String periodType);
}
