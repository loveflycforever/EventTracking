package com.apoem.mmxx.eventtracking.domain.analysis.model.vo;

import lombok.Data;

@Data
public class CustomerStatsVo {

    private String id;
    private BaseStatsVo house;
    private BaseStatsVo info;
}
