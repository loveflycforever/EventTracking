package com.apoem.mmxx.eventtracking.infrastructure.dao.mongo;

import com.google.common.base.CaseFormat;
import com.apoem.mmxx.eventtracking.infrastructure.enums.FieldEnum;
import com.apoem.mmxx.eventtracking.infrastructure.po.dm.HouseRankingEntity;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.query.Criteria;
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
public class HouseRankingAggregateDao {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public HouseRankingAggregateDao(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public List<HouseRankingEntity> getByHouseType(String city, String agentId, String communityId, String houseType,
                                                   Integer beginDateDay, Integer endDateDay,
                                                   FieldEnum fieldEnum) {
        String field = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, fieldEnum.getField());

        Criteria query = Criteria.where("agent_id").is(agentId)
                .and("city").is(city)
                .and("house_type").is(houseType)
                .and("community_id").is(communityId)
                .and("date_day").gte(beginDateDay).lte(endDateDay);
        return getHouseRankingEntities(query, field);
    }

    public List<HouseRankingEntity> getWhole(String city, String agentId, String communityId,
                                             Integer beginDateDay, Integer endDateDay,
                                             FieldEnum fieldEnum) {
        String field = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, fieldEnum.getField());

        Criteria query = Criteria.where("agent_id").is(agentId)
                .and("city").is(city)
                .and("community_id").is(communityId)
                .and("date_day").gte(beginDateDay).lte(endDateDay);
        return getHouseRankingEntities(query, field);
    }

    private List<HouseRankingEntity> getHouseRankingEntities(Criteria query, String... properties) {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(query), getAs(),
                Aggregation.sort(Sort.Direction.DESC, properties));
        return mongoTemplate.aggregate(
                aggregation,
                HouseRankingEntity.class,
                HouseRankingEntity.class)
                .getMappedResults();
    }

    public HouseRankingEntity getByX(String city, String houseId, String houseType,
                                     Integer beginDateDay, Integer endDateDay) {
        Criteria query = Criteria.where("house_id").is(houseId)
                .and("house_type").is(houseType)
                .and("city").is(city)
                .and("date_day").gte(beginDateDay).lte(endDateDay);
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(query), getAs());
        List<HouseRankingEntity> mappedResults = mongoTemplate.aggregate(
                aggregation,
                HouseRankingEntity.class,
                HouseRankingEntity.class)
                .getMappedResults();
        return CollectionUtils.isNotEmpty(mappedResults) ? mappedResults.get(0) : null;
    }

    private GroupOperation getAs() {
        // 维度-房源、房源类型
        return Aggregation.group("houseId", "houseType", "city")
                .last("dateDay").as("date_day")
                .last("periodType").as("period_type")

                .last("city").as("city")
                .last("houseType").as("house_type")
                .last("houseId").as("house_id")

                .last("houseName").as("house_name")
                .last("houseArea").as("house_area")
                .last("houseTotalPrice").as("house_total_price")
                .last("houseAveragePrice").as("house_average_price")
                .last("houseAreaLower").as("house_area_lower")
                .last("houseAreaUpper").as("house_area_upper")
                .last("houseLivingRoom").as("house_living_room")
                .last("houseBedroom").as("house_bedroom")
                .last("houseBathroom").as("house_bathroom")
                .last("communityId").as("community_id")
                .last("communityName").as("community_name")
                .last("plateId").as("plate_id")
                .last("plateName").as("plate_name")
                .last("agentId").as("agent_id")
                .last("storeId").as("store_id")

                .sum("pageView").as("page_view")
                .sum("uniqueVisitor").as("unique_visitor")
                .sum("collected").as("collected")
                .sum("reposted").as("reposted");
    }
}
