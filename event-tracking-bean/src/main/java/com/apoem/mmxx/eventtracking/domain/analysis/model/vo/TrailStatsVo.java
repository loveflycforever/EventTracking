package com.apoem.mmxx.eventtracking.domain.analysis.model.vo;

import lombok.Data;

import java.util.Date;

@Data
public class TrailStatsVo {
    private String city;
    private String customerOpenId;
    private String customerUniqueId;
    private String agentId;
    private Date opTime;
    private Date originOpTime;
    private Long odsId;
    private String actionType;
    private Long viewTimes;
    private Long duration;
    private String actionDefinition;
    private String houseId;
    private String houseType;
    private String pageName;
    private String pageNameCn;
    private String methodName;
    private String methodNameCn;
    private String eventType;
}
