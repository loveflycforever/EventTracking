package com.apoem.mmxx.eventtracking.infrastructure.dao.mongo;

import com.apoem.mmxx.eventtracking.infrastructure.po.dm.HouseTemplateEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: 运营后台-资讯访问量、访客数、收藏数统计  </p>
 * <p>Description:  </p>
 * <p>Date: 2020/8/26 9:29 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Repository
public interface HouseTemplateStatsDao extends MongoRepository<HouseTemplateEntity, Object> {

    /**
     * 条件查询
     *
     * @param city 城市
     * @param houseTemplateId 资讯
     * @param dateDay 日
     * @return 实体
     */
    List<HouseTemplateEntity> findByCityAndHouseTemplateIdInAndDateDay(String city, List<String> houseTemplateId, Integer dateDay);
}
