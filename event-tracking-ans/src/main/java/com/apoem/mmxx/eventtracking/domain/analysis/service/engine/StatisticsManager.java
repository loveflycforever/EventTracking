package com.apoem.mmxx.eventtracking.domain.analysis.service.engine;

import com.apoem.mmxx.eventtracking.DateUtils;
import com.apoem.mmxx.eventtracking.application.event.EtlBusinessListener;
import com.apoem.mmxx.eventtracking.application.event.EtlConsumerListener;
import com.apoem.mmxx.eventtracking.application.event.EtlConsumerTrailListener;
import com.apoem.mmxx.eventtracking.exception.LogSupport;
import com.apoem.mmxx.eventtracking.exception.Suppose;
import com.apoem.mmxx.eventtracking.infrastructure.dao.mongo.BizLogDao;
import com.apoem.mmxx.eventtracking.infrastructure.dao.mongo.TaskTableDao;
import com.apoem.mmxx.eventtracking.infrastructure.dao.mongo.TaskTableDaoE;
import com.apoem.mmxx.eventtracking.infrastructure.dao.mongo.TrackPointDao;
import com.apoem.mmxx.eventtracking.infrastructure.dao.mongo.ods.BusinessAcquisitionOdsDao;
import com.apoem.mmxx.eventtracking.infrastructure.dao.mongo.ods.ConsumerAcquisitionOdsDao;
import com.apoem.mmxx.eventtracking.infrastructure.dao.support.AbstractMapReduceDmDao;
import com.apoem.mmxx.eventtracking.infrastructure.dao.support.AbstractMapReduceDmDaoV2;
import com.apoem.mmxx.eventtracking.infrastructure.dao.support.AbstractMapReduceDmDaoV3;
import com.apoem.mmxx.eventtracking.infrastructure.dao.support.TaskStatusEnum;
import com.apoem.mmxx.eventtracking.infrastructure.enums.AvenueEnum;
import com.apoem.mmxx.eventtracking.infrastructure.enums.EndpointEnum;
import com.apoem.mmxx.eventtracking.infrastructure.po.entity.BizLogEntity;
import com.apoem.mmxx.eventtracking.infrastructure.po.entity.TaskTableEntity;
import com.apoem.mmxx.eventtracking.infrastructure.po.entity.TrackPointEntity;
import com.apoem.mmxx.eventtracking.infrastructure.po.ods.BusinessAcquisitionOdsEntity;
import com.apoem.mmxx.eventtracking.infrastructure.po.ods.ConsumerAcquisitionOdsEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: AcquisitionManager2 </p>
 * <p>Description: 领域层服务：实时业务数据处理服务，空实现</p>
 * <p>Date: 2020/7/15 14:20 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Service
@Slf4j
public class StatisticsManager {

    @Autowired
    private TaskTableDaoE taskTableDaoE;

    @Autowired
    private TaskTableDao taskTableDao;

    @Autowired
    private BizLogDao bizLogDao;

    @Autowired
    private TrackPointDao trackPointDao;

    @Autowired
    private BusinessAcquisitionOdsDao businessAcquisitionOdsDao;

    @Autowired
    private ConsumerAcquisitionOdsDao consumerAcquisitionOdsDao;

    @Autowired
    private EtlBusinessListener etlBusinessListener;

    @Autowired
    private EtlConsumerListener etlConsumerListener;

    @Autowired
    private EtlConsumerTrailListener etlConsumerTrailListener;

    @Autowired
    List<AbstractMapReduceDmDao<?, ?>> abstractMapReduceDmDaoList;

    @Autowired
    List<AbstractMapReduceDmDaoV2<?, ?, ?>> abstractMapReduceDmDaoV2List;

    @Autowired
    List<AbstractMapReduceDmDaoV3<?, ?>> abstractMapReduceDmDaoV3List;

    public void initial(LocalDateTime localDateTime) {
        Integer queryDateDay = DateUtils.numericalYyyyMmDd(localDateTime);

        List<TaskTableEntity> dateDayTaskTable = taskTableDaoE.findByDateDay(queryDateDay);

        if (CollectionUtils.isNotEmpty(dateDayTaskTable)) {
            dateDayTaskTable.forEach(t -> {
                Integer dateDay = t.getDateDay();
                String taskName = t.getTaskName();
                String status = t.getStatus();
                log.info(LogSupport.info(dateDay + "_" + taskName, t));
                if (TaskStatusEnum.RUNNING.maybe(status)) {
                    log.warn(LogSupport.message(dateDay + "_" + taskName, Suppose.TASK_IS_RUNNING, t));
                }
                taskTableDao.update(t, TaskStatusEnum.READY);
            });
        }

    }

    public void execute(LocalDateTime localDateTime) {
//        abstractMapReduceDmDaoList.forEach(o -> o.mr(localDateTime));
//        abstractMapReduceDmDaoV2List.forEach(o -> o.mr(localDateTime));
        abstractMapReduceDmDaoV3List.forEach(o -> o.mr(localDateTime));
    }

    public void afterCompleted(LocalDateTime date) {
        // TODO: 2020/8/17 状态刷新
//        Message通知
    }

    public void prepare(LocalDateTime date) {
        etlConsumerListener.execute(date);
        etlBusinessListener.execute(date);
    }

    public List<TrackPointEntity> scan(LocalDateTime localDateTime, boolean b) {
        List<TrackPointEntity> entities = trackPointDao.findAll();

        List<TrackPointEntity> result = new ArrayList<>();
        List<TrackPointEntity> collect = entities.stream()
                .filter(o -> AvenueEnum.ANDROID.maybe(o.getAvenue()))
                .filter(o -> {
                    if (b && o.getActionDefinition().length == 1 && !"EMPTY".equalsIgnoreCase(o.getActionDefinition()[0])) {
                        return true;
                    }
                    if (b && o.getActionDefinition().length > 1) {
                        return true;
                    }

                    return !b;
                }).collect(Collectors.toList());
        collect.forEach(o -> {
            String endpoint = o.getEndpoint();
            if (EndpointEnum.BUSINESS.maybe(endpoint)) {
                List<BusinessAcquisitionOdsEntity> list = businessAcquisitionOdsDao.find(o, localDateTime);
                if (CollectionUtils.isEmpty(list)) {
                    result.add(o);
                }
            }

            if (EndpointEnum.CONSUMER.maybe(endpoint)) {
                List<ConsumerAcquisitionOdsEntity> list = consumerAcquisitionOdsDao.find(o, localDateTime);
                if (CollectionUtils.isEmpty(list)) {
                    result.add(o);
                } else {
                    log.trace("");
                }
            }
        });

        return result;
    }

    public List<BizLogEntity> scanLog() {

        return bizLogDao.findAll();

    }

    public void check(LocalDateTime signDate) {

    }

    public void trailWhole(LocalDateTime now) {
        etlConsumerTrailListener.executeWholeDay(now);
    }

    public void trailStep(LocalDateTime localDateTime) {
        etlConsumerTrailListener.executeStep(localDateTime);
    }
}






