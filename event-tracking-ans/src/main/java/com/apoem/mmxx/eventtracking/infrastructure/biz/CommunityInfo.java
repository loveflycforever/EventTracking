package com.apoem.mmxx.eventtracking.infrastructure.biz;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class CommunityInfo {

    private String communityId;
    private String communityName;
    private Double communityLongitude;
    private Double communityLatitude;
    private String housePrices;
    private Integer plateId;
    private String plateName;

}
