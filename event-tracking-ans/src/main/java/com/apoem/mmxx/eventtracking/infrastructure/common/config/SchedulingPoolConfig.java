package com.apoem.mmxx.eventtracking.infrastructure.common.config;

import com.alibaba.ttl.threadpool.TtlExecutors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: SchedulingPoolConfig </p>
 * <p>Description: 定时任务线程池 配置 </p>
 * <p>Date: 2020/8/26 12:27 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Configuration
@EnableScheduling
@Slf4j
public class SchedulingPoolConfig implements SchedulingConfigurer {

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        int processorAmount = Runtime.getRuntime().availableProcessors();
        log.info("Current Server Available Processors Present : {}", processorAmount);

        BasicThreadFactory.Builder handler = new BasicThreadFactory.Builder()
                .namingPattern("scheduled-pool-service-%s")
                .daemon(true)
                .priority(Thread.NORM_PRIORITY)
                // 待优化异步异常处理逻辑
                .uncaughtExceptionHandler(new ThreadGroup("scheduled-pool"));

        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(processorAmount, handler.build());
        taskRegistrar.setScheduler(TtlExecutors.getTtlScheduledExecutorService(executor));
    }
}