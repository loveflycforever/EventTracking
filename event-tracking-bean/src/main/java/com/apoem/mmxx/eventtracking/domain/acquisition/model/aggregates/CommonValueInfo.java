package com.apoem.mmxx.eventtracking.domain.acquisition.model.aggregates;

import lombok.*;
import org.apache.commons.lang3.StringUtils;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CommonValueInfo {

    private String storeId;
    private String companyId;
    private String test;

    public CommonValueInfo init() {
        this.storeId = StringUtils.EMPTY;
        this.companyId = StringUtils.EMPTY;
        this.test = StringUtils.EMPTY;
        return this;
    }
}
