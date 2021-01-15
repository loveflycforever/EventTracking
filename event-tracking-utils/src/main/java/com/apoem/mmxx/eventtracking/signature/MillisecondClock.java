package com.apoem.mmxx.eventtracking.signature;

import java.sql.Timestamp;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: MillClock </p>
 * <p>Description: 毫秒钟 </p>
 * <p>Date: 2020/7/27 15:47 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
public final class MillisecondClock {

    private final long period;
    private final AtomicLong currentMillisecond;

    private MillisecondClock(long period) {
        this.period = period;
        this.currentMillisecond = new AtomicLong(System.currentTimeMillis());
        this.scheduleClockUpdating();
    }

    private static MillisecondClock instance() {
        return MillisecondClock.InstanceHolder.INSTANCE;
    }

    public static long tik() {
        return instance().tak();
    }

    public static String strik() {
        return new Timestamp(instance().tak()).toString();
    }

    private void scheduleClockUpdating() {
        ScheduledExecutorService scheduler = new ScheduledThreadPoolExecutor(1, (runnable) -> {
            Thread thread = new Thread(runnable, "Millisecond Clock Tik Tak");
            thread.setDaemon(true);
            return thread;
        });
        scheduler.scheduleAtFixedRate(() -> this.currentMillisecond.set(System.currentTimeMillis()), this.period, this.period, TimeUnit.MILLISECONDS);
    }

    private long tak() {
        return this.currentMillisecond.get();
    }

    private static class InstanceHolder {

        public static final MillisecondClock INSTANCE = new MillisecondClock(1L);

        private InstanceHolder() {}
    }
}
