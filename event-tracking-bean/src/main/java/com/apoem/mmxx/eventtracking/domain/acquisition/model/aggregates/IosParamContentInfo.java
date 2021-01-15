package com.apoem.mmxx.eventtracking.domain.acquisition.model.aggregates;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class IosParamContentInfo {

    private CommonValueInfo commonValueInfo;
    private BusinessDataInfo businessDataInfo;

    public IosParamContentInfo init() {
        this.commonValueInfo = new CommonValueInfo().init();
        this.businessDataInfo = new BusinessDataInfo().init();
        return this;
    }
}
