package com.apoem.mmxx.eventtracking.infrastructure.dao.mongo;

import com.google.common.base.CaseFormat;
import com.apoem.mmxx.eventtracking.infrastructure.po.dm.AgentCommunityRankingEntity;
import com.apoem.mmxx.eventtracking.infrastructure.dao.support.WonderfulPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: AgentCommunityRankingDao </p>
 * <p>Description: 效果分析-经纪人小区访问统计 </p>
 * <p>Date: 2020/8/24 16:59 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Repository
public class AgentCommunityRankingDao {


    private final MongoTemplate mongoTemplate;

    @Autowired
    public AgentCommunityRankingDao(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }


    public List<AgentCommunityRankingEntity> findByHouseType(String city,
                                                             String agentId,
                                                             Integer dateDay,
                                                             String houseType,
                                                             String periodType,
                                                             WonderfulPage page) {
        String field = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, page.getFieldEnum().getField());
        // myself wonderful
        Query query = new Query(Criteria
                .where("city").is(city)
                .and("agent_id").is(agentId)
                .and("date_day").is(dateDay)
                .and("house_type").is(houseType)
                .and("period_type").is(periodType)
                .and(field).ne(0)
        ).skip(page.getOffset()).limit(page.getPageSize()).with(new Sort(Sort.Direction.DESC, field));
        return mongoTemplate.find(query, AgentCommunityRankingEntity.class);
    }
}
