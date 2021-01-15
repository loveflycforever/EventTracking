package com.apoem.mmxx.eventtracking.admin;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 启动类
 * @author papafan
 */
@SpringBootApplication
@EnableAdminServer
@EnableScheduling
public class EventTrackingAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(EventTrackingAdminApplication.class, args);
    }

}
