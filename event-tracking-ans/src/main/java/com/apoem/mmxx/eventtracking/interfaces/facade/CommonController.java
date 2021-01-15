package com.apoem.mmxx.eventtracking.interfaces.facade;

import com.apoem.mmxx.eventtracking.BaseConstants;
import com.apoem.mmxx.eventtracking.DateUtils;
import com.apoem.mmxx.eventtracking.application.service.ArrangementService;
import com.apoem.mmxx.eventtracking.application.service.StatisticsService;
import com.apoem.mmxx.eventtracking.infrastructure.common.CommonResponse;
import com.apoem.mmxx.eventtracking.infrastructure.common.holder.SpringContextHolder;
import com.apoem.mmxx.eventtracking.infrastructure.dao.mongo.TaskTableDaoE;
import com.apoem.mmxx.eventtracking.infrastructure.dao.support.AbstractMapReduceDmDaoV4;
import com.apoem.mmxx.eventtracking.infrastructure.dao.support.Dictate;
import com.apoem.mmxx.eventtracking.infrastructure.dao.support.DictateRepository;
import com.apoem.mmxx.eventtracking.infrastructure.dao.support.LongIdPair;
import com.apoem.mmxx.eventtracking.infrastructure.enums.PeriodTypeEnum;
import com.apoem.mmxx.eventtracking.infrastructure.po.entity.BizLogEntity;
import com.apoem.mmxx.eventtracking.infrastructure.po.entity.TaskTableEntity;
import com.apoem.mmxx.eventtracking.infrastructure.po.entity.TrackPointEntity;
import com.apoem.mmxx.eventtracking.infrastructure.po.ro.support2.Rooobject;
import com.apoem.mmxx.eventtracking.infrastructure.repository.AcquisitionOdsReadRepository;
import com.apoem.mmxx.eventtracking.infrastructure.repository.CommonRepository;
import com.apoem.mmxx.eventtracking.interfaces.dto.PhaseRequestDto;
import com.mongodb.MongoNamespace;
import com.mongodb.client.MongoCollection;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapreduce.MapReduceResults;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: CommonController </p>
 * <p>Description: 通用控制器 </p>
 * <p>Date: 2020/7/23 9:12 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@RestController
@RequestMapping("common")
@Api(value = "通用控制器")
@Slf4j
public class CommonController {

    private final CommonRepository commonRepository;

    private final StatisticsService analysisService;

    private final ArrangementService arrangementService;

    @Resource
    private AcquisitionOdsReadRepository acquisitionOdsRepository;

    @Resource
    private TaskTableDaoE taskTableDaoE;

    @Resource
    private MongoTemplate mongoTemplate;

    @Autowired
    public CommonController(CommonRepository commonRepository, StatisticsService analysisService, ArrangementService arrangementService) {
        this.commonRepository = commonRepository;
        this.analysisService = analysisService;
        this.arrangementService = arrangementService;
    }

    @PostMapping("/serialNumber/initNextMonth")
    @ApiOperation(value = "初始化下个月的序列号")
    public Object initNextMonth() {
        commonRepository.initializeNextMonthSerialNumber();
        return CommonResponse.success();
    }

    @GetMapping("/serialNumber/tryForceReinitialize")
    @ApiOperation(value = "尝试强制初始化序列号")
    public Object tryForceReinitialize() {
        commonRepository.acquireForceReinitializeToken();
        return CommonResponse.success();
    }

    @PostMapping("/serialNumber/forceReinitialize")
    @ApiOperation(value = "强制初始化序列号")
    public Object forceReinitialize(@Validated @RequestBody PhaseRequestDto requestDto) {
        commonRepository.forceReinitializeSerialNumber(requestDto.getToken(), requestDto.getStartDate(), requestDto.getEndDate());
        return CommonResponse.success();
    }

    @GetMapping("/calculate")
    @ApiOperation(value = "计算")
    public Object calculate(String date) {
        analysisService.appoint(date);
        return CommonResponse.success();
    }

    @GetMapping("/trail")
    @ApiOperation(value = "轨迹")
    public Object trail(String date) {
        arrangementService.appoint(date);
        return CommonResponse.success();
    }

    @GetMapping("/scan")
    @ApiOperation(value = "扫描")
    public Object scan(String date, int bool) {
        LocalDateTime dateTime = DateUtils.localDateTime2(date);

        List<TrackPointEntity> scan = arrangementService.scan(date, bool >= 1);
        List<String> collect = scan.stream().map(o -> {
            String join = StringUtils.join(o.getActionDefinition(), "|");
            return StringUtils.join(new String[]{join, o.getEndpoint(), o.getAvenue(), o.getEventType(), o.getPageName(), o.getMethodName(), o.getPageNameCn(), o.getMethodNameCn()}, ",");
        }).collect(Collectors.toList());
        return CommonResponse.success().setData(collect).setTotal(scan.size());
    }

    @GetMapping("/scanLog")
    @ApiOperation(value = "扫描日志")
    public String scanLog() {
        List<BizLogEntity> scan = arrangementService.scanLog();
        List<BizLogEntity> collect1 = new ArrayList<>(scan.stream().collect(Collectors.groupingBy(o -> {
            String[] s = new String[]{
                    o.getDesc(),
                    o.getPageName(),
                    o.getMethodName(),
                    o.getEventType(),
                    o.getAvenue(),
                    o.getEndpoint(),
                    o.getSource().replaceAll(",", "|"),
                    o.getTarget().replaceAll(",", "|")
            };

            return StringUtils.join(s, BaseConstants.COMMA_SPACE);
        }, Collectors.collectingAndThen(Collectors.toList(), l -> l.get(0)))).values());
        List<String> collect = collect1.stream().sorted(Comparator.comparing(BizLogEntity::getDesc).thenComparing(BizLogEntity::getPageName))
                .map(o -> {

                    String[] s = new String[]{
                            o.getDesc(),
                            o.getPageName(),
                            o.getMethodName(),
                            o.getEventType(),
                            o.getAvenue(),
                            o.getEndpoint(),
                            o.getSource().replaceAll(",", "|"),
                            o.getTarget().replaceAll(",", "|"),
                            o.getContent()
                    };
                    return StringUtils.join(s, BaseConstants.COMMA_SPACE);
                })
                .collect(Collectors.toList());
        return StringUtils.join(collect, "\n");
    }

