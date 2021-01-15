package com.apoem.mmxx.eventtracking.infrastructure.dao.mongo;

import com.apoem.mmxx.eventtracking.infrastructure.po.dm.InfoAgentH5Entity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: InfoAgentH5Dao </p>
 * <p>Description: H5资讯经纪人PV、UV统计 </p>
 * <p>Date: 2020/11/19 9:24 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author somebody
 * @version v1.0
 */
@Repository
public interface InfoAgentH5Dao extends MongoRepository<InfoAgentH5Entity, Object> {

    /**
     * 条件查询
     *
     * @param city 城市
     * @param agentId 经纪人
     * @param dateDay 日期
     * @return 实体
     */
    InfoAgentH5Entity findByCityAndAgentIdAndDateDay(String city, String agentId, Integer dateDay);
}
