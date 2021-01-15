package com.apoem.mmxx.eventtracking.infrastructure.dao.mongo;

import com.google.gson.Gson;
import com.apoem.mmxx.eventtracking.application.event.EtlConsumerListener;
import com.apoem.mmxx.eventtracking.infrastructure.enums.AvenueEnum;
import com.apoem.mmxx.eventtracking.infrastructure.enums.RangeGlanceTimeLegendEnum;
import com.apoem.mmxx.eventtracking.domain.acquisition.model.aggregates.ContentInfo;
import com.apoem.mmxx.eventtracking.infrastructure.BizServiceImpl;
import com.apoem.mmxx.eventtracking.infrastructure.dao.mongo.dm.*;
import com.apoem.mmxx.eventtracking.infrastructure.dao.mongo.ods.BusinessAcquisitionOdsDao;
import com.apoem.mmxx.eventtracking.infrastructure.dao.mongo.ods.ConsumerAcquisitionOdsDao;
import com.apoem.mmxx.eventtracking.infrastructure.po.entity.TrackPointEntity;
import com.apoem.mmxx.eventtracking.infrastructure.po.ods.ConsumerAcquisitionOdsEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrdinaryConsumerAcquisitionDaoTest {

    @Autowired
    private EtlConsumerListener etlListener;

    @Autowired
    private BizServiceImpl bizService2;

    @Autowired
    private ConsumerAcquisitionOdsDao consumerAcquisitionOdsDao;

    @Autowired
    private BusinessAcquisitionOdsDao businessAcquisitionOdsDao;

    @Autowired
    private TrackPointDao trackPointDao;

    @Autowired
    private AgentCommunityRankingDm agentCommunityRankingDm;
    @Autowired
    private AgentCommunityRankingWholeDm agentCommunityRankingWholeDm;
    @Autowired
    private AgentVisitTrendDm agentVisitTrendDm;
    @Autowired
    private AgentVisitTrendWholeDm agentVisitTrendWholeDm;
    @Autowired
    private HouseRankingDm houseRankingDay1Dm;
    @Autowired
    private InformationStatsDm informationTrendDm;
    @Autowired
    private OverviewAgentStatsDm overviewAgentStatsDm;
    @Autowired
    private OverviewCustomerStatsDm overviewCustomerStatsDm;
    @Autowired
    private OverviewHouseCollectedStatsDm overviewHouseCollectedStatsDm;
    @Autowired
    private OverviewHouseCollectedWholeStatsDm overviewHouseCollectedWholeStatsDm;
    @Autowired
    private PageViewDm pageViewDm;
    @Autowired
    private StoreStatsDm storeStatsDm;
    @Autowired
    private StoreStatsWholeDm storeStatsWholeDm;

    @Autowired
    private RelationshipDao2 relationshipDao2;

    @Test
    public void dsds() {
        relationshipDao2.appendRecord("wwww", "33333");
    }

    @Test
    public void initDwData() {
        LocalDateTime of = LocalDateTime.of(2020, 12, 2, 12, 12);

        // 访问新房
        TrackPointEntity trackPointEntity = new TrackPointEntity();
        trackPointEntity.setAvenue(AvenueEnum.WMP.getName());
        trackPointEntity.setPageName("subPackages/shopDetail/newHouseDetail");
        trackPointEntity.setMethodName("getHouseDetail");
        trackPointEntity.setEventType("VIEW");

        String[] houseTypes = {"NWHS"};
        ods(of, trackPointEntity,houseTypes);

        // 访问二手房
        TrackPointEntity trackPointEntity2 = new TrackPointEntity();
        trackPointEntity2.setAvenue(AvenueEnum.WMP.getName());
        trackPointEntity2.setPageName("subPackages/shopDetail/shopDetail");
        trackPointEntity2.setMethodName("getHouseDetail");
        trackPointEntity2.setEventType("VIEW");

        String[] houseTypes1 = {"SHHS"};
        ods(of, trackPointEntity2, houseTypes1);
        // 访问租房
        String[] houseTypes2 = {"RTHS"};
        ods(of, trackPointEntity2, houseTypes2);

        // 访问咨询
        TrackPointEntity trackPointEntity3 = new TrackPointEntity();
        trackPointEntity3.setAvenue(AvenueEnum.WMP.getName());
        trackPointEntity3.setPageName("mph5/ziXunDetailXcx");
        trackPointEntity3.setMethodName("getNewsitem");
        trackPointEntity3.setEventType("VIEW");
        String[] houseTypes3 = {"SHHS", "RTHS", "NWHS"};
        ods(of, trackPointEntity3, houseTypes3);
    }

    private void ods(LocalDateTime localDateTime, TrackPointEntity trackPointEntity, String[] houseTypes) {
        Random random = new Random(47);

        // 2019-09-17 00:00:1
        long l = 1602172801000L;

        String[] uniqueIds = {"1001", "1002", "1003", "1004", "1005"};
        String[] uniqueIds2 = {"5006", "5007", "5008", "5009", "5010"};
        String[] agentIds = {"9001", "9002", "9003", "9004"};
        String[] cities = {"fz", "nj"};
        String[] communityIds = {"1", "2", "3", "4", "5"};
        String[] posterIds = {"190", "191", "192", "193", "194"};
        String[] storeIds = {"3001", "3002", "3003", "3004", "3005"};
        String[] gameIds = {"100", "101", "102"};
        String[] occurreds = {"APP", "H5"};
        String[] localStatuses = {"STAS", "STAF", "STAO", "STAU", "STAC", "STAG"};
        String[] tests = {"YES", "NO"};

        for (int i = 0; i < 500; i++) {
            String uniqueId = uniqueIds[i % uniqueIds.length];
            String openId = uniqueId + "_openId";
            String agentId = agentIds[i % agentIds.length];
            String communityId = communityIds[i % communityIds.length];
            String posterId = posterIds[i % posterIds.length];
            String gameId = gameIds[i % gameIds.length];
            String communityName = "commName_" + communityIds[i % communityIds.length];
            String houseType = houseTypes[i % houseTypes.length];
            String occurred = occurreds[i % occurreds.length];
            String houseId = "house_" + houseType + "_" + (101 + i % 50);
            String houseName = "houseName_" + houseType + "_" + (101 + i % 50);
            String city = i < 100 ? cities[0] : cities[1];
            String informationId = "" + (3001 + i % 3);
            String inputType = random.nextInt(1) > 0 ? "MANUAL" : "PUBLIC";
            String localStatus = localStatuses[i % localStatuses.length];
            String test = tests[i % tests.length];

            String blockId = "block_" + random.nextInt(10);
            String blockName = "blockName_" + random.nextInt(10);
            String plateId = "plate_" + random.nextInt(10);
            String plateName = "plateName_" + random.nextInt(10);
            int houseTotalPrice = 100 + (2 * i);
            int houseAveragePrice = 10000 + (1000 * i);
            int houseArea = 110 + i;
            int houseAreaUpper = 120 + i;
            int houseAreaLower = 110 + i;

            Date opTime = new Date(l + random.nextInt(86300000));
            Date startTime = new Date(opTime.getTime() - random.nextInt(10000));
            Date endTime = new Date(opTime.getTime() - random.nextInt(5000));

            String storeId = storeIds[i % storeIds.length];
            String storeName = "storeName_1";
            String companyId = "company_1";
            String companyName = "companyName_1";
            String houseTemplateId = "houseTemplate_1";
            String infoTemplateId = "infoTemplate_1";

            ContentInfo contentInfo = new ContentInfo();
            contentInfo.setHouseId(houseId);
            contentInfo.setHouseName(houseName);
            contentInfo.setHouseType(houseType);
            contentInfo.setHouseArea(new BigDecimal(houseArea));
            contentInfo.setHouseTotalPrice(new BigDecimal(houseTotalPrice));
            contentInfo.setHouseAveragePrice(new BigDecimal(houseAveragePrice));
            contentInfo.setHouseAreaLower(new BigDecimal(houseAreaLower));
            contentInfo.setHouseAreaUpper(new BigDecimal(houseAreaUpper));
            contentInfo.setHouseLivingRoom(2);
            contentInfo.setHouseBedroom(2);
            contentInfo.setHouseBathroom(2);
            contentInfo.setCommunityId(communityId);
            contentInfo.setCommunityName(communityName);
            contentInfo.setPlateId(plateId);
            contentInfo.setPlateName(plateName);
            contentInfo.setAgentId(agentId);
            contentInfo.setStoreId(storeId);
            contentInfo.setStoreName(storeName);
            contentInfo.setCompanyId(companyId);
            contentInfo.setCompanyName(companyName);
            contentInfo.setInformationId(informationId);
            contentInfo.setBlockId(blockId);
            contentInfo.setBlockName(blockName);
            contentInfo.setInputType(inputType);
            contentInfo.setPosterCode(houseTemplateId);
            contentInfo.setInformationId(informationId);
            contentInfo.setPosterId(infoTemplateId);
            contentInfo.setPosterId(posterId);
            contentInfo.setGameId(gameId);
            contentInfo.setOccurred(occurred);
            contentInfo.setLocalStatus(localStatus);
            contentInfo.setTest(test);

            String toJson = new Gson().toJson(contentInfo);

            ConsumerAcquisitionOdsEntity entity = new ConsumerAcquisitionOdsEntity();
            entity.setAvenue(trackPointEntity.getAvenue());
            entity.setPageName(trackPointEntity.getPageName());
            entity.setMethodName(trackPointEntity.getMethodName());
            entity.setEventType(trackPointEntity.getEventType());
            entity.setCity(city);
            entity.setOpTime(opTime);
            entity.setOpenId(openId);
            if(i<150) {
                entity.setUniqueId(uniqueId);
            } else {
                entity.setOpenId(uniqueIds2[i % uniqueIds2.length]);
            }

            entity.setStartTime(startTime);
            entity.setEndTime(endTime);
            entity.setContent(toJson);
            consumerAcquisitionOdsDao.insertEntity(localDateTime, entity);
        }

    }

    @Test
    public void task() {
        LocalDateTime localDateTime = LocalDateTime.now();
//        LocalDateTime of = LocalDateTime.of(2020, 9, 17, 12, 12);
        etlListener.execute(localDateTime);

//        agentVisitTrendDm.mr(localDateTime);
//        agentVisitTrendWholeDm.mr(localDateTime);
//        agentCommunityRankingDay1Dm.mr(localDateTime);
//        agentCommunityRankingDay1WholeDm.mr(localDateTime);
//        houseRankingDay1Dm.mr(localDateTime);
//        overviewAgentStatsDm.mr(localDateTime);
//        storeStatsDm.mr(localDateTime);
//        storeStatsWholeDm.mr(localDateTime);
//        informationTrendDm.mr(localDateTime);
//        overviewCustomerStatsDm.mr(localDateTime);
//        overviewHouseCollectedStatsDm.mr(localDateTime);
//        overviewHouseCollectedWholeStatsDm.mr(localDateTime);
//        pageViewDm.mr(localDateTime);
//        customerStatsDm.mr(localDateTime);
    }

    @Test
    public void name() {
        Date opTime = new Date();
        Instant instant = opTime.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime opDate = LocalDateTime.ofInstant(instant, zone);
        int hour = opDate.getHour();
        DayOfWeek dayOfWeek = opDate.getDayOfWeek();
        String displayName = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.ENGLISH);

        RangeGlanceTimeLegendEnum range1 = RangeGlanceTimeLegendEnum.findRange(hour);
        String timeRange = displayName + range1.getLabel();
        Integer timeRangeOrder = Integer.parseInt(dayOfWeek.getValue() + "" + range1.getOrderNumber());
        System.out.println(timeRange);
        System.out.println(timeRangeOrder);
    }

    @Test
    public void name2() {
        bizService2.requestCityData();
    }

    private String getProperty(int houseId, String... strings) {
        return strings[houseId % strings.length];
    }
}