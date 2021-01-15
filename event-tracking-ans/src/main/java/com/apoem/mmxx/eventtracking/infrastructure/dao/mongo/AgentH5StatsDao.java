package com.apoem.mmxx.eventtracking.infrastructure.dao.mongo;

import com.apoem.mmxx.eventtracking.infrastructure.po.dm.AgentH5StatsEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: AgentStatsDao </p>
 * <p>Description: 运营后台-经纪人统计-H5 </p>
 * <p>Date: 2020/8/26 9:29 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Repository
public interface AgentH5StatsDao extends MongoRepository<AgentH5StatsEntity, Object> {


    /**
     * 条件查询
     *
     * @param city 城市
     * @param agentId 经纪人
     * @param houseType 经纪人
     * @param dateDay 日
     * @return 实体
     */
    List<AgentH5StatsEntity> findByCityAndAgentIdInAndHouseTypeAndDateDay(String city, List<String> agentId,
                                                                          String houseType, Integer dateDay);
}
