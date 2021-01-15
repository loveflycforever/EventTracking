package com.apoem.mmxx.eventtracking.domain.acquisition.model.aggregates;

import lombok.*;
import org.apache.commons.lang3.StringUtils;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BusinessDataInfo {

    private String type;
    private String actId;

    public BusinessDataInfo init() {
        this.type = StringUtils.EMPTY;
        this.actId = StringUtils.EMPTY;
        return this;
    }
}
