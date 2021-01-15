package com.apoem.mmxx.eventtracking.infrastructure.common.config;

import com.alibaba.ttl.threadpool.TtlExecutors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: TaskPoolConfig </p>
 * <p>Description: 异步线程池 配置 </p>
 * <p>Date: 2020/7/16 12:27 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Configuration
@EnableAsync
@Slf4j
public class AsyncPoolConfig implements AsyncConfigurer {

    @Override
    public Executor getAsyncExecutor() {
        int processorAmount = Runtime.getRuntime().availableProcessors();
        log.info("Current Server Available Processors Present : {}", processorAmount);
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(processorAmount);
        taskExecutor.setMaxPoolSize(processorAmount*4);
        taskExecutor.setQueueCapacity(200);
        taskExecutor.setKeepAliveSeconds(60);
        taskExecutor.setThreadNamePrefix("async-pool-service-");
        taskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        taskExecutor.setAwaitTerminationSeconds(60);
        taskExecutor.initialize();
        return TtlExecutors.getTtlExecutor(taskExecutor);
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return (throwable, method, objects) -> {
            StringBuilder stringBuilder = new StringBuilder().append("\n");
            stringBuilder.append("╔════════════════════════ 捕获异步线程异常信息 ════════════════════════╗").append("\n");
            stringBuilder.append("║ Exception message - ").append(throwable.toString()).append("\n");
            stringBuilder.append("║ Method name - ").append(method.toGenericString()).append("\n");
            for (Object param : objects) {
                stringBuilder.append("║ Parameter value - ").append(param).append("\n");
            }
            stringBuilder.append("╚════════════════════════ 捕获异步线程异常信息 ════════════════════════╝");
            log.info(stringBuilder.toString());
            throwable.printStackTrace();
        };
    }
}