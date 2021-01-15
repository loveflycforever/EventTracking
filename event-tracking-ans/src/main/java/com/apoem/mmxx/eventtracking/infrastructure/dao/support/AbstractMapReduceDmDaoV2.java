package com.apoem.mmxx.eventtracking.infrastructure.dao.support;

import com.apoem.mmxx.eventtracking.DateUtils;
import com.apoem.mmxx.eventtracking.infrastructure.common.holder.SpringContextHolder;
import com.apoem.mmxx.eventtracking.infrastructure.dao.mongo.TaskTableDao;
import com.apoem.mmxx.eventtracking.infrastructure.enums.PeriodTypeEnum;
import com.apoem.mmxx.eventtracking.infrastructure.enums.ProFieldEnum;
import com.apoem.mmxx.eventtracking.infrastructure.po.ro.support.RoSupport;
import com.apoem.mmxx.eventtracking.infrastructure.po.support.DmEntity;
import com.mongodb.client.result.DeleteResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapreduce.MapReduceOptions;
import org.springframework.data.mongodb.core.mapreduce.MapReduceResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.lang.reflect.ParameterizedType;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: AbstractMapReduceDmDao </p>
 * <p>Description: 抽象mr dm </p>
 * <p>Date: 2020/7/14 12:47 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Slf4j
public abstract class AbstractMapReduceDmDaoV2<TargetEntity extends DmEntity, MapReduceObject, SourceEntity> extends TaskDo {

    public MongoTemplate mongoTemplate() {
        return SpringContextHolder.getBean(MongoTemplate.class);
    }