    @GetMapping("/testService")
    @ApiOperation(value = "测试服务")
    public Object testService() {
        return CommonResponse.success();
    }

    @GetMapping("/testDatabase")
    @ApiOperation(value = "测试数据库")
    public Object testDatabase() {
        LocalDateTime now = LocalDateTime.now();
        LongIdPair businessPair = acquisitionOdsRepository.businessAcqDailyMinMaxId(now);
        LongIdPair consumerPair = acquisitionOdsRepository.consumerAcqDailyMinMaxId(now);

        Long businessPairMaxId = businessPair.getMaxId();
        Long consumerPairMaxId = consumerPair.getMaxId();
        return CommonResponse.success().setData(Pair.of(businessPairMaxId, consumerPairMaxId));
    }

    @GetMapping("/taskStatus")
    @ApiOperation(value = "任务状态")
    public Object taskStatus() {
        Integer queryDateDay = DateUtils.numericalYyyyMmDd(LocalDateTime.now().minusDays(1));

        List<TaskTableEntity> taskTableEntities = taskTableDaoE.findByDateDay(queryDateDay);

        if (CollectionUtils.isNotEmpty(taskTableEntities)) {
            return CommonResponse.success().setData(taskTableEntities).setTotal(taskTableEntities.size());
        }
        return CommonResponse.failure();
    }

    @GetMapping("/clearCache")
    @ApiOperation(value = "缓存清除")
    public Object clearCache(String cacheKey) {
        boolean result = false;
        if (StringUtils.isNotBlank(cacheKey)) {
            CacheManager cacheManager = SpringContextHolder.getBean(CacheManager.class);
            Collection<String> cacheNames = cacheManager.getCacheNames();
            for (String name : cacheNames) {
                if (cacheKey.equalsIgnoreCase(name)) {
                    Cache cache = cacheManager.getCache(name);
                    if (cache != null) {
                        cache.clear();
                        result = true;
                    }
                }
            }
        }

        return result ? CommonResponse.success() : CommonResponse.failure();

    }

    @Autowired
    private DictateRepository dictateRepository;

    @GetMapping("/testH2")
    @ApiOperation(value = "测试H2")
    @LocalApi
    public void testH2() {

        LocalDateTime now = LocalDateTime.now();
        Dictate dictate = new Dictate();
        dictate.setName("tesrt");
        dictate.setSourceQuery("{\"$or\":[{\"action_definition\":{\"$all\":[\"VIS_HUS_H5\"]}}]}");
        dictate.setSourceCollection("et_dw_daily_consumer_acquisition");
        dictate.setSourceUniqueFieldName("[\"open_id\"]");
        dictate.setKeyFieldNames("{\"city\":\"city\",\"house_id\":\"house_id\",\"house_type\":\"house_type\"}");
        dictate.setValueFieldNames("{\"house_id\":\"house_id\",\"house_name\":\"house_name\"}");
        dictate.setTemporaryCollection("temp_zzz");
        dictate.setTargetQuery("{\"$and\":[{\"date_day\":${date}},{\"period_type\":\"DAY1\"}]}");
        dictate.setTargetCollection("et_dm_house_ranking_h5");
        dictate.setPeriod(PeriodTypeEnum.DAY1.getName());
        dictate.setActionName("[\"VIS_HUS_H5\"]");
        AbstractMapReduceDmDaoV4 v4 = new AbstractMapReduceDmDaoV4();
        v4.executeRemoveHistory(dictate, now);
        MapReduceResults<Rooobject> mr = v4.mr(dictate);
        v4.insert(now, mr, dictate);
    }

    @GetMapping("/migrationData")
    @ApiOperation(value = "迁移数据")
    @LocalApi
    public void migrationData() {
        migrationData("et_ods_consumer_acquisition_");
        migrationData("et_ods_business_acquisition_");
    }

    private void migrationData(String collectionPrefix) {
        LocalDateTime now = LocalDateTime.now();
        String today = DateUtils.literalYyyyMmDd(now);
        String todayCollectionName = collectionPrefix + today;

        if(mongoTemplate.collectionExists(todayCollectionName)) {
            String yesterday = DateUtils.literalYyyyMmDd(now.minusDays(1));
            String yesterdayCollectionName = collectionPrefix + yesterday;

            if(mongoTemplate.collectionExists(yesterdayCollectionName)) {
                // 备份当前昨日表
                MongoCollection<Document> yesterdayCollection = mongoTemplate.getCollection(yesterdayCollectionName);
                String yesterdayBakCollectionName = yesterdayCollectionName + "_" + DateUtils.literalYyyyMmDdHhMmSsSss(now);
                MongoNamespace yesterdayBakCollectionNamespace = new MongoNamespace(yesterdayBakCollectionName);
                yesterdayCollection.renameCollection(yesterdayBakCollectionNamespace);
            }

            // 今日表重命名
            MongoCollection<Document> todayCollection = mongoTemplate.getCollection(todayCollectionName);
            MongoNamespace yesterdayCollectionNamespace = new MongoNamespace(yesterdayCollectionName);
            todayCollection.renameCollection(yesterdayCollectionNamespace);
        }
    }
}
