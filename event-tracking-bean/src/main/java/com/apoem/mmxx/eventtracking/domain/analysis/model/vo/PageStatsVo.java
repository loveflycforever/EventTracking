package com.apoem.mmxx.eventtracking.domain.analysis.model.vo;

import lombok.Data;

@Data
public class PageStatsVo {

    /**
     * 页面、事件类型
     */
    private String pageName;
    private String pageNameCn;
    private String city;

    // 统计数据

    private Long pageView;
    private Long uniqueVisitor;
}
