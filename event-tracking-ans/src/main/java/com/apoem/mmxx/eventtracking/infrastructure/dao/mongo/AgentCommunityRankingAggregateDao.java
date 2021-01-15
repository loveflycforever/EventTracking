package com.apoem.mmxx.eventtracking.infrastructure.dao.mongo;

import com.google.common.base.CaseFormat;
import com.apoem.mmxx.eventtracking.infrastructure.po.dm.AgentCommunityRankingEntity;
import com.apoem.mmxx.eventtracking.infrastructure.dao.support.WonderfulPage;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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
public class AgentCommunityRankingAggregateDao {


    private final MongoTemplate mongoTemplate;

    @Autowired
    public AgentCommunityRankingAggregateDao(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public List<AgentCommunityRankingEntity> getByHouseType(String city, String agentId, String houseType,
                                                            Integer beginDateDay, Integer endDateDay, WonderfulPage page) {
        String field = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, page.getFieldEnum().getField());

        Criteria query = Criteria.where("agent_id").is(agentId)
                .and("city").is(city)
                .and("house_type").is(houseType)
                .and(field).ne(0)
                .and("date_day").gte(beginDateDay).lte(endDateDay);
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(query),
                Aggregation.group("communityId", "houseType", "city")

                        .last("dateDay").as("date_day")
                        .last("city").as("city")
                        .last("agentId").as("agent_id")
                        .last("communityId").as("community_id")
                        .last("communityName").as("community_name")
                        .last("periodType").as("period_type")
                        .last("houseType").as("house_type")

                        .sum("pageView").as("page_view")
                        .sum("uniqueVisitor").as("unique_visitor")
                        .sum("collected").as("collected")
                        .sum("reposted").as("reposted")
                        .sum("totalPageView").as("total_page_view")
                        .sum("totalUniqueVisitor").as("total_unique_visitor")
                        .sum("totalCollected").as("total_collected")
                        .sum("totalReposted").as("total_reposted"),
                Aggregation.sort(Sort.Direction.DESC, field),
                Aggregation.skip(page.getOffset()),
                Aggregation.limit(page.getPageSize()));

        return mongoTemplate.aggregate(
                aggregation,
                AgentCommunityRankingEntity.class,
                AgentCommunityRankingEntity.class)
                .getMappedResults();
    }

    public Optional<AgentCommunityRankingEntity> getByHouseTypeTotal(String city, String agentId, String houseType,
                                                                     Integer beginDateDay, Integer endDateDay, WonderfulPage page) {
        String field = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, page.getFieldEnum().getField());

        Criteria query = Criteria.where("agent_id").is(agentId)
                .and("city").is(city)
                .and("house_type").is(houseType)
                .and(field).ne(0)
                .and("date_day").gte(beginDateDay).lte(endDateDay);

        Aggregation aggregation2 = Aggregation.newAggregation(
                Aggregation.match(query),
                Aggregation.group("houseType", "city")

                        .last("dateDay").as("date_day")
                        .last("city").as("city")
                        .last("agentId").as("agent_id")
                        .last("communityId").as("community_id")
                        .last("communityName").as("community_name")
                        .last("periodType").as("period_type")
                        .last("houseType").as("house_type")

                        .sum("pageView").as("page_view")
                        .sum("uniqueVisitor").as("unique_visitor")
                        .sum("collected").as("collected")
                        .sum("reposted").as("reposted")
                        .sum("totalPageView").as("total_page_view")
                        .sum("totalUniqueVisitor").as("total_unique_visitor")
                        .sum("totalCollected").as("total_collected")
                        .sum("totalReposted").as("total_reposted"),
                Aggregation.sort(Sort.Direction.DESC, field),
                Aggregation.skip(page.getOffset()),
                Aggregation.limit(page.getPageSize()));
        List<AgentCommunityRankingEntity> mappedResults = mongoTemplate.aggregate(
                aggregation2,
                AgentCommunityRankingEntity.class,
                AgentCommunityRankingEntity.class)
                .getMappedResults();

        if (CollectionUtils.isEmpty(mappedResults)) {
            return Optional.empty();
        }

        return Optional.ofNullable(mappedResults.get(0));
    }
}
