package com.apoem.mmxx.eventtracking.infrastructure.dao.mongo;

import com.google.common.base.CaseFormat;
import com.apoem.mmxx.eventtracking.BaseConstants;
import com.apoem.mmxx.eventtracking.infrastructure.po.dm.CustomerHouseRankingEntity;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

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
public class CustomerHouseRankingDao2 {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public CustomerHouseRankingDao2(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public List<CustomerHouseRankingEntity> getBy(String city, String houseId, String houseType,
                                                  String periodType, Integer dateDay,
                                                  Pageable page) {
        String[] sortFields = page.getSort().get()
                .map(Sort.Order::getProperty)
                .map(o -> CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, o))
                .toArray(String[]::new);
        Query query = new Query(Criteria
                .where("city").is(city)
                .and("houseId").is(houseId)
                .and("customer_id").ne(BaseConstants.BLANK)
                .and("date_day").is(dateDay)
                .and("house_type").is(houseType)
                .and("period_type").is(periodType)
        ).skip(page.getOffset()).limit(page.getPageSize()).with(new Sort(Sort.Direction.DESC, sortFields));
        return mongoTemplate.find(query, CustomerHouseRankingEntity.class);
    }

    public UpdateResult insert(CustomerHouseRankingEntity entity) {
        Query query = new Query(
                Criteria.where("date_day").is(entity.getDateDay())
                        .and("period_type").is(entity.getPeriodType())
                        .and("customer_id").is(entity.getCustomerId())
                        .and("city").is(entity.getCity())
                        .and("house_id").is(entity.getHouseId())
                        .and("house_type").is(entity.getHouseType())
        );

        Update update = new Update();
        update.setOnInsert("date_day", entity.getDateDay());
        update.setOnInsert("house_name", entity.getHouseName());
        update.setOnInsert("period_type", entity.getPeriodType());
        update.setOnInsert("customer_id", entity.getCustomerId());
        update.setOnInsert("house_area", entity.getHouseArea());
        update.setOnInsert("city", entity.getCity());
        update.setOnInsert("house_id", entity.getHouseId());
        update.setOnInsert("house_type", entity.getHouseType());
        update.setOnInsert("house_total_price", entity.getHouseTotalPrice());
        update.setOnInsert("house_average_price", entity.getHouseAveragePrice());
        update.setOnInsert("house_area_lower", entity.getHouseAreaLower());
        update.setOnInsert("house_area_upper", entity.getHouseAreaUpper());
        update.setOnInsert("house_living_room", entity.getHouseLivingRoom());
        update.setOnInsert("house_bedroom", entity.getHouseBedroom());
        update.setOnInsert("house_bathroom", entity.getHouseBathroom());
        update.setOnInsert("community_id", entity.getCommunityId());
        update.setOnInsert("community_name", entity.getCommunityName());
        update.setOnInsert("plate_id", entity.getPlateId());
        update.setOnInsert("plate_name", entity.getPlateName());
        update.setOnInsert("agent_id", entity.getAgentId());
        update.setOnInsert("store_id", entity.getStoreId());
        update.setOnInsert("avg_price_range", entity.getAvgPriceRange());
        update.setOnInsert("avg_price_range_order", entity.getAvgPriceRangeOrder());
        update.setOnInsert("total_price_range", entity.getTotalPriceRange());
        update.setOnInsert("total_price_range_order", entity.getTotalPriceRangeOrder());
        update.setOnInsert("area_range", entity.getAreaRange());
        update.setOnInsert("area_range_order", entity.getAreaRangeOrder());
        update.setOnInsert("layout_range", entity.getLayoutRange());
        update.setOnInsert("layout_range_order", entity.getLayoutRangeOrder());
        update.setOnInsert("day_of_week", entity.getDayOfWeek());
        update.setOnInsert("hour_view.day_of_week", entity.getHourView().getDayOfWeek());
        Date date = new Date();
        update.setOnInsert("create_time", date);
        update.set("update_time", date);

        update.inc("hour_view.h0", entity.getHourView().getH0());
        update.inc("hour_view.h1", entity.getHourView().getH1());
        update.inc("hour_view.h2", entity.getHourView().getH2());
        update.inc("hour_view.h3", entity.getHourView().getH3());
        update.inc("hour_view.h4", entity.getHourView().getH4());
        update.inc("hour_view.h5", entity.getHourView().getH5());
        update.inc("hour_view.h6", entity.getHourView().getH6());
        update.inc("hour_view.h7", entity.getHourView().getH7());
        update.inc("hour_view.h8", entity.getHourView().getH8());
        update.inc("hour_view.h9", entity.getHourView().getH9());
        update.inc("hour_view.h10", entity.getHourView().getH10());
        update.inc("hour_view.h11", entity.getHourView().getH11());
        update.inc("hour_view.h12", entity.getHourView().getH12());
        update.inc("hour_view.h13", entity.getHourView().getH13());
        update.inc("hour_view.h14", entity.getHourView().getH14());
        update.inc("hour_view.h15", entity.getHourView().getH15());
        update.inc("hour_view.h16", entity.getHourView().getH16());
        update.inc("hour_view.h17", entity.getHourView().getH17());
        update.inc("hour_view.h18", entity.getHourView().getH18());
        update.inc("hour_view.h19", entity.getHourView().getH19());
        update.inc("hour_view.h20", entity.getHourView().getH20());
        update.inc("hour_view.h21", entity.getHourView().getH21());
        update.inc("hour_view.h22", entity.getHourView().getH22());
        update.inc("hour_view.h23", entity.getHourView().getH23());
        update.max("last_op_time", entity.getLastOpTime());
        update.inc("page_view", entity.getPageView());
        // 第一次触发记录uv为1
        update.setOnInsert("unique_visitor", entity.getUniqueVisitor());
        update.inc("collected", entity.getCollected());
        update.inc("reposted", entity.getReposted());

        return mongoTemplate.upsert(query, update, CustomerHouseRankingEntity.class);
    }
}
