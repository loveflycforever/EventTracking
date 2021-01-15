package com.apoem.mmxx.eventtracking.infrastructure.dao.mongo;

import com.google.common.base.CaseFormat;
import com.apoem.mmxx.eventtracking.infrastructure.enums.FieldEnum;
import com.apoem.mmxx.eventtracking.infrastructure.po.support.HourView;
import com.apoem.mmxx.eventtracking.BaseConstants;
import com.apoem.mmxx.eventtracking.infrastructure.po.dm.CustomerHouseRankingEntity;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: CustomerHouseRankingDao </p>
 * <p>Description: 客源分析-房源统计 </p>
 * <p>Date: 2020/8/27 10:23 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Repository
public class CustomerHouseRankingAggregateDao {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public CustomerHouseRankingAggregateDao(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public List<CustomerHouseRankingEntity> getBy(String city,
                                                  String houseId,
                                                  String houseType,
                                                  Integer beginDate,
                                                  Integer endDate,
                                                  Pageable page) {

        String[] groupFields = {"customerId", "city", "houseId", "houseType"};
        String[] sortFields = page.getSort().get()
                .map(Sort.Order::getProperty)
                .map(o -> CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, o))
                .toArray(String[]::new);

        MatchOperation match = Aggregation.match(Criteria
                .where("city").is(city)
                .and("customer_id").ne(BaseConstants.BLANK)
                .and("house_id").is(houseId)
                .and("house_type").is(houseType)
                .and("date_day").gte(beginDate).lte(endDate));
        GroupOperation group = getGroupOperation(groupFields);
        SortOperation sort = Aggregation.sort(Sort.Direction.DESC, sortFields);
        LimitOperation limit = Aggregation.limit(10);
        return getCustomerHouseRankingEntities(match, group, sort, limit);
    }

    private List<CustomerHouseRankingEntity> getCustomerHouseRankingEntities(MatchOperation match, GroupOperation group) {
        return getCustomerHouseRankingEntities(match, group, null, null);
    }

    private List<CustomerHouseRankingEntity> getCustomerHouseRankingEntities(MatchOperation match, GroupOperation group, SortOperation sort) {
        return getCustomerHouseRankingEntities(match, group, sort, null);
    }

    private List<CustomerHouseRankingEntity> getCustomerHouseRankingEntities(MatchOperation match, GroupOperation group, SortOperation sort, LimitOperation limit) {
        AggregationOperation[] aggregationOperations = Stream.of(match, group, sort, limit).filter(Objects::nonNull).toArray(AggregationOperation[]::new);
        Aggregation aggregation = Aggregation.newAggregation(aggregationOperations);
        return mongoTemplate.aggregate(aggregation, CustomerHouseRankingEntity.class, CustomerHouseRankingEntity.class).getMappedResults();
    }

    private GroupOperation getGroupOperation(String[] groupFields) {
        return Aggregation.group(groupFields)
                .last("dateDay").as("date_day")
                .last("periodType").as("period_type")

                .last("customerId").as("customer_id")
                .last("city").as("city")
                .last("houseId").as("house_id")
                .last("houseType").as("house_type")

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

                .max("lastOpTime").as("last_op_time")

                .sum("pageView").as("page_view")
                .sum("uniqueVisitor").as("unique_visitor")
                .sum("collected").as("collected")
                .sum("reposted").as("reposted");
    }


    public List<CustomerHouseRankingEntity> mostViewHouse(CustomerHouseRankingAgQuery query) {
        String[] groupFields = {"customerId", "city", "houseId", "houseType"};
        String[] sortFields = {"page_view", "last_op_time"};
        MatchOperation match = Aggregation.match(
                Criteria
                        .where("customer_id").is(query.getCustomerId())
                        .and("agent_id").is(query.getAgentId())
                        .and("city").is(query.getCity())
                        .and("date_day").gte(query.getBeginDateDay()).lte(query.getEndDateDay())
        );
        GroupOperation group = getGroupOperation(groupFields);
        SortOperation sort = Aggregation.sort(Sort.Direction.DESC, sortFields);
        return getCustomerHouseRankingEntities(match, group, sort);
    }

