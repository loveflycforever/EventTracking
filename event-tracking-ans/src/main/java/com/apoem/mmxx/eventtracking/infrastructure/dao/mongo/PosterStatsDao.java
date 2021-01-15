package com.apoem.mmxx.eventtracking.infrastructure.dao.mongo;

import com.apoem.mmxx.eventtracking.infrastructure.po.dm.PosterEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: PosterStatsDao </p>
 * <p>Description: H5海报PV、UV统计  </p>
 * <p>Date: 2020/11/17 16:04 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author somebody
 * @version v1.0
 */
@Repository
public interface PosterStatsDao extends MongoRepository<PosterEntity, Object> {

    /**
     * 条件查询
     *
     * @param city 城市
     * @param posterId 营销海报
     * @param dateDay 日期
     * @param agentId 经纪人
     * @return 实体
     */
    PosterEntity findByCityAndPosterIdAndDateDayAndAgentId(String city, String posterId, Integer dateDay, String agentId);
}
