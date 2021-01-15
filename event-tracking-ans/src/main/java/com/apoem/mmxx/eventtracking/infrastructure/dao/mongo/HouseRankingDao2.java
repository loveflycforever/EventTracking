package com.apoem.mmxx.eventtracking.infrastructure.dao.mongo;

import com.google.common.base.CaseFormat;
import com.apoem.mmxx.eventtracking.infrastructure.enums.FieldEnum;
import com.apoem.mmxx.eventtracking.infrastructure.po.dm.HouseRankingEntity;
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
 * <p>Description: 效果分析-经纪人房源访问统计 </p>
 * <p>Date: 2020/8/24 16:59 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Repository
public class HouseRankingDao2 {
    private final MongoTemplate mongoTemplate;

    @Autowired
    public HouseRankingDao2(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    /**
     * 条件查询
     *
     * @param city       城市
     * @param customerId 客源
     * @param houseId    房源
     * @param houseType  房源类型
     * @param idKey      关键值
     * @return 实体
     */
    public List<HouseRankingEntity> findHouseTrail(String city,
                                                   String customerId,
                                                   String houseId,
                                                   String houseType,
                                                   Long idKey) {
        Query query = new Query(Criteria
                .where("city").is(city)
                .and("house_type").is(houseType)
                .and("customer_id").is(customerId)
                .and("house_id").is(houseId)
                .and("id_key").lte(idKey)
        ).limit(100).with(new Sort(Sort.Direction.DESC, "id_key"));
        return mongoTemplate
                .find(query, HouseRankingEntity.class);
    }

    public List<HouseRankingEntity> findWhole(String city,
                                              String agentId,
                                              String communityId,
                                              Integer dateDay,
                                              String periodType,
                                              FieldEnum fieldEnum) {

        String field = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, fieldEnum.getField());

        Query query = new Query(Criteria
                .where("city").is(city)
                .and("agent_id").is(agentId)
                .and("community_id").is(communityId)
                .and("date_day").is(dateDay)
                .and("period_type").is(periodType)).with(new Sort(Sort.Direction.DESC, field));
        return mongoTemplate.find(query, HouseRankingEntity.class);
    }

    public List<HouseRankingEntity> findByHouseType(String city,
                                                    String agentId,
                                                    String communityId,
                                                    Integer dateDay,
                                                    String houseType,
                                                    String periodType,
                                                    FieldEnum fieldEnum) {
        String field = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, fieldEnum.getField());

        Query query = new Query(
                Criteria.where("city").is(city)
                        .and("agent_id").is(agentId)
                        .and("community_id").is(communityId)
                        .and("date_day").is(dateDay)
                        .and("house_type").is(houseType)
                        .and("period_type").is(periodType))
                .with(new Sort(Sort.Direction.DESC, field));
        return mongoTemplate
                .find(query, HouseRankingEntity.class);
    }
}
