package com.apoem.mmxx.eventtracking.domain.analysis.model.vo;

import lombok.Data;

@Data
public class EventStatsVo {


    private String pageName;
    private String pageNameCn;
    private String methodName;
    private String methodNameCn;
    private String eventType;
    private String city;

    // 统计数据

    private Long pageView;
    private Long uniqueVisitor;
}
