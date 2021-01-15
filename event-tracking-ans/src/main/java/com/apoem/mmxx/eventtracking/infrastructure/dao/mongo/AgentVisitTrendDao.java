package com.apoem.mmxx.eventtracking.infrastructure.dao.mongo;

import com.apoem.mmxx.eventtracking.infrastructure.po.dm.AgentVisitTrendEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: AgentVisitTrendDao </p>
 * <p>Description: 效效果分析-经纪人日趋势 </p>
 * <p>Date: 2020/8/12 16:57 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
public interface AgentVisitTrendDao extends MongoRepository<AgentVisitTrendEntity, Object> {

    /**
     * 条件查询
     *
     * @param city      城市
     * @param agentId   经纪人
     * @param houseType 类型
     * @param page 分页
     * @return 实体集合
     */
    List<AgentVisitTrendEntity> findByCityAndAgentIdAndHouseType(String city,
                                                                 String agentId,
                                                                 String houseType,
                                                                 Pageable page);


    List<AgentVisitTrendEntity> findByCityAndAgentIdInAndHouseTypeAndDateDay(String city, List<String> agentId, String houseType, Integer dateDay);
}