    public List<CustomerHouseRankingEntity> mostViewHouseTotalPrice(CustomerHouseRankingAgQuery query) {
        String[] groupFields = {"customerId", "city", "houseTotalPrice", "houseType"};
        String[] sortFields = {"house_total_price"};
        MatchOperation match = Aggregation.match(
                Criteria
                        .where("customer_id").is(query.getCustomerId())
                        .and("agent_id").is(query.getAgentId())
                        .and("city").is(query.getCity())
                        .and("date_day").gte(query.getBeginDateDay()).lte(query.getEndDateDay())
        );
        GroupOperation group = getGroupOperation(groupFields);
        SortOperation sort = Aggregation.sort(Sort.Direction.DESC, sortFields);
        return getCustomerHouseRankingEntities(match, group, sort);
    }

    public List<CustomerHouseRankingEntity> mostViewHouseLayout(CustomerHouseRankingAgQuery query) {
        String[] groupFields = {"customerId", "city", "houseBedroom", "houseLivingRoom", "houseBathroom", "houseType"};
        String[] sortFields = {"page_view", "last_op_time"};
        MatchOperation match = Aggregation.match(
                Criteria.where("customer_id").is(query.getCustomerId())
                        .and("agent_id").is(query.getAgentId())
                        .and("city").is(query.getCity())
                        .and("date_day").gte(query.getBeginDateDay()).lte(query.getEndDateDay())
        );
        GroupOperation group = getGroupOperation(groupFields);
        SortOperation sort = Aggregation.sort(Sort.Direction.DESC, sortFields);
        return getCustomerHouseRankingEntities(match, group, sort);
    }

    public List<CustomerHouseRankingEntity> mostViewHouseArea(CustomerHouseRankingAgQuery query) {
        String[] groupFields = {"customerId", "city", "houseArea", "houseType"};
        String[] sortFields = {"house_area"};
        MatchOperation match = Aggregation.match(
                Criteria
                        .where("customer_id").is(query.getCustomerId())
                        .and("agent_id").is(query.getAgentId())
                        .and("city").is(query.getCity())
                        .and("date_day").gte(query.getBeginDateDay()).lte(query.getEndDateDay())
        );
        GroupOperation group = getGroupOperation(groupFields);
        SortOperation sort = Aggregation.sort(Sort.Direction.DESC, sortFields);
        return getCustomerHouseRankingEntities(match, group, sort);
    }

    public List<CustomerHouseRankingEntity> rangeHouseArea(CustomerRangeAgQuery query) {
        String[] groupFields = {"customerId", "city", "houseArea", "houseType"};
        MatchOperation match = Aggregation.match(
                Criteria
                        .where("customer_id").is(query.getCustomerId())
                        .and("agent_id").is(query.getAgentId())
                        .and("city").is(query.getCity())
                        .and("house_type").is(query.getHouseType())
                        .and("date_day").gte(query.getBeginDateDay()).lte(query.getEndDateDay())
        );
        GroupOperation group = getGroupOperation(groupFields);
        return getCustomerHouseRankingEntities(match, group);
    }

    public List<CustomerHouseRankingEntity> rangeHouseTotalMoney(CustomerRangeAgQuery query) {
        String[] groupFields = {"customerId", "city", "houseTotalPrice", "houseType"};
        MatchOperation match = Aggregation.match(
                Criteria
                        .where("customer_id").is(query.getCustomerId())
                        .and("agent_id").is(query.getAgentId())
                        .and("city").is(query.getCity())
                        .and("house_type").is(query.getHouseType())
                        .and("date_day").gte(query.getBeginDateDay()).lte(query.getEndDateDay())
        );
        GroupOperation group = getGroupOperation(groupFields);
        return getCustomerHouseRankingEntities(match, group);
    }

