package com.apoem.mmxx.eventtracking.infrastructure.dao.mongo;

import com.apoem.mmxx.eventtracking.infrastructure.po.dm.InfoH5Entity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: InfoH5Dao </p>
 * <p>Description: H5资讯PV、UV统计 </p>
 * <p>Date: 2020/11/18 14:24 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author somebody
 * @version v1.0
 */
@Repository
public interface InfoH5Dao extends MongoRepository<InfoH5Entity, Object> {

    /**
     * 条件查询
     *
     * @param city 城市
     * @param informationId 资讯
     * @param agentId 经纪人
     * @param dateDay 日期
     * @return 实体
     */
    InfoH5Entity findByCityAndInformationIdAndAgentIdAndDateDay(String city, String informationId, String agentId, Integer dateDay);
}
