package com.apoem.mmxx.eventtracking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * 启动类
 * @author papafan
 */
@SpringBootApplication
@EnableCaching
public class EventTrackingApplication {

    public static void main(String[] args) {
        SpringApplication.run(EventTrackingApplication.class, args);
    }

}