    public List<HourView> rangeHouseViewHour(CustomerRangeAgQuery query) {
        String[] groupFields = {"dayOfWeek"};
        MatchOperation match = Aggregation.match(
                Criteria.where("agent_id").is(query.getAgentId())
                        .and("city").is(query.getCity())
                        .and("house_type").is(query.getHouseType())
                        .and("customer_id").is(query.getCustomerId())
                        .and("date_day").gte(query.getBeginDateDay()).lte(query.getEndDateDay()));
        GroupOperation group = Aggregation.group(groupFields)
                .last("hourView.dayOfWeek").as("day_of_week")
                .sum("hourView.h0").as("h0")
                .sum("hourView.h1").as("h1")
                .sum("hourView.h2").as("h2")
                .sum("hourView.h3").as("h3")
                .sum("hourView.h4").as("h4")
                .sum("hourView.h5").as("h5")
                .sum("hourView.h6").as("h6")
                .sum("hourView.h7").as("h7")
                .sum("hourView.h8").as("h8")
                .sum("hourView.h9").as("h9")
                .sum("hourView.h10").as("h10")
                .sum("hourView.h11").as("h11")
                .sum("hourView.h12").as("h12")
                .sum("hourView.h13").as("h13")
                .sum("hourView.h14").as("h14")
                .sum("hourView.h15").as("h15")
                .sum("hourView.h16").as("h16")
                .sum("hourView.h17").as("h17")
                .sum("hourView.h18").as("h18")
                .sum("hourView.h19").as("h19")
                .sum("hourView.h20").as("h20")
                .sum("hourView.h21").as("h21")
                .sum("hourView.h22").as("h22")
                .sum("hourView.h23").as("h23");
        Aggregation aggregation = Aggregation.newAggregation(match, group);
        return mongoTemplate.aggregate(aggregation, CustomerHouseRankingEntity.class, HourView.class).getMappedResults();
    }

    public List<CustomerHouseRankingEntity> getByHouseType(String city, String customerId, String agentId, String communityId, String houseType,
                                                           Integer beginDateDay, Integer endDateDay, FieldEnum fieldEnum) {
        String[] groupFields = {"customerId", "city", "houseId", "houseType"};

        String field = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, fieldEnum.getField());

        MatchOperation match = Aggregation.match(
                Criteria.where("agent_id").is(agentId)
                        .and("city").is(city)
                        .and("customer_id").is(customerId)
                        .and("house_type").is(houseType)
                        .and("community_id").is(communityId)
                        .and("date_day").gte(beginDateDay).lte(endDateDay));
        GroupOperation group = getGroupOperation(groupFields);
        SortOperation sort = Aggregation.sort(Sort.Direction.DESC, field);
        return getCustomerHouseRankingEntities(match, group, sort);

    }

    public Optional<CustomerHouseRankingEntity> getCommunityTypeWhole(String city, String customerId, String agentId,
                                                                      String houseType, Integer beginDate, Integer endDate) {
        Criteria query = Criteria
                .where("agent_id").is(agentId)
                .and("city").is(city)
                .and("customer_id").is(customerId)
                .and("house_type").is(houseType)
                .and("date_day").gte(beginDate).lte(endDate);
        String[] groupFields = {"city"};
        MatchOperation match = Aggregation.match(query);
        GroupOperation group = getGroupOperation(groupFields);
        Aggregation aggregation = Aggregation.newAggregation(match, group);
        List<CustomerHouseRankingEntity> results = mongoTemplate.aggregate(
                aggregation,
                CustomerHouseRankingEntity.class,
                CustomerHouseRankingEntity.class)
                .getMappedResults();

        if (CollectionUtils.isEmpty(results)) {
            return Optional.empty();
        }
        return Optional.ofNullable(results.get(0));
    }

    public List<CustomerHouseRankingEntity> getCommunityTypePage(String city, String customerId, String agentId,
                                                                 String houseType, Integer beginDate, Integer endDate,
                                                                 Pageable page) {

        String field = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, FieldEnum.PVA.getField());

        // myself wonderful
        Criteria query = Criteria
                .where("agent_id").is(agentId)
                .and("city").is(city)
                .and("customer_id").is(customerId)
                .and("house_type").is(houseType)
                .and(field).ne(0)
                .and("date_day").gte(beginDate).lte(endDate);
        String[] groupFields = {"communityId", "houseType"};
        MatchOperation match = Aggregation.match(query);
        GroupOperation group = getGroupOperation(groupFields);
        SortOperation sort = Aggregation.sort(Sort.Direction.DESC, field);
        SkipOperation skip = Aggregation.skip(page.getOffset());
        LimitOperation limit = Aggregation.limit(page.getPageSize());
        Aggregation aggregation = Aggregation.newAggregation(match, group, sort, skip, limit);
        return mongoTemplate.aggregate(
                aggregation,
                CustomerHouseRankingEntity.class,
                CustomerHouseRankingEntity.class)
                .getMappedResults();
    }
}
