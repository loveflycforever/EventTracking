package com.apoem.mmxx.eventtracking.infrastructure.dao.mongo;

import com.apoem.mmxx.eventtracking.infrastructure.po.dm.CustomerTrailEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: CustomerTrailDao2 </p>
 * <p>Description: 客户轨迹2 </p>
 * <p>Date: 2020/8/27 17:41 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Repository
public class CustomerTrailDao2 {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public CustomerTrailDao2(MongoTemplate mongoTemplate) {
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
    public List<CustomerTrailEntity> findHouseTrail(String city,
                                                    String houseId,
                                                    String houseType,
                                                    Long idKey,
                                                    Object... customerId) {
        Query query = new Query(Criteria
                .where("city").is(city)
                .and("house_type").is(houseType)
                .and("customer_id").in(customerId)
                .and("house_id").is(houseId)
                .and("id_key").lt(idKey)
        ).limit(100).with(new Sort(Sort.Direction.DESC, "id_key"));
        return mongoTemplate
                .find(query, CustomerTrailEntity.class);
    }


    public List<CustomerTrailEntity> findTrailWhole(String city,
                                                    String agentId,
                                                    Long idKey,
                                                    Object... customerId) {
        Query query = new Query(Criteria
                .where("city").is(city)
                .and("customer_id").in(customerId)
                .and("agentId").is(agentId)
                .and("id_key").lt(idKey)
        ).limit(100).with(new Sort(Sort.Direction.DESC, "id_key"));
        return mongoTemplate
                .find(query, CustomerTrailEntity.class);
    }

    public List<CustomerTrailEntity> findTrail(String city,
                                               String actionType,
                                               String agentId,
                                               Long idKey,
                                               Object... customerId) {
        Query query = new Query(Criteria
                .where("city").is(city)
                .and("action_type").is(actionType)
                .and("customer_id").in(customerId)
                .and("agent_id").is(agentId)
                .and("id_key").lt(idKey)
        ).limit(100).with(new Sort(Sort.Direction.DESC, "id_key"));
        return mongoTemplate
                .find(query, CustomerTrailEntity.class);
    }

    public List<CustomerTrailEntity> ddd(String city, String actionDefinition, String houseId,
                                         String houseType, Long idKey, Object... customerId) {
        Query query = new Query(Criteria
                .where("city").is(city)
                .and("action_definition").is(actionDefinition)
                .and("house_type").is(houseType)
                .and("customer_id").in(customerId)
                .and("house_id").is(houseId)
                .and("id_key").lte(idKey)
        ).limit(1).with(new Sort(Sort.Direction.DESC, "id_key"));
        return mongoTemplate
                .find(query, CustomerTrailEntity.class);
    }
}