    @SuppressWarnings("unchecked")
    private Class<TargetEntity> target() {
        return (Class<TargetEntity>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @SuppressWarnings("unchecked")
    private Class<MapReduceObject> ro() {
        return (Class<MapReduceObject>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
    }

    @SuppressWarnings("unchecked")
    private Class<SourceEntity> source() {
        return (Class<SourceEntity>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[2];
    }

    @Override
    public TaskTableDao taskTableDao() {
        return SpringContextHolder.getBean(TaskTableDao.class);
    }

    public MongoGeneratedKeyHandler mongoGeneratedKeyHandler() {
        return SpringContextHolder.getBean(MongoGeneratedKeyHandler.class);
    }

    public void startRemove(LocalDateTime localDateTime) {

    }

    public void finishRemove(LocalDateTime localDateTime) {

    }

    public void startCross(LocalDateTime localDateTime) {

    }

    public void finishCross(LocalDateTime localDateTime) {

    }

    public void startMr(LocalDateTime localDateTime) {

    }

    public void finishMr(LocalDateTime localDateTime) {

    }

    public void startCo(LocalDateTime localDateTime) {

    }

    public void finishCo(LocalDateTime localDateTime) {

    }

    public void startSave(LocalDateTime localDateTime) {

    }

    public void finishSave(LocalDateTime localDateTime) {

    }

    public void startRemoveTemp(LocalDateTime localDateTime) {

    }

    public void finishRemoveTemp(LocalDateTime localDateTime) {

    }

    public void mr(LocalDateTime localDateTime) {


        startTask(localDateTime);
        String sourceDataCollection = sourceCollectionName(localDateTime);

        if (!mongoTemplate().collectionExists(sourceDataCollection)) {
            throw new RuntimeException("Collection[" + sourceDataCollection + "] must exists.");
        }

        startRemove(localDateTime);
        if (removeHistory()) {
            executeRemoveHistory(localDateTime);
        }
        finishRemove(localDateTime);

        startCross(localDateTime);
        List<TargetEntity> crossData = Collections.emptyList();
        if(hasCrossData()) {
            crossData = crossData(localDateTime);
        }
        finishCross(localDateTime);

        startMr(localDateTime);
        // group()在返回的查询结果条数大 10,000 keys 的情况下会爆出异常，4MB的大小限制
        // mapReduce() 查询结果是生成一张表，所以返回是 fetch 一段一段加载的，不存在这样的问题。
        // group()不适合在多点分片的mongodb部署中使用
        MapReduceResults<MapReduceObject> reduceResults = mr(localDateTime, sourceDataCollection);
        finishMr(localDateTime);

        logVerbose(reduceResults);

        startCo(localDateTime);
        List<TargetEntity> convertedTargetData = processMrResult(localDateTime, reduceResults);
        finishCo(localDateTime);

        startSave(localDateTime);
        afterTargetData(localDateTime, crossData, convertedTargetData);
        finishSave(localDateTime);

        startRemoveTemp(localDateTime);
        if (removeTemporary()) {
            executeRemoveTemporary(localDateTime);
        }
        finishRemoveTemp(localDateTime);


        finishTask(localDateTime);
    }

    private String sourceCollectionName(LocalDateTime localDateTime) {
        return mongoGeneratedKeyHandler().getTableName(localDateTime, sourceDataClass());
    }

    private String temporaryCollectionName(LocalDateTime localDateTime) {
        return mongoGeneratedKeyHandler().getTableName(localDateTime, mrResultClass());
    }

    private String targetCollectionName(LocalDateTime localDateTime) {
        return mongoGeneratedKeyHandler().getTableName(localDateTime, targetClass());
    }

    /**
     * 原始数据
     * @return 原始数据类
     */
    public Class<SourceEntity> sourceDataClass() {
        return source();
    }

    /**
     * 原始数据筛选条件
     * @param localDateTime 统计时间
     * @return 条件
     */
    public abstract Query sourceDataCondition(LocalDateTime localDateTime);

    /**
     * MR临时结果数据
     * @return MR临时结果数据类
     */
    public Class<MapReduceObject> mrResultClass() {
        return ro();
    }

    /**
     * 目标数据
     * @return 目标源类
     */
    public Class<TargetEntity> targetClass() {
        return target();
    }

    /**
     * 交叉数据
     * 默认无交叉数据
     * @return 布尔值
     */
    protected boolean hasCrossData() {
        return false;
    }

    /**
     * 删除历史数据
     * 默认删除历史数据
     * @return 布尔值
     */
    public boolean removeHistory() {
        return true;
    }

    public Query removeHistoryCondition(LocalDateTime localDateTime) {
        if(removeHistory()) {
            // 默认删除当天所有数据
            return Query.query(new Criteria().andOperator(
                    Criteria.where(ProFieldEnum.DATE_DAY.getName()).is(Integer.parseInt(dateDayFormat(localDateTime))),
                    Criteria.where(ProFieldEnum.PERIOD_TYPE.getName()).is(PeriodTypeEnum.DAY1.getName())));
        }
        return null;
    }

    private void executeRemoveHistory(LocalDateTime localDateTime) {
        String collectionName = targetCollectionName(localDateTime);

        if (!mongoTemplate().collectionExists(collectionName)) {
            log.warn("Collection[" + collectionName + "] is not exists, remove history cancel.");
            return;
        }
        Query condition = removeHistoryCondition(localDateTime);

        if(condition != null) {
            DeleteResult remove = mongoTemplate().remove(condition, targetClass());
        }
    }

    /**
     * 删除临时结果表
     * 默认删除临时结果表
     * @return 布尔值
     */
    public boolean removeTemporary() {
        return true;
    }

    public void executeRemoveTemporary(LocalDateTime localDateTime) {
        String collectionName = temporaryCollectionName(localDateTime);

        if (!mongoTemplate().collectionExists(collectionName)) {
            log.warn("Collection[" + collectionName + "] is not exists, remove temporary cancel.");
            return;
        }
        mongoTemplate().dropCollection(collectionName);
    }

    public String dateDayFormat(LocalDateTime localDateTime) {
        return DateUtils.literalYyyyMmDd(localDateTime);
    }

    public Query crossDataCondition(LocalDateTime localDateTime) {
        if(hasCrossData()) {
            throw new RuntimeException("Cross data query condition must exists.");
        }
        return null;
    }

    private List<TargetEntity> crossData(LocalDateTime localDateTime) {
        List<TargetEntity> result = new ArrayList<>();

        String collectionName = targetCollectionName(localDateTime);

        if (!mongoTemplate().collectionExists(collectionName)) {
            mongoTemplate().createCollection(collectionName);
            log.warn("Collection[" + collectionName + "] is not exists, auto create just now.");
        }

        Query condition = crossDataCondition(localDateTime);

        if(condition != null) {
            result = mongoTemplate().find(condition, targetClass());
        }
        return result;
    }

    private MapReduceResults<MapReduceObject> mr(LocalDateTime localDateTime, String sourceCollectionName) {
        String mapFunction = getMapFunction();
        String reduceFunction = getReduceFunction();

        // 二手房、新房、租房
        Query sourceDataCondition = sourceDataCondition(localDateTime);

        String temporaryCollectionName = temporaryCollectionName(localDateTime);

        return mongoTemplate().mapReduce(
                sourceDataCondition,
                sourceCollectionName,
                mapFunction,
                reduceFunction,
//                new MapReduceOptions().outputCollection(temporaryCollectionName).finalizeFunction(finalizeFunction),
                new MapReduceOptions().outputCollection(temporaryCollectionName),
                mrResultClass()
        );
    }

    public String getReduceFunction() {
        return RoSupport.gentReduce(mrResultClass());
    }

    public String getMapFunction() {
        return RoSupport.gentMap(sourceDataClass(), mrResultClass());
    }

    public void logVerbose(MapReduceResults<MapReduceObject> reduceResults) {
        /* Verbose info is not useful，because the the reduceResults document is just instantiated.
        log.info("OutputCollection[{}], " +
                        "TotalTime[{}], " +
                        "MapTime[{}], " +
                        "EmitLoopTime[{}], " +
                        "EmitCount[{}], " +
                        "InputCount[{}], " +
                        "OutputCount[{}],",
                reduceResults.getOutputCollection(),
                reduceResults.getTiming().getTotalTime(),
                reduceResults.getTiming().getMapTime(),
                reduceResults.getTiming().getEmitLoopTime(),
                reduceResults.getCounts().getEmitCount(),
                reduceResults.getCounts().getInputCount(),
                reduceResults.getCounts().getOutputCount());*/
    }

    public List<TargetEntity> processMrResult(LocalDateTime localDateTime, MapReduceResults<MapReduceObject> reduceResults) {
        String collectionName = targetCollectionName(localDateTime);

        if (!mongoTemplate().collectionExists(collectionName)) {
            log.info("Collection[" + collectionName + "] is not exists.");
        }

        return convertToTargetData(localDateTime, reduceResults);
    }

    public List<TargetEntity> convertToTargetData(LocalDateTime localDateTime, MapReduceResults<MapReduceObject> reduceResults) {
        Iterator<MapReduceObject> iterator = reduceResults.iterator();
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterator, Spliterator.ORDERED), false)
                .map(ro -> convertToTargetData(localDateTime, ro)).collect(Collectors.toList());
    }

    public abstract TargetEntity convertToTargetData(LocalDateTime localDateTime, MapReduceObject ro);

    public void afterTargetData(LocalDateTime localDateTime, List<TargetEntity> crossData, List<TargetEntity> convertedTargetData) {
        convertedTargetData.forEach(o -> {
            Date current = new Date();
            o.setCreateTime(current);
            o.setUpdateTime(current);
            o.setMark(0);
            mongoTemplate().save(o);
        });
    }

}
